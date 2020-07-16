package com.kexin.admin.entity.vo.monitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Monitor {

    private String rollNum;//卷轴号

    private String productName;//产品名称

    private String templateName;//模板名称

    private String shiftTeam;//生产班组

    private String startDate;//开始时间

    private String endDate;//结束时间

    private String totalNum;//总数计数

    private String goodNum;//好品计数

    private String doubtNum;//怀疑品计数

    private String wasteNum;//废品计数

    private String goodRate;//好品率

    private String machineSpeed;//机速（单位）

    private String machineId;//设备id

    @JsonSerialize
    private String machineName;//设备名称

    private String status;//设备的生产状态枚举值 0:未开机生产 1 开机生产中

}
