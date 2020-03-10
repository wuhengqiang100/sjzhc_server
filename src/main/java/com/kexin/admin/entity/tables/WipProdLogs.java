package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 生产日志表
 */
//@KeySequence(value = "SQ_DIC_MACHINES", clazz = Integer.class)
@TableName(value = "wip_prod_logs")
public class WipProdLogs {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    @TableField(value = "LOG_ID")
    private Integer logId;//主键

    @TableField(value = "JOB_ID")
    private Integer jobId;//外键（生产序号）

    @TableField(value = "OPERATION_ID")
    private Integer operationId;//生产工序

    @TableField(value = "MACHINE_ID")
    private Integer machineId;//生产机器

    @TableField(value = "WORK_UNIT_ID")
    private Integer workUnitId;//生产机台

    @TableField(value = "OPERATOR_ID")
    private Integer operatorId;//操作人


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public Integer getWorkUnitId() {
        return workUnitId;
    }

    public void setWorkUnitId(Integer workUnitId) {
        this.workUnitId = workUnitId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }
}
