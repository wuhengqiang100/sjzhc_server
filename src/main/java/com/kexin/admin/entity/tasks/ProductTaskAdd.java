package com.kexin.admin.entity.tasks;

/**
 * @Description: 任务 添加时辅助 子任务添加实体
 * @Author: 巫恒强
 * @Date: 2019/10/11 17:06
 */
public class ProductTaskAdd {

    private Integer productId;
    private String productName;
    private Integer count;




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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
