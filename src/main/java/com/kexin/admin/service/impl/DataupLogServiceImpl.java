package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.DataupLog;
import com.kexin.admin.mapper.DataupLogMapper;
import com.kexin.admin.service.DataupLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class DataupLogServiceImpl extends ServiceImpl<DataupLogMapper,DataupLog> implements DataupLogService {

    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)

    @Override
    public Integer dataupLogCountByCode(String dataupLogCode) {
        QueryWrapper<DataupLog> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_CODE",dataupLogCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer dataupLogCountByName(String dataupLogName) {
        QueryWrapper<DataupLog> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_NAME",dataupLogName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDataupLog(DataupLog dataupLog) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        baseMapper.insert(dataupLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDataupLog(DataupLog dataupLog) {
//        dropUserRolesByUserId(user.getLoginId());
        baseMapper.updateById(dataupLog);
    }

    @Override
    public void deleteDataupLog(DataupLog dataupLog) {

    }

    @Override
    public void lockDataupLog(DataupLog dataupLog) {

    }

/*    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDataupLog(DataupLog dataupLog) {
        baseMapper.deleteById(dataupLog.getDataupLogId());
    }

    @Override
    public void lockDataupLog(DataupLog dataupLog) {
        dataupLog.setUseFlag(dataupLog.getUseFlag()==1?0:1);
        baseMapper.updateById(dataupLog);
    }*/


}
