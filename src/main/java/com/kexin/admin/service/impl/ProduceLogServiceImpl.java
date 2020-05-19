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



}
