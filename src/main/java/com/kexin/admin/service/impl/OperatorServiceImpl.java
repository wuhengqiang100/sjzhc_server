package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.Operator;
import com.kexin.admin.mapper.OperatorMapper;
import com.kexin.admin.service.OperatorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 人员配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OperatorServiceImpl extends ServiceImpl<OperatorMapper, Operator> implements OperatorService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)

    @Override
    public Integer operatorCountByCode(String operatorCode) {
        QueryWrapper<Operator> wrapper = new QueryWrapper<>();
        wrapper.eq("OPERATOR_CODE",operatorCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer operatorCountByName(String operatorName) {
        QueryWrapper<Operator> wrapper = new QueryWrapper<>();
        wrapper.eq("OPERATOR_NAME",operatorName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOperator(Operator operator) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        if (operator.getUseFlag()){ //启用
            operator.setStartDate(new Date());
            operator.setEndDate(null);
        }else{//禁用
            operator.setEndDate(new Date());
        }
        baseMapper.insert(operator);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOperator(Operator operator) {
//        dropUserRolesByUserId(user.getLoginId());
        if (operator.getUseFlag()){ //启用
            operator.setStartDate(new Date());
            operator.setEndDate(null);
        }else{//禁用
            operator.setEndDate(new Date());
        }
        baseMapper.updateById(operator);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOperator(Operator operator) {
        baseMapper.deleteById(operator.getOperatorId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockOperator(Operator operator) {
        if (operator.getUseFlag()){
            operator.setUseFlag(false);
            operator.setEndDate(new Date());
        }else{
            operator.setUseFlag(true);
            operator.setEndDate(null);
        }
        baseMapper.updateById(operator);
    }
}
