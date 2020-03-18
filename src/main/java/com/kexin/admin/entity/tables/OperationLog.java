package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Description:操作日志表对应实体
 * @Author: 巫恒强
 * @Date: 2020/1/3 9:41
 */
@TableName(value = "LOG_OPERATION_NOTES")
public class OperationLog {

    @TableId(type = IdType.INPUT)
    @TableField(value = "LOG_OPERATION_NOTE_ID")
    private Integer logOperationNoteId;

    @TableField(value = "JOB_ID")
    private Integer jobId;


    @TableField(value = "OPERATOR_ID")
    private Integer operatorId;

    @TableField(value = "OPERATOR_NAME")
    private String operatorName;

    @TableField(value = "START_DATE")
    private Date startDate;

    @TableField(value = "END_DATE")
    private Date endDate;

    @TableField(value = "ITEM_FLAG")
    private Integer itemFlag;

    @TableField(value = "NOTE")
    private String note;

    @TableField(value = "OPERATION_NOTE_TYPE")
    private String operationNoteType;

    public Integer getLogOperationNoteId() {
        return logOperationNoteId;
    }

    public void setLogOperationNoteId(Integer logOperationNoteId) {
        this.logOperationNoteId = logOperationNoteId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
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

    public Integer getItemFlag() {
        return itemFlag;
    }

    public void setItemFlag(Integer itemFlag) {
        this.itemFlag = itemFlag;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOperationNoteType() {
        return operationNoteType;
    }

    public void setOperationNoteType(String operationNoteType) {
        this.operationNoteType = operationNoteType;
    }
}
