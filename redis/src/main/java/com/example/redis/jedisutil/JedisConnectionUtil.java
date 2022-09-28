package com.example.redis.jedisutil;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisConnectionUtil {
    private static final JedisPool jedisPool;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(8);
        config.setMaxIdle(8);
        config.setMinIdle(0);
        config.setMaxWaitMillis(1000);
        jedisPool = new JedisPool(config, "192.168.189.128", 6379, 1000, "123456");
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }
}
