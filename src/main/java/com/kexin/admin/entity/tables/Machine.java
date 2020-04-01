package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 设备信息主表
 */
@TableName("dic_machines")
@KeySequence(value = "SQ_DIC_MACHINES", clazz = Integer.class)
public class Machine {
    @TableId(type = IdType.INPUT)
    @TableField(value = "MACHINE_ID")
    private Integer machineId;//设备主键id


    @TableField(value = "MACHINE_CODE")
    private String machineCode;//设备编号

    @TableField(value = "MACHINE_NAME")
    private String machineName;//设备名称

    /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "USE_FLAG")
    protected Boolean useFlag;
    /**
     * 启用时间,写入时间
     */
//    @TableField(value = "START_DATE",strategy= FieldStrategy.IGNORED)
    @TableField(value = "START_DATE")
    protected Date startDate;
    /**
     * 禁用时间,结束时间
     */
//    @TableField(value = "END_DATE",  strategy = FieldStrategy.IGNORED)
    @TableField(value = "END_DATE")
    protected Date endDate;

    /**
     * 说明
     */
    @TableField(value = "NOTE")
    protected String note;

    @TableField(value = "IMAGE_MODEL_NUM")
    protected Integer imageModelNum;

    @TableField(value = "IMAGE_MODEL_PATH")
    protected String imageModelPath;

    public Integer getImageModelNum() {
        return imageModelNum;
    }

    public void setImageModelNum(Integer imageModelNum) {
        this.imageModelNum = imageModelNum;
    }

    public String getImageModelPath() {
        return imageModelPath;
    }

    public void setImageModelPath(String imageModelPath) {
        this.imageModelPath = imageModelPath;
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

}
