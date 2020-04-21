package com.kexin.admin.component;

import com.kexin.admin.service.MachineService;
import com.kexin.common.util.ResponseEty;
import com.kexin.common.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

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

    /**
     * 获取数据的大屏数据方法
     * @return
     */
    public ResponseEty getData(){
        ResponseEty responseEty=new ResponseEty();responseEty.setSuccess(20000);
        return  responseEty;

    }

}
