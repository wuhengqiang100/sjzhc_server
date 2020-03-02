package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kexin.common.base.TableEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户身份实体,人员类型
 */
@TableName("dic_operator_types")
public class Identity{
    @TableId(type = IdType.AUTO)
    @TableField(value = "OPERATOR_TYPE_ID")
    private int operatorTypeId;//身份id

    @TableField(value = "OPERATOR_TYPE_CODE")
    private String operatorTypeCode;//身份编号

    @TableField(value = "OPERATOR_TYPE_NAME")
    private String operatorTypeName;//身份名称
    /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "USE_FLAG", fill = FieldFill.INSERT_UPDATE)
    protected Boolean useFlag;
    /**
     * 启用时间,写入时间
     */
    @TableField(value = "START_DATE",strategy= FieldStrategy.IGNORED)
    protected Date startDate;
    /**
     * 禁用时间,结束时间
     */
    @TableField(value = "END_DATE",  strategy = FieldStrategy.IGNORED)
    protected Date endDate;

    /**
     * 说明
     */
    @TableField(value = "NOTE")
    protected String note;

    public Boolean getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(Boolean useFlag) {
        this.useFlag = useFlag;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getOperatorTypeId() {
        return operatorTypeId;
    }

    public void setOperatorTypeId(int operatorTypeId) {
        this.operatorTypeId = operatorTypeId;
    }

    public String getOperatorTypeCode() {
        return operatorTypeCode;
    }

    public void setOperatorTypeCode(String operatorTypeCode) {
        this.operatorTypeCode = operatorTypeCode;
    }

    public String getOperatorTypeName() {
        return operatorTypeName;
    }

    public void setOperatorTypeName(String operatorTypeName) {
        this.operatorTypeName = operatorTypeName;
    }
}
