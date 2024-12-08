package org.itzixi.netty.utils;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorConfig {
    //单机、集群ip：port
    //开发
    private static String host = "127.0.0.1:2181";
    //生产
//    private static String host = "39.29.1.32:2181";
    //连接超时时间
    private static Integer connectTimeoutMs = 30 * 1000;
    //会话超时时间
    private static Integer sessionTimeoutMs = 3 * 1000;
    //每次重试的间隔时间
    private static Integer sleepMsBetweenRetry = 2 * 1000;
    //最大重试次数
    private static Integer maxRetries = 3;
    //命名空间（root根节点名称）
    private static String namespace = "itzixi-im";

    //Curator的客户端,其实说就是zookeeper的客户端也是对的
    private static CuratorFramework client;

    static {
        // 定义重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(sleepMsBetweenRetry, maxRetries);

        //声明初始化客户端
        client = CuratorFrameworkFactory.builder()
                .connectString(host)
                .connectionTimeoutMs(connectTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                .retryPolicy(retryPolicy)
                .namespace(namespace)
                .build();

        //启动Curator客户端
        client.start();
    }

    public static CuratorFramework getClient() {
        return client;
    }
}
