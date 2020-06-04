package com.kexin.admin.component;

import com.kexin.common.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledComponent {

    @Autowired
    private RedisUtil redisUtil;

    //在一个特定的时间执行这个方法 Timer

    //cron表达式
    //秒     分       时       日       月       周几

    /**
      0   0   10   *    *    ?  每天的10点   执行一次
      0  0/5  10,18  *  *  ?   每天10点和18点,每隔5分钟执行一次
     */
/*    @Scheduled(cron = "0/2 * * * * ?")
    public void hello(){

            redisUtil.set("name", "看源码学redis"+new Date());
        System.out.println(redisUtil.get("name"));
    }*/
//    @Scheduled(cron = "0/2 * * * * ?")
//    public  void setRedis(){
//            redisUtil.set("name", "看源码学redis"+new Date());
//        System.out.println(redisUtil.get("name"));
//    }
}
