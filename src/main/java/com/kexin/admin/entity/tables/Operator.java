package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 人员实体类
 */
@TableName("dic_operators")
@KeySequence(value = "SQ_DIC_OPERATORS")
@Data
public class Operator {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.INPUT)
    @TableField(value = "OPERATOR_ID")
    private Integer operatorId;//人员序号

    @TableField(value = "OPERATOR_CODE")
    private String operatorCode; //人员编号

    @TableField(value = "OPERATOR_NAME")
    private String operatorName; //人员名称

    /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "USE_FLAG", fill = FieldFill.INSERT_UPDATE)
    protected Boolean useFlag;
    /**
     * 启用时间,写入时间
     */
//    @TableField(value = "START_DATE",strategy= FieldStrategy.IGNORED)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "START_DATE")
    protected Date startDate;
    /**
     * 禁用时间,结束时间
     */
//    @TableField(value = "END_DATE",  strategy = FieldStrategy.IGNORED)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "END_DATE")
    protected Date endDate;

    /**
     * 说明
     */
    @TableField(value = "NOTE")
    protected String note;



}
