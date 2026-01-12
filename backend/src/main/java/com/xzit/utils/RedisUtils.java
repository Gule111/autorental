package com.xzit.utils;

import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author 31507
 */
@Data
@Component
public class RedisUtils {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 设置键值对，并指定过期时间
     *
     * @param key     键，用于唯一标识存储的数据
     * @param value   值，存储的数据内容
     * @param timeout 过期时间（秒），用于指定数据的存活时间
     */
    public void set(String key, String value, Long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, Duration.ofSeconds(timeout));
    }

    /**
     * a
     * 根据键获取存储的值
     *
     * @param key 键，用于检索存储的数据
     * @return 存储的值，如果键不存在则返回null
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 删除指定键的存储数据
     *
     * @param key 键，用于指定要删除的数据
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }


}
