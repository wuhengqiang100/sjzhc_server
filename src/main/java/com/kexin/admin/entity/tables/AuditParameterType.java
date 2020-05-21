package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 审核参数种类实体类
 */
@TableName("SET_JUDGE_CHECK_TYPES")
@KeySequence(value = "SQ_SET_JUDGE_CHECK_TYPES")
@Data
public class AuditParameterType {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    @TableField(value = "JUDGE_CHECK_TYPE_ID")
    private Integer judgeCheckTypeId;//审核参数种类id

    @TableField(value = "JUDGE_CHECK_TYPE_CODE")
    private String judgeCheckTypeCode; //审核参数种类代码

    @TableField(value = "JUDGE_CHECK_TYPE_NAME")
    private String judgeCheckTypeName; //审核参数种类名称


    @TableField(exist = false)
    private Integer value;//临时传值

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
