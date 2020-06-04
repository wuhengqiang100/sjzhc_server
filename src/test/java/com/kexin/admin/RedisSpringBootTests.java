package com.kexin.admin;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.kexin.admin.component.ScheduledComponent;
import com.kexin.admin.entity.pojo.User;
import com.kexin.admin.entity.vo.monitor.Monitor;
import com.kexin.common.util.redis.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class RedisSpringBootTests {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    ScheduledComponent scheduledComponent;//定时组件

    @Test
    public void testRedisUtil() {
        Monitor monitor=new Monitor(
                "1","产品1","模板1","班组1","2020-6-3 15:43:60"
                ,"2020-6-3 15:43:60",10000,9999,1,222, (float) 0.88,"5000z",1,"设备1");
        redisUtil.hmset("machine:1", BeanMap.create(monitor));
    }


    @Test
    void contextLoads() {
        // redisTemplate 操作不同的数据类型，api和我们的指令是一样的
        // opsForValue 操作字符串 类似String
        // opsForList 操作List 类似List
        // opsForSet
        // opsForHash
        // opsForZSet
        // opsForGeo
        // opsForHyperLogLog
        // 除了进本的操作，我们常用的方法都可以直接通过redisTemplate操作，比如事务，和基本CRUD
        // 获取redis的连接对象
        // RedisConnection connection =
        redisTemplate.getConnectionFactory().getConnection();
        // connection.flushDb();
        // connection.flushAll();
        redisTemplate.opsForValue().set("mykey", "看源码学redis");
        System.out.println(redisTemplate.opsForValue().get("mykey"));
    }


    @Test
    public void test() throws JsonProcessingException {
        User user =new User("巫恒强",23);
//        String jsonUser=new ObjectMapper().writeValueAsString(user);//jdk序列化
        redisTemplate.opsForValue().set("user",user);
        System.out.println(redisTemplate.opsForValue().get("user"));
    }


}
