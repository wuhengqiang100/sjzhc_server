package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kexin.admin.entity.vo.AuditParameter.AuditParameterDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 审核参数实体类
 */
@TableName("SET_JUDGE_CHECK")
@KeySequence(value = "SQ_SET_JUDGE_CHECK")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditParameter {

    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.INPUT)
    @TableField(value = "JUDGE_CHECK_ID")
    private Integer judgeCheckId;//审核参数id
    
    @TableField(value = "JUDGE_CHECK_TYPE_ID")
    private Integer judgeCheckTypeId; // 审核参数种类的外键


    @TableField(exist = false)
    private AuditParameterType judgeCheckType; // 审核参数种类实体

    @TableField(value = "OPERATION_ID")
    private Integer operationId;//工序id
    @TableField(exist = false)
    private Operation operation;//工序实体

    @TableField(value = "PRODUCT_ID")
    private Integer productId;//产品id

    @TableField(exist = false)
    private Products products;//产品实体

    @TableField(value = "MACHINE_ID")
    private Integer machineId;//设备id
    @TableField(exist = false)
    private Machine machine;//设备实体

    @TableField(exist = false)
    private List<AuditParameterDetail> detailList;

    @TableField(exist = false)
    private Integer[] values;//添加,修改时的value

    @TableField(exist = false)
    private Integer[] typeIds;//添加,修改时的参数类型id

    @TableField(value = "VALUE")
    private Integer value;//审核参数的值
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
