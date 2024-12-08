package org.itzixi.zk;

import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.itzixi.pojo.netty.NettyServerNode;
import org.itzixi.utils.JsonUtils;
import org.itzixi.utils.RedisOperator;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConfigurationProperties(prefix = "zookeeper.curator")
@Data
public class CuratorConfig {
    //单机、集群ip：port
    private String host;
    //连接超时时间
    private Integer connectTimeoutMs;
    //会话超时时间
    private Integer sessionTimeoutMs;
    //每次重试的间隔时间
    private Integer sleepMsBetweenRetry;
    //最大重试次数
    private Integer maxRetries;
    //命名空间（root根节点名称）
    private String namespace;

    public static final String PATH = "/server-list";

    @Bean("curatorClient")
    public CuratorFramework curatorClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(sleepMsBetweenRetry, maxRetries);

        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(host)
                .connectionTimeoutMs(connectTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                .retryPolicy(retryPolicy)
                .namespace(namespace).build();

        client.start();

//        try {
//            client.create().creatingParentsIfNeeded().forPath("/springboot/test", "springcloud".getBytes());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

        //注册监听watcher的事件
        addWatcher(PATH, client);
        return client;
    }

    @Autowired
    private RedisOperator redis;
    @Resource
    private RabbitAdmin rabbitAdmin;

    /**
     * 注册节点的事件监听
     */
    public void addWatcher(String path, CuratorFramework client) {
        CuratorCache curatorCache = CuratorCache.build(client, path);
        curatorCache.listenable().addListener((type, oldData, data) -> {
            //type:当前监听到的事件类型
            //oldData:节点更新前的数据、状态
            //data:节点更新后的数据、状态
            System.out.println(type);

            if (oldData != null) {
                System.out.println("old path = " + oldData.getPath());
                System.out.println("old value = " + new String(oldData.getData()));
            }
            if (data != null) {
                System.out.println("new path = " + data.getPath());
                System.out.println("new value = " + new String(data.getData()));
            }

            switch (type.name()) {
                case "NODE_CREATED":
                    log.info("（子）节点创建");
                    break;
                case "NODE_CHANGED":
                    log.info("（子）节点（数据）变更");
                    break;
                case "NODE_DELETED":
                    log.info("（子）节点删除");
                    NettyServerNode oldNode = JsonUtils.jsonToPojo(
                            new String(oldData.getData()),
                            NettyServerNode.class);
                    System.out.println("old path = " + oldData.getPath());
                    System.out.println("old value = " + oldNode);

                    //移除残留端口
                    String oldPort = oldNode.getPort() + "";
                    String portKey = "netty_port";
                    redis.hdel(portKey, oldPort);

                    //移除残留队列
                    String queueName="netty_queue_"+oldPort;
                    rabbitAdmin.deleteQueue(queueName);

                    break;
                default:
                    log.info("default...");
                    break;
            }
        });

        curatorCache.start();
    }
}
