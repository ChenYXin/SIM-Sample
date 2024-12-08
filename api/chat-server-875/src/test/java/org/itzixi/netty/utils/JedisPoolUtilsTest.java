package org.itzixi.netty.utils;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class JedisPoolUtilsTest {

    @Test
    void getJedis() {
        String key = "testJedis";

        Jedis jedis = JedisPoolUtils.getJedis();
        jedis.set(key, "Hello Jedis");
        String cacheValue = jedis.get(key);
        System.out.println(cacheValue);
    }

    @Test
    void testGetNettyPort() {
        Integer nettyPort = selectPort(nettyDefaultPort);
        System.out.println(nettyPort);
    }

    public static final Integer nettyDefaultPort = 875;
    public static final String initOnlineCounts = "0";

    public static Integer selectPort(Integer port) {
        String portKey = "netty_port";
        Jedis jedis = JedisPoolUtils.getJedis();
        Map<String, String> portMap = jedis.hgetAll(portKey);
        System.out.println(portMap);

        //由于map中的key都应该是整数类型的port,所以先转换成整数后，再比对，否则string类型的端口比对会有问题
        List<Integer> portList = portMap
                .entrySet()
                .stream()
                .map(entry -> Integer.valueOf(entry.getKey()))
                .collect(Collectors.toList());

        System.out.println(portList);
        Integer nettyPort = null;
        if (portList == null || portList.isEmpty()) {
            jedis.hset(portKey, port + "", initOnlineCounts);
            nettyPort = port;
        } else {
            //使用stream循环获得最大值，并且累加10（累加的数值大家自己决定）
            Optional<Integer> maxInteger = portList.stream().max(Integer::compareTo);
            Integer maxPort = maxInteger.get().intValue();
            Integer currentPort = maxPort + 10;
            jedis.hset(portKey, currentPort + "", initOnlineCounts);
            nettyPort = currentPort;
        }

        return nettyPort;
    }

    @Test
    public void testCurator() throws Exception {
        CuratorFramework zkClient = CuratorConfig.getClient();

        String path = "/server-list";
        Stat stat = zkClient.checkExists().forPath(path);

        String nodeData = new String(zkClient.getData().forPath(path));

        System.out.println(nodeData);
        System.out.println(stat.toString());
    }
}