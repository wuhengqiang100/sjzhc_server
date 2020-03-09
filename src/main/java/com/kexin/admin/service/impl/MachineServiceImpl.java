package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.Machine;
import com.kexin.admin.mapper.MachineMapper;
import com.kexin.admin.service.MachineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 工序配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MachineServiceImpl extends ServiceImpl<MachineMapper, Machine> implements MachineService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)



    @Override
    public Integer machineCountByCode(String machineCode) {
        QueryWrapper<Machine> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_CODE",machineCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer machineCountByName(String machineName) {
        QueryWrapper<Machine> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_NAME",machineName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMachine(Machine machine) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        baseMapper.insert(machine);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMachine(Machine machine) {
//        dropUserRolesByUserId(user.getLoginId());
        baseMapper.updateById(machine);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMachine(Machine machine) {
        baseMapper.deleteById(machine.getMachineId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockMachine(Machine machine) {
        if (machine.getUseFlag()){
            machine.setUseFlag(false);
            machine.setEndDate(new Date());
        }else{
            machine.setUseFlag(true);
            machine.setEndDate(null);
        }
        baseMapper.updateById(machine);
    }
}
