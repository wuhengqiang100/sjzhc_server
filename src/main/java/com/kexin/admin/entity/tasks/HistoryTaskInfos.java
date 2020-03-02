package com.kexin.admin.entity.tasks;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Description: 实时任务页面的历史查询数据实体
 * @Author: 巫恒强  @Date: 2019/10/12 11:36
 * @Param:
 * @Return: 
 */
public class HistoryTaskInfos {
    private Integer operatorId;
    private String operatorName;
    private Integer recountTaskId;
    private Integer totalNum;
    private Date recountTaskDate;
    private Integer recountTaskInfoId;
    private Integer recountNum;
    private Integer productId;
    private String productName;
    private Integer productAmount;

    public Integer getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Integer productAmount) {
        this.productAmount = productAmount;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getRecountTaskDate() {
        return recountTaskDate;
    }

    public void setRecountTaskDate(Date recountTaskDate) {
        this.recountTaskDate = recountTaskDate;
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

    public Integer getRecountTaskId() {
        return recountTaskId;
    }

    public void setRecountTaskId(Integer recountTaskId) {
        this.recountTaskId = recountTaskId;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getRecountTaskInfoId() {
        return recountTaskInfoId;
    }

    public void setRecountTaskInfoId(Integer recountTaskInfoId) {
        this.recountTaskInfoId = recountTaskInfoId;
    }

    public Integer getRecountNum() {
        return recountNum;
    }

    public void setRecountNum(Integer recountNum) {
        this.recountNum = recountNum;
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
}
