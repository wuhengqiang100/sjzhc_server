package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.WasterReason;
import com.kexin.admin.mapper.WasterReasonMapper;
import com.kexin.admin.service.WasterReasonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 错误类型配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WasterReasonServiceImpl extends ServiceImpl<WasterReasonMapper, WasterReason> implements WasterReasonService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)



    @Override
    public Integer wasterReasonCountByCode(String wasterReasonCode) {
        QueryWrapper<WasterReason> wrapper = new QueryWrapper<>();
        wrapper.eq("WASTER_REASONS_CODE",wasterReasonCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer wasterReasonCountByName(String wasterReasonName) {
        QueryWrapper<WasterReason> wrapper = new QueryWrapper<>();
        wrapper.eq("WASTER_REASONS_NAME",wasterReasonName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveWasterReason(WasterReason wasterReason) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        if (wasterReason.getUseFlag()){//启用
            wasterReason.setStartDate(new Date());
            wasterReason.setEndDate(null);
        }else{//禁用
            wasterReason.setEndDate(new Date());
        }
        baseMapper.insert(wasterReason);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWasterReason(WasterReason wasterReason) {
//        dropUserRolesByUserId(user.getLoginId());
        if (wasterReason.getUseFlag()){//启用
            wasterReason.setStartDate(new Date());
            wasterReason.setEndDate(null);
        }else{//禁用
            wasterReason.setEndDate(new Date());
        }
        baseMapper.updateById(wasterReason);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteWasterReason(WasterReason wasterReason) {
        baseMapper.deleteById(wasterReason.getWasterReasonsId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockWasterReason(WasterReason wasterReason) {
        if (wasterReason.getUseFlag()){
            wasterReason.setUseFlag(false);
            wasterReason.setEndDate(new Date());
        }else{
            wasterReason.setUseFlag(true);
            wasterReason.setEndDate(null);
        }
        baseMapper.updateById(wasterReason);
    }
}
