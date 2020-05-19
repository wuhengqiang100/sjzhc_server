package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.WorkUnit;
import com.kexin.admin.mapper.WorkUnitMapper;
import com.kexin.admin.service.WorkUnitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 机台配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WorkUnitServiceImpl extends ServiceImpl<WorkUnitMapper, WorkUnit> implements WorkUnitService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)



    @Override
    public Integer workUnitCountByCode(String workUnitCode) {
        QueryWrapper<WorkUnit> wrapper = new QueryWrapper<>();
        wrapper.eq("WORK_UNIT_CODE",workUnitCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer workUnitCountByName(String workUnitName) {
        QueryWrapper<WorkUnit> wrapper = new QueryWrapper<>();
        wrapper.eq("WORK_UNIT_NAME",workUnitName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveWorkUnit(WorkUnit workUnit) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        if (workUnit.getUseFlag()){//启用
            workUnit.setStartDate(new Date());
            workUnit.setEndDate(null);
        }else{//禁用
            workUnit.setEndDate(new Date());
        }
        baseMapper.insert(workUnit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWorkUnit(WorkUnit workUnit) {
//        dropUserRolesByUserId(user.getLoginId());
        if (workUnit.getUseFlag()){//启用
            workUnit.setStartDate(new Date());
            workUnit.setEndDate(null);
        }else{//禁用
            workUnit.setEndDate(new Date());
        }
        baseMapper.updateById(workUnit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteWorkUnit(WorkUnit workUnit) {
        baseMapper.deleteById(workUnit.getWorkUnitId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockWorkUnit(WorkUnit workUnit) {
        if (workUnit.getUseFlag()){
            workUnit.setUseFlag(false);
            workUnit.setEndDate(new Date());
        }else{
            workUnit.setUseFlag(true);
            workUnit.setEndDate(null);
        }
        baseMapper.updateById(workUnit);
    }
}
