package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.ViewProduceLog;
import com.kexin.admin.mapper.ViewProduceLogMapper;
import com.kexin.admin.service.ViewProduceLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class ViewProduceLogServiceImpl extends ServiceImpl<ViewProduceLogMapper, ViewProduceLog> implements ViewProduceLogService {

    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)
    

}
