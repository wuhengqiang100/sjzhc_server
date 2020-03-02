package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.OperationType;
import com.kexin.admin.mapper.OperationTypeMapper;
import com.kexin.admin.service.OperationTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 节点类别配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OperationTypeServiceImpl extends ServiceImpl<OperationTypeMapper, OperationType> implements OperationTypeService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)
    
    @Override
    public Integer operationTypeCountByCode(String operationTypeCode) {
        QueryWrapper<OperationType> wrapper = new QueryWrapper<>();
        wrapper.eq("OPERATION_TYPE_CODE",operationTypeCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer operationTypeCountByName(String operationTypeName) {
        QueryWrapper<OperationType> wrapper = new QueryWrapper<>();
        wrapper.eq("OPERATION_TYPE_NAME",operationTypeName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOperationType(OperationType operationType) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        baseMapper.insert(operationType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOperationType(OperationType operationType) {
//        dropUserRolesByUserId(user.getLoginId());
        baseMapper.updateById(operationType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOperationType(OperationType operationType) {
        baseMapper.deleteById(operationType.getOperationTypeId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockOperationType(OperationType operationType) {
        if (operationType.getUseFlag()){
            operationType.setUseFlag(false);
            operationType.setEndDate(new Date());
        }else{
            operationType.setUseFlag(true);
            operationType.setEndDate(null);
        }
        baseMapper.updateById(operationType);
    }
}
