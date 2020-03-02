package com.kexin.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 接线人员M名单  实体类
 */
@TableName("DIC_OPERATORS")
public class DicOperators {

    @TableId(type = IdType.AUTO)
    @TableField(value = "OPERATOR_ID")
    private int operatorId;//人员id

    @TableField(value = "OPERATOR_CODE")
    private String operatorCode;//人员卡号

    @TableField(value = "OPERATOR_NAME")
    private String operatorName; //人员名称



    @TableField(value = "START_DATE")
    private String startDate; //开始时间

    @TableField(value = "END_DATE")
    private String endDate;//结束时间

    @TableField(value = "NOTE")
    private String note;//备注

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
