package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import static com.baomidou.mybatisplus.annotation.FieldStrategy.IGNORED;

/**
 * 权限实体类
 */
@KeySequence(value = "SQ_DIC_FUNCTIONS")
@TableName("dic_functions")
public class Functions {

    @TableId(type = IdType.AUTO)
    @TableField(value = "FUNCTON_ID")
    private int functionId;//权限id

    @TableField(value = "FUNCTON_CODE")
    private String functionCode;//权限编号

    @TableField(value = "FUNCTON_NAME")
    private String functionName;//权限功能名称
    /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "USE_FLAG", fill = FieldFill.INSERT_UPDATE)
    protected Boolean useFlag;
    /**
     * 启用时间,写入时间
     */
//    @TableField(value = "START_DATE",strategy=FieldStrategy.IGNORED )
    @TableField(value = "START_DATE")
    protected Date startDate;
    /**
     * 禁用时间,结束时间
     */
//    @TableField(value = "END_DATE",  strategy = IGNORED)
    @TableField(value = "END_DATE")
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

    public int getFunctionId() {
        return functionId;
    }

    public void setFunctionId(int functionId) {
        this.functionId = functionId;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
}
