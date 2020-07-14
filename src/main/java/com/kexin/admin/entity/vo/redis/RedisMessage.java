package com.kexin.admin.entity.vo.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RedisMessage {

    private String machineId;//设备id

    private String logType;//报警级别 1,2,3 最高三级

    private String note;//报警信息描述 "设备"+"描述"

    private String logDate;//报警时间

    public RedisMessage(String json) throws IOException, JsonProcessingException {
        RedisMessage param = new ObjectMapper().readValue(json, RedisMessage.class);
        this.machineId=param.getMachineId();
        this.logType = param.getLogType();
        this.note = param.getNote();
        this.logDate = param.getLogDate();
    }

}
