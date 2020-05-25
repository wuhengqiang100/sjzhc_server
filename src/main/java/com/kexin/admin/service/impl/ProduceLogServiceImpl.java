package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.*;
import com.kexin.admin.mapper.LoginUserMapper;
import com.kexin.admin.mapper.OperatorMapper;
import com.kexin.admin.mapper.ProduceLogMapper;
import com.kexin.admin.service.ProduceLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;


@Service
@Transactional(rollbackFor = Exception.class)
public class ProduceLogServiceImpl extends ServiceImpl<ProduceLogMapper, ProduceLog> implements ProduceLogService {
    @Resource
    LoginUserMapper loginUserMapper;//登陆用户mapper

    @Resource
    OperatorMapper operatorMapper;//用户mapper

    @Override
    public void saveProduceLog(QaInspectMaster qaInspectMaster,Integer token, String logType, String note) {
        LoginUser loginUser=loginUserMapper.selectById(token);
        Operator operator=operatorMapper.selectById(loginUser.getOperatorId());

        note=operator.getOperatorName() +"  " +note;
        ProduceLog produceLog=new ProduceLog();
        produceLog.setLogDate(new Date());
        produceLog.setProductId(qaInspectMaster.getProduct().getProductId());
        produceLog.setCartNumber(qaInspectMaster.getWipJobs().getCartNumber());
        produceLog.setOperationId(qaInspectMaster.getOperation().getOperationId());
        produceLog.setOperatorId(operator.getOperatorId());
        produceLog.setOperatorName(operator.getOperatorName());
        produceLog.setLogType(logType);
        produceLog.setActionCount(1);
        produceLog.setNote(note);
        produceLog.setItemFlag(1);
        baseMapper.insert(produceLog);
    }

    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)



}
