package org.itzixi.netty.utils;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.itzixi.pojo.netty.NettyServerNode;
import org.itzixi.utils.JsonUtils;

import java.net.InetAddress;

public class ZookeeperRegister {
    public static void registerNettyServer(String nodeName,
                                           String ip,
                                           Integer port) throws Exception {
        CuratorFramework zkClient = CuratorConfig.getClient();

        String path = "/" + nodeName;
        Stat stat = zkClient.checkExists().forPath(path);
        if (stat == null) {
            zkClient.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(path);
        } else {
            System.out.println(stat);
        }

        //创建对应的临时节点，值可以放在线人数，默认为初始化的0
        NettyServerNode serverNode = new NettyServerNode();
        serverNode.setIp(ip);
        serverNode.setPort(port);

        String nodeJson = JsonUtils.objectToJson(serverNode);

        zkClient.create()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)//临时，带有顺序
                .forPath(path + "/im-", nodeJson.getBytes());
    }

    public static String getLocalIp() throws Exception {
        InetAddress address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();
        System.out.println("本机ip地址：" + ip);
        return ip;
    }
}
