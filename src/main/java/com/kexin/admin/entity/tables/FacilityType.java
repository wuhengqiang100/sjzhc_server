package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kexin.common.base.TableEntity;

import java.io.Serializable;
import java.util.Date;


@TableName("dic_machine_types")
public class FacilityType {
    @TableId(type = IdType.AUTO)
    @TableField(value = "MACHINE_TYPE_ID")
    private int machineTypeId;//设备类型id

    @TableField(value = "MACHINE_TYPE_CODE")
    private String machineTypeCode;//设备类型编号

    @TableField(value = "MACHINE_TYPE_NAME")
    private String machineTypeName;//设备类型名称
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

    public int getMachineTypeId() {
        return machineTypeId;
    }

    public void setMachineTypeId(int machineTypeId) {
        this.machineTypeId = machineTypeId;
    }


    public String getMachineTypeCode() {
        return machineTypeCode;
    }

    public void setMachineTypeCode(String machineTypeCode) {
        this.machineTypeCode = machineTypeCode;
    }

    public String getMachineTypeName() {
        return machineTypeName;
    }

    public void setMachineTypeName(String machineTypeName) {
        this.machineTypeName = machineTypeName;
    }
}
