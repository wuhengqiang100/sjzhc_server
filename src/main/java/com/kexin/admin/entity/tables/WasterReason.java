package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 错误类型实体类
 */
@TableName("DIC_WASTER_REASONS")
@KeySequence(value = "SQ_DIC_WASTER_REASONS")
@Data
public class WasterReason {
    
    @TableId(type = IdType.INPUT)
    @TableField(value = "WASTER_REASONS_ID")
    private Integer wasterReasonsId;//错误类型id

    @TableField(value = "OPERATION_ID")
    private Integer operationId;//外键,工序id

    @TableField(exist = false)
    private Operation operation;//工序实体

    @TableField(value = "WASTER_REASONS_INDEX")
    private String wasterReasonsIndex; //错误类型序号

    @TableField(value = "WASTER_REASONS_CODE")
    private String wasterReasonsCode; //错误类型代码

    @TableField(value = "WASTER_REASONS_NAME")
    private String wasterReasonsName; //错误类型名称


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



}
