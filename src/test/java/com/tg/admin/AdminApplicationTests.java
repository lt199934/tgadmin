package com.tg.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class AdminApplicationTests {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() throws JsonProcessingException {
        String code = "qqqqq";
        ObjectMapper om = new ObjectMapper();
        redisTemplate.opsForValue().set("1",om.writeValueAsString(code));
        System.out.println(redisTemplate.opsForValue().get("1"));
    }

    public static void main(String[] args) {
        for(int i=1;i<=100;i++) {
            if (i%5!=0){
                System.out.println("活着的和尚编号："+i);
            }
        }
    }
}
