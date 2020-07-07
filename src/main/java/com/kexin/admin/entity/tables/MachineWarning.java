package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 设备报警信息
 */
@TableName("LOG_MACHINE_WARNINGS")
@KeySequence(value = "SQ_LOG_MACHINE_WARNINGS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineWarning {

    @TableId(type = IdType.INPUT)
    @TableField(value = "LOG_ID")
    private Integer logId;//主键：报警ID

    @TableField(value = "MACHINE_ID")
    private Integer machineId;//外键：设备ID

    @TableField(exist = false)
    private Machine machine;//设备实体

    @TableField(value = "LOG_TYPE")
    private String logType;//日志类型

    @TableField(value = "NOTE")
    private String note;//内容

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "LOG_DATE")
    private Date logDate;//时间

    @TableField(value = "LOG_STATE")
    private Integer logState;//日志状态 0 没处理,1 处理完成

    @TableField(value = "OPERATOR_ID")
    private Integer operatorId;//外键：人员id

    @TableField(exist = false)
    private Operator operator;//人员实体

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "DEAL_DATE")
    private Date dealDate;//处理时间

}
