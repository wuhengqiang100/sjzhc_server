package com.kexin.admin.component;

import com.kexin.admin.entity.tables.MachineWarning;
import com.kexin.admin.entity.vo.redis.RedisMessage;
import com.kexin.admin.mapper.MachineWarningMapper;
import com.kexin.admin.service.MachineWarningService;
import com.kexin.common.util.DateUtil.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;

@Component
public class MessageReceiveHandler {

    @Resource
    MachineWarningMapper machineWarningMapper;

    @Autowired
    MachineWarningService machineWarningService1;

    public void messagePush(RedisMessage redisMessage) throws ParseException {
        MachineWarning machineWarning=new MachineWarning();
        machineWarning.setMachineId(Integer.parseInt(redisMessage.getMachineId()));
        machineWarning.setLogType(redisMessage.getLogType());
        machineWarning.setNote(redisMessage.getNote());
        machineWarning.setLogDate(DateUtil.stringToDate(redisMessage.getLogDate()));

        machineWarningMapper.insert(machineWarning);
        System.out.println("----------收到消息了message："+machineWarning.toString());

    }
}
