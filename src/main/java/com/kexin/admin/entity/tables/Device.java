package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kexin.common.base.TableEntity;

import java.util.Date;

/**
 * 登录用户实体类
 */
@TableName("DIC_MACHINES")
@KeySequence(value = "SQ_DIC_MACHINES", clazz = Integer.class)
public class Device {

    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.INPUT)
    @TableField(value = "MACHINE_ID")
    private Integer machineId;//设备id

    @TableField(value = "MACHINE_Type_ID")
    private Integer machineTypeId;//设备类别id

    @TableField(value = "MACHINE_CODE")
    private String machineCode; //设备代码

    @TableField(value = "MACHINE_NAME")
    private String machineName; //设备名称

    @TableField(value = "MACHINE_IP")
    private String machineIp; //设备ip
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

    @TableField(exist = false)
    private DeviceType deviceType;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getMachineTypeId() {
        return machineTypeId;
    }

    public void setMachineTypeId(Integer machineTypeId) {
        this.machineTypeId = machineTypeId;
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

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMachineIp() {
        return machineIp;
    }

    public void setMachineIp(String machineIp) {
        this.machineIp = machineIp;
    }
}
