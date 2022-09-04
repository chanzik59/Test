package com.example.redis;

import com.example.redis.jedisutil.JedisConnectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SpringBootTest
@Slf4j
class RedisApplicationTests {
    private static final String REDIS_HOST = "192.168.189.128";
    private static final int REDIS_PORT = 6379;
    private Jedis jedis;

    @Test
    void contextLoads() {
        jedis.set("name", "小强");
        String name = jedis.get("age");
        log.info("接收的名字为{}", name);


    }


    @Test
    void testString() {
        jedis.set("name", "小强");
        String name = jedis.get("age");
        log.info("接收的名字为{}", name);
    }

    @Test
    void testHash() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", "小强");
        hashMap.put("age", "209");
        hashMap.put("sex", "nan");
        jedis.hset("user", hashMap);
        Map<String, String> user = jedis.hgetAll("user");
        log.info(user.toString());

    }

    @BeforeEach
    void setUp() {
        jedis = JedisConnectionUtil.getJedis();

    }

    @AfterEach
    void tearDown() {
        if (Objects.nonNull(jedis)) {
            jedis.close();
        }
    }

}
