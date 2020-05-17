package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.AuditParameter;
import com.kexin.admin.mapper.AuditParameterMapper;
import com.kexin.admin.service.AuditParameterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 工序配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AuditParameterServiceImpl extends ServiceImpl<AuditParameterMapper, AuditParameter> implements AuditParameterService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)


    @Override
    public Integer countParameterByOperationProduct(AuditParameter auditParameter) {
        QueryWrapper<AuditParameter> auditParameterQueryWrapper=new QueryWrapper<>();
        auditParameterQueryWrapper.eq("OPERATION_ID",auditParameter.getOperationId())
                .eq("PRODUCT_ID",auditParameter.getProductId());
        return baseMapper.selectCount(auditParameterQueryWrapper);
    }

    @Override
    public Integer auditParameterCountByCode(String auditParameterCode) {
        QueryWrapper<AuditParameter> wrapper = new QueryWrapper<>();
        wrapper.eq("JUDGE_CHECK_CODE",auditParameterCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer auditParameterCountByName(String auditParameterName) {
        QueryWrapper<AuditParameter> wrapper = new QueryWrapper<>();
        wrapper.eq("JUDGE_CHECK_NAME",auditParameterName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAuditParameter(AuditParameter auditParameter) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        if (auditParameter.getUseFlag()){//启用
            auditParameter.setStartDate(new Date());
            auditParameter.setEndDate(null);
        }else{//禁用
            auditParameter.setEndDate(new Date());
        }
        baseMapper.insert(auditParameter);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAuditParameter(AuditParameter auditParameter) {
//        dropUserRolesByUserId(user.getLoginId());
        if (auditParameter.getUseFlag()){//启用
            auditParameter.setStartDate(new Date());
            auditParameter.setEndDate(null);
        }else{//禁用
            auditParameter.setEndDate(new Date());
        }
        baseMapper.updateById(auditParameter);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAuditParameter(AuditParameter auditParameter) {
        baseMapper.deleteById(auditParameter.getJudgeCheckId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockAuditParameter(AuditParameter auditParameter) {
        if (auditParameter.getUseFlag()){
            auditParameter.setUseFlag(false);
            auditParameter.setEndDate(new Date());
        }else{
            auditParameter.setUseFlag(true);
            auditParameter.setEndDate(null);
        }
        baseMapper.updateById(auditParameter);
    }
}
