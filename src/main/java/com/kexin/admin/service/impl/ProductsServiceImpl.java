package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.Products;
import com.kexin.admin.entity.tables.Products;
import com.kexin.admin.mapper.ProductsMapper;
import com.kexin.admin.service.ProductsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * @Description:
 * @Author: 巫恒强  @Date: 2019/10/11 14:15
 * @Param: 产品service层
 * @Return: 
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductsServiceImpl extends ServiceImpl<ProductsMapper, Products> implements ProductsService {

    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)

    @Override
    public Integer productCountByCode(String productCode) {
        QueryWrapper<Products> wrapper = new QueryWrapper<>();
        wrapper.eq("PRODUCT_CODE",productCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer productCountByName(String productName) {
        QueryWrapper<Products> wrapper = new QueryWrapper<>();
        wrapper.eq("PRODUCT_NAME",productName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveProducts(Products product) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        if (product.getUseFlag()){//启用
            product.setStartDate(new Date());
            product.setEndDate(null);
        }else{//禁用
            product.setEndDate(new Date());
        }
        baseMapper.insert(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProducts(Products product) {
//        dropUserRolesByUserId(user.getLoginId());
        if (product.getUseFlag()){//启用
            product.setStartDate(new Date());
            product.setEndDate(null);
        }else{//禁用
            product.setEndDate(new Date());
        }
        baseMapper.updateById(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProducts(Products product) {
        baseMapper.deleteById(product.getProductId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockProducts(Products product) {
        if (product.getUseFlag()){
            product.setUseFlag(false);
            product.setEndDate(new Date());
        }else{
            product.setUseFlag(true);
            product.setEndDate(null);
        }
        baseMapper.updateById(product);
    }
}
