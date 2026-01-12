package com.xzit;

import com.xzit.utils.RedisUtils;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class AutoRentalApplicationTests {
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private RedisProperties redisProperties;
    @Resource
    private StringRedisTemplate  stringRedisTemplate;
//    @Resource
//    private

    @Test
    void printRedisConfig() {
        System.out.println("Redis Host: " + redisProperties.getHost());
        System.out.println("Redis Port: " + redisProperties.getPort());
        System.out.println("Redis Database: " + redisProperties.getDatabase());
    }
    @Test
    void testConnection(){
        String str="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE3NDk3MzU0MjMsImV4cCI6MTc0OTczNzIyMywidXNlcmlkIjoxLCJpYXQiOjE3NDk3MzU0MjMsInVzZXJuYW1lIjoiYWRtaW4ifQ.yrCG1vdw_FOwEPXCdwL3PqyXB6o7hY1PxtXPgYZgUs8";
        String s = stringRedisTemplate.opsForValue().get("token:"+str);
        System.out.println("s = " + s);
    }
    @Test
    void testExpire(){
        String str="token:"+"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE3NDk3MzU0MjMsImV4cCI6MTc0OTczNzIyMywidXNlcmlkIjoxLCJpYXQiOjE3NDk3MzU0MjMsInVzZXJuYW1lIjoiYWRtaW4ifQ.yrCG1vdw_FOwEPXCdwL3PqyXB6o7hY1PxtXPgYZgUs8";
        String s = stringRedisTemplate.getExpire(str).toString();
        System.out.println("s = " + s);
    }
    @Test
    void testRedis(){
        String test = redisUtils.get("token:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE3NDkzODc2MjYsImV4cCI6MTc0OTM4OTQyNiwidXNlcmlkIjoxLCJpYXQiOjE3NDkzODc2MjYsInVzZXJuYW1lIjoiYWRtaW4ifQ.mNuZv8vS7BuXAtPRkQgZ7LLlB9BAuLiNWZkV_6SdLM0");

        System.out.println(test.equals("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE3NDkzODc2MjYsImV4cCI6MTc0OTM4OTQyNiwidXNlcmlkIjoxLCJpYXQiOjE3NDkzODc2MjYsInVzZXJuYW1lIjoiYWRtaW4ifQ.mNuZv8vS7BuXAtPRkQgZ7LLlB9BAuLiNWZkV_6SdLM0"));
    }
    @Test
    void testReturn(){

    }

}
