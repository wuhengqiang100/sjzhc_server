package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.QueryReportNck;
import com.kexin.admin.mapper.QueryReportNckMapper;
import com.kexin.admin.service.QueryReportNckService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class QueryReportNckServiceImpl extends ServiceImpl<QueryReportNckMapper, QueryReportNck> implements QueryReportNckService {






    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)

  



}
