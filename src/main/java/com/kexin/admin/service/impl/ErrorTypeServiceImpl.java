package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.ErrorType;
import com.kexin.admin.mapper.ErrorTypeMapper;
import com.kexin.admin.service.ErrorTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 错误类型配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ErrorTypeServiceImpl extends ServiceImpl<ErrorTypeMapper, ErrorType> implements ErrorTypeService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)



    @Override
    public Integer errorTypeCountByCode(String errorTypeCode) {
        QueryWrapper<ErrorType> wrapper = new QueryWrapper<>();
        wrapper.eq("ERR_TYPE_NOTE_CODE",errorTypeCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer errorTypeCountByName(String errorTypeName) {
        QueryWrapper<ErrorType> wrapper = new QueryWrapper<>();
        wrapper.eq("ERR_TYPE_NOTE_NAME",errorTypeName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveErrorType(ErrorType errorType) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        if (errorType.getUseFlag()){//启用
            errorType.setStartDate(new Date());
            errorType.setEndDate(null);
        }else{//禁用
            errorType.setEndDate(new Date());
        }
        baseMapper.insert(errorType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateErrorType(ErrorType errorType) {
//        dropUserRolesByUserId(user.getLoginId());
        if (errorType.getUseFlag()){//启用
            errorType.setStartDate(new Date());
            errorType.setEndDate(null);
        }else{//禁用
            errorType.setEndDate(new Date());
        }
        baseMapper.updateById(errorType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteErrorType(ErrorType errorType) {
        baseMapper.deleteById(errorType.getErrTypeNoteId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockErrorType(ErrorType errorType) {
        if (errorType.getUseFlag()){
            errorType.setUseFlag(false);
            errorType.setEndDate(new Date());
        }else{
            errorType.setUseFlag(true);
            errorType.setEndDate(null);
        }
        baseMapper.updateById(errorType);
    }
}
