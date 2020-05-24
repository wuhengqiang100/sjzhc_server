package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.*;
import com.kexin.admin.mapper.LoginUserMapper;
import com.kexin.admin.mapper.MachineLogMapper;
import com.kexin.admin.mapper.OperatorMapper;
import com.kexin.admin.service.MachineLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Service
@Transactional(rollbackFor = Exception.class)
public class MachineLogServiceImpl extends ServiceImpl<MachineLogMapper,MachineLog> implements MachineLogService {

    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)

    @Resource
    LoginUserMapper loginUserMapper;//登陆用户mapper

    @Resource
    OperatorMapper operatorMapper;//用户mapper

    @Autowired
    HttpServletRequest req;

    @Override
    public Integer machineLogCountByCode(String machineLogCode) {
        QueryWrapper<MachineLog> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_CODE",machineLogCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer machineLogCountByName(String machineLogName) {
        QueryWrapper<MachineLog> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_NAME",machineLogName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMachineLog(WorkUnit workUnit, Integer token) {

        LoginUser loginUser=loginUserMapper.selectById(token);
        Operator operator=operatorMapper.selectById(loginUser.getOperatorId());

        MachineLog machineLog=new MachineLog();
        machineLog.setOperatorId(loginUser.getOperatorId());
        machineLog.setLogDate(new Date());
        machineLog.setMachineIp(req.getRemoteAddr());
        machineLog.setLogInfo(operator.getOperatorName()+"新增了"+workUnit.getWorkUnitName()+"机台的配置");
//        machineLog.setMachineIp(workUnit);
        baseMapper.insert(machineLog);
    }

    @Override
    public void updateMachineLog(WorkUnit workUnit, Integer token) {


        LoginUser loginUser=loginUserMapper.selectById(token);
        Operator operator=operatorMapper.selectById(loginUser.getOperatorId());

        MachineLog machineLog=new MachineLog();
        machineLog.setOperatorId(loginUser.getOperatorId());
        machineLog.setLogDate(new Date());
        machineLog.setMachineIp(req.getRemoteAddr());

        machineLog.setLogInfo(operator.getOperatorName()+"更改了"+workUnit.getWorkUnitName()+"机台的配置");
//        machineLog.setMachineIp(workUnit);
        baseMapper.insert(machineLog);
    }

    @Override
    public void deleteMachineLog(WorkUnit workUnit, Integer token) {
        LoginUser loginUser=loginUserMapper.selectById(token);
        Operator operator=operatorMapper.selectById(loginUser.getOperatorId());

        MachineLog machineLog=new MachineLog();
        machineLog.setOperatorId(loginUser.getOperatorId());
        machineLog.setLogDate(new Date());
        machineLog.setMachineIp(req.getRemoteAddr());

        machineLog.setLogInfo(operator.getOperatorName()+"删除了"+workUnit.getWorkUnitName()+"机台的配置");
//        machineLog.setMachineIp(workUnit);
        baseMapper.insert(machineLog);
    }





}
