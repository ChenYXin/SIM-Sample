package org.itzixi.netty.utils;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

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
}