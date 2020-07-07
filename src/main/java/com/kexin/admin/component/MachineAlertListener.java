package com.kexin.admin.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kexin.admin.entity.tables.MachineWarning;
import com.kexin.admin.entity.vo.redis.RedisMessage;
import com.kexin.admin.mapper.MachineWarningMapper;
import com.kexin.common.util.DateUtil.DateUtil;
import com.kexin.common.util.constantEnum.ConstantEnum;
import lombok.SneakyThrows;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 监听发送的消息
 */
@Component
public class MachineAlertListener implements MessageListener {

    @Resource
    private MachineWarningMapper machineWarningMapper;
    //当前工具类
    private static MachineAlertListener machineAlertListener;
    //解决静态方法中不能直接用mapper的问题
    @PostConstruct
    public void init() {
        machineAlertListener = this;
        machineAlertListener.machineWarningMapper = this.machineWarningMapper;
    }



    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] bytes) {
        RedisMessage redisMessage=new RedisMessage();
        ObjectMapper mapper = new ObjectMapper();

        redisMessage = mapper.readValue(message.toString(), RedisMessage.class);
        MachineWarning machineWarning=new MachineWarning();
        machineWarning.setLogType(redisMessage.getLogType());
        machineWarning.setNote(redisMessage.getNote());
        machineWarning.setMachineId(redisMessage.getMachineId());
        machineWarning.setLogState(ConstantEnum.NOT_DEAL_STATE);
        machineWarning.setLogDate(DateUtil.stringToDate(redisMessage.getLogDate()));
        System.out.println(machineWarning.toString());

        int total=machineAlertListener.machineWarningMapper.insert(machineWarning);
        System.out.println(total);
    }
}
