package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @Description:设备更改日志对应实体
 * @Author: 巫恒强
 * @Date: 2020/1/3 9:41
 */
@TableName(value = "LOG_MACHINE_SETS")
public class MachineLog {

    @TableId(type = IdType.INPUT)
    @TableField(value = "LOG_MACHINE_ID")
    private Integer logMachineId;

    @TableField(value = "OPERATOR_ID")
    private Integer operatorId;

    @TableField(exist = false)
    private String operatorName="员工名称";

    @TableField(value = "MACHINE_IP")
    private String machineIp;

    @TableField(value = "LOG_DATE")
    private Date logDate;

    @TableField(value = "LOG_INFO")
    private String logInfo;

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Integer getLogMachineId() {
        return logMachineId;
    }

    public void setLogMachineId(Integer logMachineId) {
        this.logMachineId = logMachineId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getMachineIp() {
        return machineIp;
    }

    public void setMachineIp(String machineIp) {
        this.machineIp = machineIp;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }
}