package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 生产日志表
 */
//@KeySequence(value = "SQ_DIC_MACHINES", clazz = Integer.class)
@TableName(value = "wip_prod_logs")
@Data
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

    @TableField(value = "ITEM_FLAG")
    private Integer itemFlag;//操作标志

    @TableField(value = "QAINFONUM")
    private Integer qainfonum;//信息数量

    @TableField(value = "QAWASTERNUM")
    private Integer qawasternum;//报错数量


    @TableField(value = "SMINFONUM")
    private Integer sminfonum;//识码数量

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "START_DATE")
    protected Date startDate;


}
