package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Description:生产日志视图综合查询
 * @Author: 巫恒强
 * @Date: 2020/1/3 9:41
 */
@TableName(value = "view_produce_log")
public class ProduceLog {

    @TableId(type = IdType.INPUT)
    @TableField(value = "LOG_ID")
    private Integer logId; //生产日志id

    @TableField(value = "ITEM_FLAG")
    private Integer itemFlag;//操作标志

    @TableField(value = "QAINFONUM")
    private Integer qainfonum;//信息数量

    @TableField(value = "QAWASTERNUM")
    private Integer qawasternum;//报错数量

    @TableField(value = "SMINFONUM")
    private Integer sminfonum;//识码数量

    @TableField(value = "JOB_ID")
    private Integer jobId; //基础生产信息主键id

    @TableField(value = "CART_NUMBER")
    private String cartNumber;//生产车号（大万编号）

    @TableField(value = "START_DATE")
    private Date startDate;//日志开始时间

    @TableField(value = "END_DATE")
    private Date endDate;//日志结束时间

    @TableField(value = "PRODUCT_ID")
    private Integer productId;//产品id

    @TableField(value = "PRODUCT_NAME")
    private String productName;//产品名称

    @TableField(value = "OPERATION_ID")
    private Integer operationId;//工序id

    @TableField(value = "OPERATION_NAME")
    private String operationName; //工序名称

    @TableField(value = "OPERATOR_ID")
    private Integer operatorId;//人员序号

    @TableField(value = "OPERATOR_NAME")
    private String operatorName; //人员名称

    @TableField(value = "MACHINE_ID")
    private Integer machineId;//设备主键id

    @TableField(value = "MACHINE_NAME")
    private String machineName;//设备名称

    @TableField(value = "WORK_UNIT_ID")
    private Integer workUnitId;//主键

    @TableField(value = "WORK_UNIT_NAME")
    private String workUnitName;//机台名称

    @TableField(value = "INSPECTM_ID")
    private Integer inspectmId;//主键

    @TableField(value = "MACHINE_WASTER_NUMBER")
    private Integer machineWasterNumber;//整万错误数量


    @TableField(value = "INFO_NUMBER")
    private Integer infoNumber;//整万信息数量

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getItemFlag() {
        return itemFlag;
    }

    public void setItemFlag(Integer itemFlag) {
        this.itemFlag = itemFlag;
    }

    public Integer getQainfonum() {
        return qainfonum;
    }

    public void setQainfonum(Integer qainfonum) {
        this.qainfonum = qainfonum;
    }

    public Integer getQawasternum() {
        return qawasternum;
    }

    public void setQawasternum(Integer qawasternum) {
        this.qawasternum = qawasternum;
    }

    public Integer getSminfonum() {
        return sminfonum;
    }

    public void setSminfonum(Integer sminfonum) {
        this.sminfonum = sminfonum;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getCartNumber() {
        return cartNumber;
    }

    public void setCartNumber(String cartNumber) {
        this.cartNumber = cartNumber;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
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

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public Integer getWorkUnitId() {
        return workUnitId;
    }

    public void setWorkUnitId(Integer workUnitId) {
        this.workUnitId = workUnitId;
    }

    public String getWorkUnitName() {
        return workUnitName;
    }

    public void setWorkUnitName(String workUnitName) {
        this.workUnitName = workUnitName;
    }

    public Integer getInspectmId() {
        return inspectmId;
    }

    public void setInspectmId(Integer inspectmId) {
        this.inspectmId = inspectmId;
    }

    public Integer getMachineWasterNumber() {
        return machineWasterNumber;
    }

    public void setMachineWasterNumber(Integer machineWasterNumber) {
        this.machineWasterNumber = machineWasterNumber;
    }

    public Integer getInfoNumber() {
        return infoNumber;
    }

    public void setInfoNumber(Integer infoNumber) {
        this.infoNumber = infoNumber;
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


}
