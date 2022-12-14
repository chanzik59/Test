package com.example.redis;

import com.alibaba.fastjson2.JSON;
import com.example.redis.entiy.User;
import com.example.redis.jedisutil.JedisConnectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SpringBootTest
@Slf4j
class RedisApplicationTests {
    private static final String REDIS_HOST = "192.168.189.128";
    private static final int REDIS_PORT = 6379;
    /**
     * 使用spring 结合lettuce 操作redis  key 和value 会被序列化之后放入redis 中
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 普通jedis的测试使用
     */
    private Jedis jedis;

    @Test
    void contextLoads() {
        jedis.set("name", "小强");
        String name = jedis.get("age");
        log.info("接收的名字为{}", name);


    }

    @Test
    void testStringTemplate() {
        redisTemplate.opsForValue().set("name2", "小芳");
        Object name = redisTemplate.opsForValue().get("name2");
        log.info("template测试{}", name);

    }


    @Test
    void testObjectByTemplate() {
        User user = new User("小张", 30);
        redisTemplate.opsForValue().set("xiaozhang", user);
        User xiaoZhang = (User) redisTemplate.opsForValue().get("xiaozhang");
        log.info(xiaoZhang.toString());

    }

    /**
     * 使用stringredistemplate   手动序列化对象，节省redis 存储空间
     */
    @Test
    void testStringRedisTemplate() {
        User user = new User("小王", 67);
        String json = JSON.toJSONString(user);
        stringRedisTemplate.opsForValue().set("xiaoliu", json);
        String info = stringRedisTemplate.opsForValue().get("xiaoliu");
        User redisUser = JSON.parseObject(info, User.class);
        log.info(redisUser.toString());

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
