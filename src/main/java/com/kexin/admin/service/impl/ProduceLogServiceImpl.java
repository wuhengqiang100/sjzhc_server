package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.ProduceLog;
import com.kexin.admin.mapper.ProduceLogMapper;
import com.kexin.admin.service.ProduceLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class ProduceLogServiceImpl extends ServiceImpl<ProduceLogMapper, ProduceLog> implements ProduceLogService {

    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)

    @Override
    public Integer produceLogCountByCode(String produceLogCode) {
        QueryWrapper<ProduceLog> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_CODE",produceLogCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer produceLogCountByName(String produceLogName) {
        QueryWrapper<ProduceLog> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_NAME",produceLogName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveProduceLog(ProduceLog produceLog) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        baseMapper.insert(produceLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProduceLog(ProduceLog produceLog) {
//        dropUserRolesByUserId(user.getLoginId());
        baseMapper.updateById(produceLog);
    }

    @Override
    public void deleteProduceLog(ProduceLog produceLog) {

    }

    @Override
    public void lockProduceLog(ProduceLog produceLog) {

    }

/*    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProduceLog(ProduceLog produceLog) {
        baseMapper.deleteById(produceLog.getProduceLogId());
    }

    @Override
    public void lockProduceLog(ProduceLog produceLog) {
        produceLog.setUseFlag(produceLog.getUseFlag()==1?0:1);
        baseMapper.updateById(produceLog);
    }*/


}
