package com.vktech.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    public  void testRedis()
    {
        redisTemplate.opsForValue().set("email","vivek@gmail.com");
        Object email = redisTemplate.opsForValue().get("sal");
        int  a=56;

    }
}
