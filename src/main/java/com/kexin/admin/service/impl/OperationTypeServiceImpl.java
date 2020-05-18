package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.OperationType;
import com.kexin.admin.entity.tables.Operator;
import com.kexin.admin.entity.vo.webQuery.SelectOption;
import com.kexin.admin.mapper.OperationTypeMapper;
import com.kexin.admin.service.OperationTypeService;
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
public class OperationTypeServiceImpl extends ServiceImpl<OperationTypeMapper, OperationType> implements OperationTypeService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)



    @Override
    public Integer operationTypeCountByCode(String operationTypeCode) {
        QueryWrapper<OperationType> wrapper = new QueryWrapper<>();
        wrapper.eq("OPERATION_TYPE_CODE",operationTypeCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer operationTypeCountByName(String operationTypeName) {
        QueryWrapper<OperationType> wrapper = new QueryWrapper<>();
        wrapper.eq("OPERATION_TYPE_NAME",operationTypeName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOperationType(OperationType operationType) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        if (operationType.getUseFlag()){//启用
            operationType.setStartDate(new Date());
            operationType.setEndDate(null);
        }else{//禁用
            operationType.setEndDate(new Date());
        }
        baseMapper.insert(operationType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOperationType(OperationType operationType) {
//        dropUserRolesByUserId(user.getLoginId());
        if (operationType.getUseFlag()){//启用
            operationType.setStartDate(new Date());
            operationType.setEndDate(null);
        }else{//禁用
            operationType.setEndDate(new Date());
        }
        baseMapper.updateById(operationType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOperationType(OperationType operationType) {
        baseMapper.deleteById(operationType.getOperationTypeId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockOperationType(OperationType operationType) {
        if (operationType.getUseFlag()){
            operationType.setUseFlag(false);
            operationType.setEndDate(new Date());
        }else{
            operationType.setUseFlag(true);
            operationType.setEndDate(null);
        }
        baseMapper.updateById(operationType);
    }

    /**
     * 获取所有的工序种类的selectoption
     * @return
     */
    @Override
    public List<SelectOption> getOperationTypeSelectOption() {
        QueryWrapper<OperationType> operatorQueryWrapper = new QueryWrapper<>();
        List<OperationType> operationTypeList= baseMapper.selectList(operatorQueryWrapper);
        List<SelectOption> selectOptionList=new ArrayList<>(operationTypeList.size());
        SelectOption selectOption;
        for (OperationType r : operationTypeList) {
            selectOption=new SelectOption();
            selectOption.setValue(r.getOperationTypeId());
            selectOption.setLabel(r.getOperationTypeName());
            selectOptionList.add(selectOption);
        }
        return selectOptionList;
    }
}
