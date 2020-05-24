package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.QueryReportQa;
import com.kexin.admin.mapper.QueryReportQaMapper;
import com.kexin.admin.service.QueryReportQaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class QueryReportQaServiceImpl extends ServiceImpl<QueryReportQaMapper, QueryReportQa> implements QueryReportQaService {






    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)

  



}
