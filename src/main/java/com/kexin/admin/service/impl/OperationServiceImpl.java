package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.Operation;
import com.kexin.admin.mapper.OperationMapper;
import com.kexin.admin.service.OperationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 工序配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OperationServiceImpl extends ServiceImpl<OperationMapper, Operation> implements OperationService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)



    @Override
    public Integer operationCountByCode(String operationCode) {
        QueryWrapper<Operation> wrapper = new QueryWrapper<>();
        wrapper.eq("OPERATION_CODE",operationCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer operationCountByName(String operationName) {
        QueryWrapper<Operation> wrapper = new QueryWrapper<>();
        wrapper.eq("OPERATION_NAME",operationName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOperation(Operation operation) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        if (operation.getUseFlag()){//启用
            operation.setStartDate(new Date());
            operation.setEndDate(null);
        }else{//禁用
            operation.setEndDate(new Date());
        }
        baseMapper.insert(operation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOperation(Operation operation) {
//        dropUserRolesByUserId(user.getLoginId());
        if (operation.getUseFlag()){//启用
            operation.setStartDate(new Date());
            operation.setEndDate(null);
        }else{//禁用
            operation.setEndDate(new Date());
        }
        baseMapper.updateById(operation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOperation(Operation operation) {
        baseMapper.deleteById(operation.getOperationId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockOperation(Operation operation) {
        if (operation.getUseFlag()){
            operation.setUseFlag(false);
            operation.setEndDate(new Date());
        }else{
            operation.setUseFlag(true);
            operation.setEndDate(null);
        }
        baseMapper.updateById(operation);
    }
}
