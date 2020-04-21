package com.kexin.admin;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.kexin.admin.entity.pojo.User;
import com.kexin.common.util.redis.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisSpringBootTests {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testRedisUtil() {
        redisUtil.set("name", "看源码学redis");
        System.out.println(redisUtil.get("name"));
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
