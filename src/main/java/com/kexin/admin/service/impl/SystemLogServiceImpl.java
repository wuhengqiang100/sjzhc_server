package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.SystemLog;
import com.kexin.admin.mapper.SystemLogMapper;
import com.kexin.admin.service.SystemLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper,SystemLog> implements SystemLogService {

    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)

 
}
