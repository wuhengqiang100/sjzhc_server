package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kexin.admin.entity.tasks.ProductTaskAdd;
import com.kexin.common.base.TableEntityTask;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 复点任务信息表
 */
@TableName(value = "wip_recount_tasks")
public class Tasks {

    @TableId(type = IdType.AUTO)
    @TableField(value = "RECOUNT_TASK_ID")
    private Integer recountTaskId;//复点任务主键

    @TableField(value = "RECOUNT_TASK_CODE")
    private String recountTaskCode; //复点任务编号

    @TableField(value = "RECOUNT_TASK_DATE")
    private Date recountTaskDate; //复点任务录入日期

    @TableField(value = "RECOUNT_NUM")
    private Integer recountNum; //复点总数

    @TableField(value = "RECORD_OPERATOR_ID")
    private Integer recordOperatorId; //记录人员ID(外键)
    @TableField(value = "FINISHED_FLAG")
    private Integer finishedFlag; //复点任务状态

    @TableField(value = "CONFIRM_OPERATOR_ID")
    private Integer confirmOperatorId; //完后确认人员ID(外键)
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

    @TableField(exist = false)
    private User recordOperator;//记录人员实体
    @TableField(exist = false)
    private User confirmOperator;//确认人员实体

    //@TableField(exist = false)
    //private String [] productIds;//添加产品的数组


    @TableField(exist = false)
    private List<TaskInfos> taskInfos ;//具体的复点任务信息,有分类

    @TableField(exist = false)
    private List<ProductTaskAdd> productIds ;//具体的复点任务信息,有分类
    @TableField(exist = false)
    private List<Products> productsList;//所有的产品类型

    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }

    public List<ProductTaskAdd> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<ProductTaskAdd> productIds) {
        this.productIds = productIds;
    }

    public User getRecordOperator() {
        return recordOperator;
    }

    public void setRecordOperator(User recordOperator) {
        this.recordOperator = recordOperator;
    }

    public User getConfirmOperator() {
        return confirmOperator;
    }

    public void setConfirmOperator(User confirmOperator) {
        this.confirmOperator = confirmOperator;
    }

    public List<TaskInfos> getTaskInfos() {
        return taskInfos;
    }

    public void setTaskInfos(List<TaskInfos> taskInfos) {
        this.taskInfos = taskInfos;
    }

    public Integer getRecountTaskId() {
        return recountTaskId;
    }

    public void setRecountTaskId(Integer recountTaskId) {
        this.recountTaskId = recountTaskId;
    }

    public String getRecountTaskCode() {
        return recountTaskCode;
    }

    public void setRecountTaskCode(String recountTaskCode) {
        this.recountTaskCode = recountTaskCode;
    }

    public Date getRecountTaskDate() {
        return recountTaskDate;
    }

    public void setRecountTaskDate(Date recountTaskDate) {
        this.recountTaskDate = recountTaskDate;
    }

    public Integer getRecountNum() {
        return recountNum;
    }

    public void setRecountNum(Integer recountNum) {
        this.recountNum = recountNum;
    }

    public Integer getRecordOperatorId() {
        return recordOperatorId;
    }

    public void setRecordOperatorId(Integer recordOperatorId) {
        this.recordOperatorId = recordOperatorId;
    }

    public Integer getFinishedFlag() {
        return finishedFlag;
    }

    public void setFinishedFlag(Integer finishedFlag) {
        this.finishedFlag = finishedFlag;
    }

    public Integer getConfirmOperatorId() {
        return confirmOperatorId;
    }

    public void setConfirmOperatorId(Integer confirmOperatorId) {
        this.confirmOperatorId = confirmOperatorId;
    }


}
