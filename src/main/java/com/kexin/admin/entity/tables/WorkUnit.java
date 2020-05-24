package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 机台实体类
 */
@TableName("DIC_WORK_UNITS")
@KeySequence(value = "SQ_DIC_WORK_UNITS")
@Data
public class WorkUnit {



    @TableId(type = IdType.INPUT)
    @TableField(value = "WORK_UNIT_ID")
    private Integer workUnitId;//机台id

    @TableField(value = "WORK_UNIT_CODE")
    private String workUnitCode; //机台代码

    @TableField(value = "WORK_UNIT_NAME")
    private String workUnitName; //机台名称

    @TableField(value = "OPERATOR_ID")
    private Integer operatorId;//外键 机长id

    @TableField(exist = false)
    private Operator operator;//机长实体

    /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "USE_FLAG")
    private Boolean useFlag;//启用状态
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

    @TableField(value = "WORK_UNIT_CODE_MES")
    private String workUnitCodeMes; // MES机台代码 控制显示

    @TableField(exist = false)
    private String requestIp;//请求ip

}
