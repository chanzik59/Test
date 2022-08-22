package com.example.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author chenzhiqin
 * @date 2022/8/22 12:01
 * @info XX
 */
@Component
public class RedisCache {
    @Autowired
    private RedisTemplate redisTemplate;

    public <T> void setCache(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public <T> void setCache(final String key, final T value, final Long ttl, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, ttl, timeUnit);
    }

    public boolean expire(final String key, final Long timeout) {
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    public boolean expire(final String key, final Long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    public <T> T getCacheValue(final String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    public boolean delete(final String key) {
        return redisTemplate.delete(key);
    }

    public long deleteCollete(final Collection collection) {
        return redisTemplate.delete(collection);
    }

    public <E> long setCacheList(final String key, final List<E> list) {
        return Optional.ofNullable(redisTemplate.opsForList().rightPushAll(key, list)).orElse(0L);
    }

    public <E> List<E> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public <E> BoundSetOperations<String, E> setCacheSet(final String key, final Set<E> valueSet) {
        BoundSetOperations<String, E> boundSetOperations = redisTemplate.boundSetOps(key);
        valueSet.stream().forEach(boundSetOperations::add);
        return boundSetOperations;
    }

    public <E> Set<E> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public <T> void setCacheMap(final String key, final Map<String, T> valueMap) {
        Optional.ofNullable(valueMap).ifPresent(v -> redisTemplate.opsForHash().putAll(key, v));

    }

    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public <T> void setCacheMapValue(final String key, final String hashKey, final T value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public <T> T getCacheMapValue(final String key, final String hashKey) {
        return (T) redisTemplate.opsForHash().get(key, hashKey);
    }

    public void deleteCacheMapValue(final String key, final String hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    public <E> List<E> getCacheMapValueList(final String key, final Collection hashKey) {
        return redisTemplate.opsForHash().multiGet(key, hashKey);
    }

    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }
}
