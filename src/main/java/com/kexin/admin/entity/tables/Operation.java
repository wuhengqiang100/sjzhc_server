package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 工序实体类
 */
@TableName("DIC_OPERATIONS")
@KeySequence(value = "SQ_DIC_OPERATIONS", clazz = Integer.class)
@Data
public class Operation {

    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.INPUT)
    @TableField(value = "OPERATION_ID")
    private Integer operationId;//工序id

    @TableField(value = "OPERATION_CODE")
    private String operationCode; //工序代码

    @TableField(value = "OPERATION_NAME")
    private String operationName; //工序名称

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

    @TableField(value = "OPERATION_TYPE_ID")
    private Integer operationTypeId; // 工序种类的外键

    @TableField(exist = false)
    private OperationType operationType;

}
