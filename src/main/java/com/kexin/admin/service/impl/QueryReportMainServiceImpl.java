package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.*;
import com.kexin.admin.mapper.*;
import com.kexin.admin.service.QueryReportMainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class QueryReportMainServiceImpl extends ServiceImpl<QueryReportMainMapper, QueryReportMain> implements QueryReportMainService {






    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)

  



}
