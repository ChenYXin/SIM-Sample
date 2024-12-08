package org.itzixi.zk;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
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

        return client;
    }
}
