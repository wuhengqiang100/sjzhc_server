package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.SystemSet;
import com.kexin.admin.mapper.SystemSetMapper;
import com.kexin.admin.service.SystemSetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 系统设置配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemSetServiceImpl extends ServiceImpl<SystemSetMapper, SystemSet> implements SystemSetService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)




    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSystemSet(SystemSet systemSet) {

    
        baseMapper.insert(systemSet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSystemSet(SystemSet systemSet) {

        baseMapper.updateById(systemSet);
    }

}
