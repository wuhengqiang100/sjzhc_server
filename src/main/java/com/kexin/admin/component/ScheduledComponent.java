package com.kexin.admin.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kexin.admin.entity.tables.MachineWarning;
import com.kexin.admin.entity.vo.monitor.Monitor;
import com.kexin.admin.entity.vo.redis.RedisMessage;
import com.kexin.admin.mapper.MachineWarningMapper;
import com.kexin.common.util.jackson.JacksonUtil;
import com.kexin.common.util.DateUtil.DateUtil;
import com.kexin.common.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.Random;

//@EnableScheduling
@Component
public class ScheduledComponent {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    @Qualifier("redisTemplate")
    RedisTemplate redisTemplate;//获取连接等操作
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
    @Scheduled(cron = "0/3 * * * * ?")
    public  void setRedis() throws ParseException {
        Random rm = new Random();
        for (int i = 0; i <8 ; i++) {
            Monitor monitor=new Monitor(
                    "卷轴"+rm.nextInt(100),"产品"+rm.nextInt(10),"模板"+rm.nextInt(10),
                    "班组"+rm.nextInt(10), DateUtil.dateToString(new Date())
                    ,DateUtil.dateToString(new Date()),String.valueOf(10000),String.valueOf(rm.nextInt(10000)+1),String.valueOf(rm.nextInt(2000)+1),
                    String.valueOf(rm.nextInt(2000)+1), String.valueOf(rm.nextFloat()),
                    "5000z",String.valueOf(rm.nextInt(100)),"设备"+rm.nextInt(100),String.valueOf(rm.nextInt(2)));

            String monitorName="machine:"+i;
            redisUtil.hmset(monitorName, BeanMap.create(monitor));
        }


    }

    @Resource
    MachineWarningMapper machineWarningMapper;//设备报警信息mapper

/*    @Scheduled(fixedRate = 10000) //间隔2s 通过StringRedisTemplate对象向redis消息队列cat频道发布消息
    public void sendCatMessage() throws JsonProcessingException, ParseException {
        Random rm = new Random();
        ObjectMapper mapper = new ObjectMapper();

        RedisMessage redisMessage=new RedisMessage("1",String.valueOf(rm.nextInt(2)+1),"设备1异常",DateUtil.dateToString(new Date()));
        String json = mapper.writeValueAsString(redisMessage);

        MachineWarning machineWarning=new MachineWarning();
        machineWarning.setMachineId(Integer.parseInt(redisMessage.getMachineId()));
        machineWarning.setLogType(redisMessage.getLogType());
        machineWarning.setNote(redisMessage.getNote());
        machineWarning.setLogDate(DateUtil.stringToDate(redisMessage.getLogDate()));
        redisTemplate.convertAndSend("machineAlert", json);
    }*/

//    @Scheduled(fixedRate = 1000) //间隔1s 通过StringRedisTemplate对象向redis消息队列fish频道发布消息
//    public void sendFishMessage(){
//        redisTemplate.convertAndSend("fish","鱼");
//    }


}
