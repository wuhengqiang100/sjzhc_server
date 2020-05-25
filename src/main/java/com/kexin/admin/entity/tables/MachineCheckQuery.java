package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Description:生产日志视图综合查询
 * @Author: 巫恒强
 * @Date: 2020/1/3 9:41
 */

@TableName(value = "view_produce_log")
@Data
public class MachineCheckQuery {

    @TableId(type = IdType.INPUT)
    @TableField(value = "LOG_ID")
    private Integer logId; //生产日志id

    @TableField(value = "ITEM_FLAG")
    private Integer itemFlag;//操作标志

    @TableField(value = "QAINFONUM")
    private Integer qainfonum;//信息数量

    @TableField(value = "QAWASTERNUM")
    private Integer qawasternum;//报错数量

    @TableField(value = "SMINFONUM")
    private Integer sminfonum;//识码数量

    @TableField(value = "JOB_ID")
    private Integer jobId; //基础生产信息主键id

    @TableField(value = "CART_NUMBER")
    private String cartNumber;//生产车号（大万编号）

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "START_DATE")
    private Date startDate;//日志开始时间

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "END_DATE")
    private Date endDate;//日志结束时间

    @TableField(value = "PRODUCT_ID")
    private Integer productId;//产品id

    @TableField(value = "PRODUCT_NAME")
    private String productName;//产品名称

    @TableField(value = "OPERATION_ID")
    private Integer operationId;//工序id

    @TableField(value = "OPERATION_NAME")
    private String operationName; //工序名称

    @TableField(value = "OPERATOR_ID")
    private Integer operatorId;//人员序号

    @TableField(value = "OPERATOR_NAME")
    private String operatorName; //人员名称

    @TableField(value = "MACHINE_ID")
    private Integer machineId;//设备主键id

    @TableField(value = "MACHINE_NAME")
    private String machineName;//设备名称

    @TableField(value = "WORK_UNIT_ID")
    private Integer workUnitId;//主键

    @TableField(value = "WORK_UNIT_NAME")
    private String workUnitName;//机台名称

    @TableField(value = "INSPECTM_ID")
    private Integer inspectmId;//主键


    @TableField(value = "INFO_NUMBER")
    private Integer infoNumber;//整万信息数量

    @TableField(value = "MACHINE_WASTER_NUMBER")
    private Integer machineWasterNumber;//检测报错条数

   @TableField(value = "Nocheck_Number")
    private Integer nocheckNumber;//未检条数

    @TableField(value = "judge_waster_number")
    private Integer judgeWasterNumber;//判费数量

    @TableField(value = "note")
    private String note;//备注

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "CHECK_DATE")
    private Date checkDate;//审核时间

    @TableField(value = "AUTO_CHECK_FLAG")
    private Integer autoCheckFlag;//自动审核标志: 0 未设定 1 自动审核 2 人工审核




}
