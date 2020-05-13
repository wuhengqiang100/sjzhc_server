package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 公司信息主表
 */
@TableName("DIC_COMPANIES")
@KeySequence(value = "SQ_DIC_COMPANIES")
@Data
public class Company {
    @TableId(type = IdType.INPUT)
    @TableField(value = "COMPANY_ID")
    private Integer companyId;//公司主键id


    @TableField(value = "COMPANY_CODE")
    private String companyCode;//公司编号

    @TableField(value = "COMPANY_NAME")
    private String companyName;//公司名称


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
