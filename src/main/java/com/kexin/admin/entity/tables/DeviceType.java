package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 设备类别类别实体类
 */
@TableName("DIC_MACHINE_TYPE")
@KeySequence(value = "SQ_DIC_MACHINE_TYPE", clazz = Integer.class)
public class DeviceType {

    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.INPUT)
    @TableField(value = "MACHINE_TYPE_ID")
    private Integer machineTypeId;//设备类别id

    @TableField(value = "MACHINE_TYPE_CODE")
    private String machineTypeCode; //设备类别代码

    @TableField(value = "MACHINE_TYPE_NAME")
    private String machineTypeName; //设备类别名称

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getMachineTypeId() {
        return machineTypeId;
    }

    public void setMachineTypeId(Integer machineTypeId) {
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
}
