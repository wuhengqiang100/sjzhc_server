package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 基础生产表
 */
//@KeySequence(value = "SQ_DIC_MACHINES", clazz = Integer.class)
@TableName(value = "wip_jobs")
public class WipJobs {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    @TableField(value = "JOB_ID")
    private Integer jobId;//主键

    @TableField(value = "PRODUCT_ID")
    private Integer productId;//产品种类号

    @TableField(value = "CART_NUMBER")
    private String cartNumber;//生产车号（大万编号）


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getCartNumber() {
        return cartNumber;
    }

    public void setCartNumber(String cartNumber) {
        this.cartNumber = cartNumber;
    }
}
