package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.OperationLog;
import com.kexin.admin.mapper.OperationLogMapper;
import com.kexin.admin.service.OperationLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper,OperationLog> implements OperationLogService {

    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)

    @Override
    public Integer operationLogCountByCode(String operationLogCode) {
        QueryWrapper<OperationLog> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_CODE",operationLogCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer operationLogCountByName(String operationLogName) {
        QueryWrapper<OperationLog> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_NAME",operationLogName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOperationLog(OperationLog operationLog) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        baseMapper.insert(operationLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOperationLog(OperationLog operationLog) {
//        dropUserRolesByUserId(user.getLoginId());
        baseMapper.updateById(operationLog);
    }

    @Override
    public void deleteOperationLog(OperationLog operationLog) {

    }

    @Override
    public void lockOperationLog(OperationLog operationLog) {

    }

/*    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOperationLog(OperationLog operationLog) {
        baseMapper.deleteById(operationLog.getOperationLogId());
    }

    @Override
    public void lockOperationLog(OperationLog operationLog) {
        operationLog.setUseFlag(operationLog.getUseFlag()==1?0:1);
        baseMapper.updateById(operationLog);
    }*/


}
