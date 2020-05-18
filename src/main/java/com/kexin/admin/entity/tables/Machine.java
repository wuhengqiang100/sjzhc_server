package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 设备信息主表
 */
@TableName("dic_machines")
@KeySequence(value = "SQ_DIC_MACHINES")
@Data
public class Machine {
    @TableId(type = IdType.INPUT)
    @TableField(value = "MACHINE_ID")
    private Integer machineId;//设备主键id


    @TableField(value = "MACHINE_CODE")
    private String machineCode;//设备编号

    @TableField(value = "MACHINE_CODE_MES")
    protected String machineCodeMes;//mes设备code,控制显示

    @TableField(value = "MACHINE_NAME")
    private String machineName;//设备名称

    @TableField(value = "MACHINE_IP")
    private String machineIp;//机器IP

    @TableField(value = "USE_MACHINE_WASTE_NO_JUDGE")
    private Boolean useMachineWasteNoJudge;//机检严重废人工不干预标志： 0 不启用 1 启用

    /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "USE_FLAG")
    protected Boolean useFlag;
    /**
     * 启用时间,写入时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "START_DATE")
    protected Date startDate;
    /**
     * 禁用时间,结束时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "END_DATE")
    protected Date endDate;
    /**
     * 说明
     */
    @TableField(value = "NOTE")
    protected String note;





}
