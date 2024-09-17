package com.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    void testSendMail(){
            redisTemplate.opsForValue().set("email","kaushikjain0608@gmail.com");
            Object email = redisTemplate.opsForValue().get("email");
            int a = 1;
//        emailService.sendEmail("kaushikjain0608@gmail.com","Testing java mail sender","Hi, App kese hai");
    }
}
