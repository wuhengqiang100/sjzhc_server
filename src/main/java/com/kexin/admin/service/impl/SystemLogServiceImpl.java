package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.LoginUser;
import com.kexin.admin.entity.tables.Operator;
import com.kexin.admin.entity.tables.SystemLog;
import com.kexin.admin.mapper.LoginUserMapper;
import com.kexin.admin.mapper.OperatorMapper;
import com.kexin.admin.mapper.SystemLogMapper;
import com.kexin.admin.service.SystemLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;


@Service
@Transactional(rollbackFor = Exception.class)
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper,SystemLog> implements SystemLogService {
    @Resource
    LoginUserMapper loginUserMapper;//登陆用户mapper

    @Resource
    OperatorMapper operatorMapper;//用户mapper

    @Override
    public void saveMachineLog(Integer token, String logType, String note) {
        LoginUser loginUser=loginUserMapper.selectById(token);
        Operator operator=operatorMapper.selectById(loginUser.getOperatorId());

        note=operator.getOperatorName() +"  " +note;
        SystemLog systemLog=new SystemLog();
        systemLog.setLogDate(new Date());
        systemLog.setLogType(logType);
        systemLog.setNote(note);
        systemLog.setOperatorId(loginUser.getOperatorId());
        systemLog.setOperatorName(operator.getOperatorName());
        baseMapper.insert(systemLog);
    }

    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)

 
}
