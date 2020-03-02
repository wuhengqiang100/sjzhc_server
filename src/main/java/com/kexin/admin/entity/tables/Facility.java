package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kexin.admin.entity.vo.CheckOptionsGroup;
import com.kexin.admin.entity.vo.CheckOptionsType;
import com.kexin.common.base.TableEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 设备信息主表
 */
@TableName("dic_machines")
public class Facility {
    @TableId(type = IdType.AUTO)
    @TableField(value = "MACHINE_ID")
    private int machineId;//设备主键id

    @TableField(value = "MACHINE_TYPE_ID")
    private int machineTypeId;//设备类型外键

    @TableField(value = "MACHINE_CODE")
    private String machineCode;//设备编号

    @TableField(value = "MACHINE_NAME")
    private String machineName;//设备名称

    @TableField(exist = false)
    private FacilityType facilityType;//设备类型  一对一

    @TableField(exist = false)
    private List<FacilityGroup> facilityGroupList;//设备分组 可扩张的 多对多

    @TableField(value ="GROUP_STRING")
    private String groupString;//设备分组字符串

    @TableField(value ="ORDER_NUM")
    private String orderNum;//设备排序字符串

    @TableField(value ="OPERATOR_ID")
    private Integer operatorId;//设备用户外键


    @TableField(exist = false)
    private User user;//设备属于用户

    @TableField(exist = false)
    private int[] checkMachineGroup;//设备分组数组

    @TableField(exist = false)
    private int[] checkMachineType;//设备分类数组

    @TableField(exist = false)
    private CheckOptionsGroup checkOptionsGroup;//分组options

    @TableField(exist = false)
    private CheckOptionsType checkOptionsType;//类别options
//    @TableField(exist = false)
//    private Integer groupId;//分组id
//
//    public Integer getGroupId() {
//        return groupId;
//    }
//
//    public void setGroupId(Integer groupId) {
//        this.groupId = groupId;
//    }
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


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public int[] getCheckMachineGroup() {
        return checkMachineGroup;
    }

    public void setCheckMachineGroup(int[] checkMachineGroup) {
        this.checkMachineGroup = checkMachineGroup;
    }

    public int[] getCheckMachineType() {
        return checkMachineType;
    }

    public void setCheckMachineType(int[] checkMachineType) {
        this.checkMachineType = checkMachineType;
    }

    public CheckOptionsGroup getCheckOptionsGroup() {
        return checkOptionsGroup;
    }

    public void setCheckOptionsGroup(CheckOptionsGroup checkOptionsGroup) {
        this.checkOptionsGroup = checkOptionsGroup;
    }

    public CheckOptionsType getCheckOptionsType() {
        return checkOptionsType;
    }

    public void setCheckOptionsType(CheckOptionsType checkOptionsType) {
        this.checkOptionsType = checkOptionsType;
    }

    public String getGroupString() {
        return groupString;
    }

    public void setGroupString(String groupString) {
        this.groupString = groupString;
    }

    public FacilityType getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(FacilityType facilityType) {
        this.facilityType = facilityType;
    }

    public List<FacilityGroup> getFacilityGroupList() {
        return facilityGroupList;
    }

    public void setFacilityGroupList(List<FacilityGroup> facilityGroupList) {
        this.facilityGroupList = facilityGroupList;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public int getMachineTypeId() {
        return machineTypeId;
    }

    public void setMachineTypeId(int machineTypeId) {
        this.machineTypeId = machineTypeId;
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

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
