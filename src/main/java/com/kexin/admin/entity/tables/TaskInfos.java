package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 复点任务金额信息表,有具体的复点任务分类
 */
@TableName(value = "wip_recount_task_infos")
public class TaskInfos{

    @TableId(type = IdType.AUTO)
    @TableField(value = "RECOUNT_TASK_INFO_ID")
    private Integer recountTaskInfoId;//复点金额主键序号

    @TableField(value = "RECOUNT_TASK_ID")
    private Integer recountTaskId; //复点任务编号(外键)

    @TableField(value = "PRODUCT_ID")
    private Integer productId; //产品类型ID(外键)


    @TableField(exist = false)
    private Products product;//产品信息


    @TableField(value = "RECOUNT_NUM")
    private Integer recountNum; //复点数量

    @TableField(value = "NOTE")
    private String note;//说明


    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getRecountTaskInfoId() {
        return recountTaskInfoId;
    }

    public void setRecountTaskInfoId(Integer recountTaskInfoId) {
        this.recountTaskInfoId = recountTaskInfoId;
    }

    public Integer getRecountTaskId() {
        return recountTaskId;
    }

    public void setRecountTaskId(Integer recountTaskId) {
        this.recountTaskId = recountTaskId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getRecountNum() {
        return recountNum;
    }

    public void setRecountNum(Integer recountNum) {
        this.recountNum = recountNum;
    }
}
