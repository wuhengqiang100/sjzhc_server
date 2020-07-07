package com.kexin.admin.entity.vo.monitor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Monitor {

    private String rollNum;//卷轴号

    private String productName;//产品名称

    private String templateName;//模板名称

    private String shiftTeam;//生产班组

    private String startDate;//开始时间

    private String endDate;//结束时间

    private Integer totalNum;//总数计数

    private Integer goodNum;//好品计数

    private Integer doubtNum;//怀疑品计数

    private Integer wasteNum;//废品计数

    private Float goodRate;//好品率

    private String machineSpeed;//机速（单位）

    private Integer machineId;//设备id

    private String machineName;//设备名称

    private Integer status;//设备的生产状态枚举值 0:未开机生产 1 开机生产中

}
