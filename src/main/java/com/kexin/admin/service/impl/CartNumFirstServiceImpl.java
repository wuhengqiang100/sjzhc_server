package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.CartNumFirst;
import com.kexin.admin.mapper.CartNumFirstMapper;
import com.kexin.admin.service.CartNumFirstService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 车号首字母配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CartNumFirstServiceImpl extends ServiceImpl<CartNumFirstMapper, CartNumFirst> implements CartNumFirstService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)




    @Override
    public Integer cartNumFirstCountByCode(String cartNumFirstCode) {
        QueryWrapper<CartNumFirst> wrapper = new QueryWrapper<>();
        wrapper.eq("NUM_CODE",cartNumFirstCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer cartNumFirstCountByName(String cartNumFirstName) {
        QueryWrapper<CartNumFirst> wrapper = new QueryWrapper<>();
        wrapper.eq("NUM_NAME",cartNumFirstName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCartNumFirst(CartNumFirst cartNumFirst) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        baseMapper.insert(cartNumFirst);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCartNumFirst(CartNumFirst cartNumFirst) {
//        dropUserRolesByUserId(user.getLoginId());
        baseMapper.updateById(cartNumFirst);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCartNumFirst(CartNumFirst cartNumFirst) {
        baseMapper.deleteById(cartNumFirst.getNumId());
    }
/*
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockCartNumFirst(CartNumFirst cartNumFirst) {
        if (cartNumFirst.getUseFlag()){
            cartNumFirst.setUseFlag(false);
            cartNumFirst.setEndDate(new Date());
        }else{
            cartNumFirst.setUseFlag(true);
            cartNumFirst.setEndDate(null);
        }
        baseMapper.updateById(cartNumFirst);
    }*/
}
