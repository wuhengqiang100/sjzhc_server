package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.MachineLog;
import com.kexin.admin.mapper.MachineLogMapper;
import com.kexin.admin.service.MachineLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class MachineLogServiceImpl extends ServiceImpl<MachineLogMapper,MachineLog> implements MachineLogService {

    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)

    @Override
    public Integer machineLogCountByCode(String machineLogCode) {
        QueryWrapper<MachineLog> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_CODE",machineLogCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer machineLogCountByName(String machineLogName) {
        QueryWrapper<MachineLog> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_NAME",machineLogName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMachineLog(MachineLog machineLog) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        baseMapper.insert(machineLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMachineLog(MachineLog machineLog) {
//        dropUserRolesByUserId(user.getLoginId());
        baseMapper.updateById(machineLog);
    }

    @Override
    public void deleteMachineLog(MachineLog machineLog) {

    }

    @Override
    public void lockMachineLog(MachineLog machineLog) {

    }

/*    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMachineLog(MachineLog machineLog) {
        baseMapper.deleteById(machineLog.getMachineLogId());
    }

    @Override
    public void lockMachineLog(MachineLog machineLog) {
        machineLog.setUseFlag(machineLog.getUseFlag()==1?0:1);
        baseMapper.updateById(machineLog);
    }*/


}
