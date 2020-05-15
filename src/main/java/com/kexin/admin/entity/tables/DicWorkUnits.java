package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 机台信息表
 */
@KeySequence(value = "SQ_DIC_WORK_UNITS")
@TableName(value = "dic_work_units")
public class DicWorkUnits {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    @TableField(value = "WORK_UNIT_ID")
    private Integer workUnitId;//主键

    @TableField(value = "WORK_UNIT_CODE")
    private String workUnitCode;//机台代码

    @TableField(value = "WORK_UNIT_NAME")
    private String workUnitName;//机台名称


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getWorkUnitId() {
        return workUnitId;
    }

    public void setWorkUnitId(Integer workUnitId) {
        this.workUnitId = workUnitId;
    }

    public String getWorkUnitCode() {
        return workUnitCode;
    }

    public void setWorkUnitCode(String workUnitCode) {
        this.workUnitCode = workUnitCode;
    }

    public String getWorkUnitName() {
        return workUnitName;
    }

    public void setWorkUnitName(String workUnitName) {
        this.workUnitName = workUnitName;
    }
}
