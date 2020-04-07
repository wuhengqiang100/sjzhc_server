package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Description:上传日志表对应实体
 * @Author: 巫恒强
 * @Date: 2020/1/3 9:41
 */
//SQ_LOG_DATAUP_SETS
@KeySequence(value = "SQ_LOG_DATAUP_SETS")
@TableName(value = "LOG_DATAUP_SETS")
public class DataupLog {

    @TableId(type = IdType.INPUT)
    @TableField(value = "DATAUP_SET_ID")
    private Integer dataupSetId;

    @TableField(value = "OPERATOR_ID")
    private Integer operatorId;

    @TableField(value = "OPERATOR_NAME")
    private String operatorName;

    @TableField(value = "DATAUP_SET_DATE")
    private Date dateupSetDate;

    @TableField(value = "NOTE")
    private String note;

    public Integer getDataupSetId() {
        return dataupSetId;
    }

    public void setDataupSetId(Integer dataupSetId) {
        this.dataupSetId = dataupSetId;
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
    public Date getDateupSetDate() {
        return dateupSetDate;
    }

    public void setDateupSetDate(Date dateupSetDate) {
        this.dateupSetDate = dateupSetDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
