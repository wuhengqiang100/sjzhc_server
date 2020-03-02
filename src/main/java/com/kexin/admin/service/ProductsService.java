package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.Products;
import com.kexin.admin.entity.tables.Products;
import org.apache.ibatis.annotations.Param;


/**
 * 产品信息service
 */
public interface ProductsService extends IService<Products> {

    /**
     * 根据产品编码计算数量,当前机器的code的数量
     * @param productCode
     * @return
     */
    Integer productCountByCode(@Param("productCode") String productCode);


    /**
     * 根据产品名称计算数量
     * @param productName
     * @return
     */
    Integer productCountByName(@Param("productName") String productName);

    /**
     * 保存产品
     * @param product
     */
    void saveProducts(@Param("product") Products product);


    /**
     * 修改更新产品
     * @param product
     */
    void updateProducts(@Param("product") Products product);

    /**
     * 删除产品(单个)
     * @param product
     */
    void deleteProducts(@Param("product") Products product);

    /**
     * 禁用或者启用产品
     * @param product
     */
    void lockProducts(@Param("product") Products product);
}
