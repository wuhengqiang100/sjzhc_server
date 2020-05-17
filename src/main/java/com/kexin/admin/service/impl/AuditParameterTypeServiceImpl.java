package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.AuditParameterType;
import com.kexin.admin.entity.vo.webQuery.SelectOption;
import com.kexin.admin.mapper.AuditParameterTypeMapper;
import com.kexin.admin.service.AuditParameterTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 工序种类配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AuditParameterTypeServiceImpl extends ServiceImpl<AuditParameterTypeMapper, AuditParameterType> implements AuditParameterTypeService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)



    @Override
    public Integer auditParameterTypeCountByCode(String auditParameterTypeCode) {
        QueryWrapper<AuditParameterType> wrapper = new QueryWrapper<>();
        wrapper.eq("JUDGE_CHECK_TYPE_CODE",auditParameterTypeCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer auditParameterTypeCountByName(String auditParameterTypeName) {
        QueryWrapper<AuditParameterType> wrapper = new QueryWrapper<>();
        wrapper.eq("JUDGE_CHECK_TYPE_NAME",auditParameterTypeName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAuditParameterType(AuditParameterType auditParameterType) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        if (auditParameterType.getUseFlag()){//启用
            auditParameterType.setStartDate(new Date());
            auditParameterType.setEndDate(null);
        }else{//禁用
            auditParameterType.setEndDate(new Date());
        }
        baseMapper.insert(auditParameterType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAuditParameterType(AuditParameterType auditParameterType) {
//        dropUserRolesByUserId(user.getLoginId());
        if (auditParameterType.getUseFlag()){//启用
            auditParameterType.setStartDate(new Date());
            auditParameterType.setEndDate(null);
        }else{//禁用
            auditParameterType.setEndDate(new Date());
        }
        baseMapper.updateById(auditParameterType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAuditParameterType(AuditParameterType auditParameterType) {
        baseMapper.deleteById(auditParameterType.getJudgeCheckTypeId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockAuditParameterType(AuditParameterType auditParameterType) {
        if (auditParameterType.getUseFlag()){
            auditParameterType.setUseFlag(false);
            auditParameterType.setEndDate(new Date());
        }else{
            auditParameterType.setUseFlag(true);
            auditParameterType.setEndDate(null);
        }
        baseMapper.updateById(auditParameterType);
    }

    /**
     * 获取所有的工序种类的selectoption
     * @return
     */
    @Override
    public List<SelectOption> getAuditParameterTypeSelectOption() {
        QueryWrapper<AuditParameterType> operatorQueryWrapper = new QueryWrapper<>();
        List<AuditParameterType> auditParameterTypeList= baseMapper.selectList(operatorQueryWrapper);
        List<SelectOption> selectOptionList=new ArrayList<>(auditParameterTypeList.size());
        SelectOption selectOption;
        for (AuditParameterType r : auditParameterTypeList) {
            selectOption=new SelectOption();
            selectOption.setValue(r.getJudgeCheckTypeId());
            selectOption.setLabel(r.getJudgeCheckTypeName());
            selectOptionList.add(selectOption);
        }
        return selectOptionList;
    }
}
