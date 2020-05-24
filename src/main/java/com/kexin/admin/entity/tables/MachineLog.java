package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description:设备更改日志对应实体
 * @Author: 巫恒强
 * @Date: 2020/1/3 9:41
 */
@Data
@TableName(value = "LOG_MACHINE_SETS")
@KeySequence(value = "SQ_LOG_MACHINE_SETS")
@NoArgsConstructor
@AllArgsConstructor
public class MachineLog {

    @TableId(type = IdType.INPUT)
    @TableField(value = "LOG_MACHINE_ID")
    private Integer logMachineId;

    @TableField(value = "OPERATOR_ID")
    private Integer operatorId;

    @TableField(exist = false)
    private Operator operator;


    @TableField(value = "MACHINE_IP")
    private String machineIp;

    @TableField(value = "LOG_DATE")
    private Date logDate;

    @TableField(value = "LOG_INFO")
    private String logInfo;


}
