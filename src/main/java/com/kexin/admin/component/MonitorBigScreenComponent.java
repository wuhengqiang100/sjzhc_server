package com.kexin.admin.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kexin.admin.entity.tables.DisplayPlatformInfo;
import com.kexin.admin.entity.vo.monitor.Monitor;
import com.kexin.admin.entity.vo.redis.RedisMessage;
import com.kexin.admin.service.DisplayPlatformInfoService;
import com.kexin.admin.service.MachineService;
import com.kexin.common.util.DateUtil.StringUtil;
import com.kexin.common.util.ResponseEty;
import com.kexin.common.util.constantEnum.ConstantEnum;
import com.kexin.common.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 大屏监控组件
 */
@Component
public class MonitorBigScreenComponent {


    @Autowired
    @Qualifier("redisTemplate")
    RedisTemplate redisTemplate;//获取连接等操作

    @Autowired
    RedisUtil redisUtil;//redis操作utils

    @Autowired
    MachineService machineService;

    @Autowired
    DisplayPlatformInfoService displayPlatformInfoService;

    /**
     * 获取大屏的卡片数据的上面一排
     * @return
     */
    public ResponseEty getCardTop(){
        ResponseEty responseEty=new ResponseEty();

        List<DisplayPlatformInfo> displayPlatformInfoList=displayPlatformInfoService.getDisplayPlatFormById(ConstantEnum.DISPLAY_PLAT_FORM);//获取第一块大屏的数据
        List<Monitor> monitorList=new ArrayList<>();
        for (int i = 0; i <displayPlatformInfoList.size() ; i++) {
            if (i<ConstantEnum.DISPLAY_PLAT_FORM_COUNT){
                String key="machine:"+displayPlatformInfoList.get(i).getMachineId();
                Map<Object, Object> hmget = redisUtil.hmget(key);
                ObjectMapper objectMapper = new ObjectMapper();
                Monitor monitor=objectMapper.convertValue(hmget,Monitor.class);
                monitorList.add(monitor);
            }
        }

        /**
         * 1:获取machine里面监控字段为true的设备的并且排序的设备id  String数组
         * 2:遍历这个数组,根据设备id拼成key"machine:1"的形式,放在String数组里
         * 3:在遍历拼接的同时查询根据兼职查询redis数据库,把返回的实时数据monitor实体里面
         * 4:查询到了数据就放在monitorList里返回到页面遍历显示出来
         */
        responseEty.setAny("monitorList",monitorList);
        responseEty.setSuccess(20000);
        return  responseEty;
    }
    /**
     * 获取大屏的卡片数据的下面一排
     * @return
     */
    public ResponseEty getCardBottom(){
        ResponseEty responseEty=new ResponseEty();

        List<DisplayPlatformInfo> displayPlatformInfoList=displayPlatformInfoService.getDisplayPlatFormById(ConstantEnum.DISPLAY_PLAT_FORM);//获取第一块大屏的数据
        List<Monitor> monitorList=new ArrayList<>();
        for (int i = 0; i <displayPlatformInfoList.size() ; i++) {
            if (i>=ConstantEnum.DISPLAY_PLAT_FORM_COUNT){
                String key="machine:"+displayPlatformInfoList.get(i).getInfoId();
                Map<Object, Object> hmget = redisUtil.hmget(key);
                ObjectMapper objectMapper = new ObjectMapper();
                Monitor monitor=objectMapper.convertValue(hmget,Monitor.class);
                monitorList.add(monitor);
            }
        }

        /**
         * 1:获取machine里面监控字段为true的设备的并且排序的设备id  String数组
         * 2:遍历这个数组,根据设备id拼成key"machine:1"的形式,放在String数组里
         * 3:在遍历拼接的同时查询根据兼职查询redis数据库,把返回的实时数据monitor实体里面
         * 4:查询到了数据就放在monitorList里返回到页面遍历显示出来
         */
        responseEty.setAny("monitorList",monitorList);
        responseEty.setSuccess(20000);
        return  responseEty;
    }


    /**
     * 获取大屏监控的设备报警信息
     * @return
     */
/*    public ResponseEty getWaring(){
        ResponseEty responseEty=new ResponseEty();


        *//**
     * 1:获取machine里面监控字段为true的设备的并且排序的设备id  String数组
     * 2:遍历这个数组,根据设备id拼成key"machine:1"的形式,放在String数组里
     * 3:在遍历拼接的同时查询根据兼职查询redis数据库,把返回的实时数据monitor实体里面
     * 4:查询到了数据就放在monitorList里返回到页面遍历显示出来
     *//*
        Map<Object, Object> hmget1 = redisUtil.hmget("machine:1");
        Map<Object, Object> hmget2 = redisUtil.hmget("machine:2");
        Map<Object, Object> hmget3 = redisUtil.hmget("machine:3");
//        Map<Object, Object> hmget4 = redisUtil.hmget("machine:4");
        ObjectMapper objectMapper = new ObjectMapper();
        Monitor monitor1=objectMapper.convertValue(hmget1,Monitor.class);
        Monitor monitor2=objectMapper.convertValue(hmget2,Monitor.class);
        Monitor monitor3=objectMapper.convertValue(hmget3,Monitor.class);
        List<Monitor> monitorList=new ArrayList<>();
        monitorList.add(monitor1);
        monitorList.add(monitor2);
        monitorList.add(monitor3);
//        monitorList.add(monitor4);
        responseEty.setAny("monitorList",monitorList);
        responseEty.setSuccess(20000);
        return  responseEty;
    }*/


}
