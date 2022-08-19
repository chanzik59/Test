package com.example.security.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @author chenzhiqin
 * @date 2022/8/19 15:23
 * @info json 序列化redis
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
    private Class<T> clazz;

    public FastJsonRedisSerializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }


    @Override
    public byte[] serialize(T t) throws SerializationException {
        return Optional.ofNullable(t).map(v -> JSON.toJSONString(v, SerializerFeature.WriteClassName).getBytes(StandardCharsets.UTF_8)).orElse(new byte[0]);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        return Optional.ofNullable(bytes).filter(v -> v.length > 0).map(v -> JSON.parseObject(new String(v), clazz)).orElse(null);
    }

    @Override
    public boolean canSerialize(Class<?> type) {
        return RedisSerializer.super.canSerialize(type);
    }

    @Override
    public Class<?> getTargetType() {
        return RedisSerializer.super.getTargetType();
    }
}
