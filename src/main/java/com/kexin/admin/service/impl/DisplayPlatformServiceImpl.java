package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.DisplayPlatform;
import com.kexin.admin.mapper.DisplayPlatformMapper;
import com.kexin.admin.service.DisplayPlatformService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 大屏配置配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DisplayPlatformServiceImpl extends ServiceImpl<DisplayPlatformMapper, DisplayPlatform> implements DisplayPlatformService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)



    @Override
    public Integer displayPlatformCountByCode(String displayPlatformCode) {
        QueryWrapper<DisplayPlatform> wrapper = new QueryWrapper<>();
        wrapper.eq("DISPLAY_PLATFORM_CODE",displayPlatformCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer displayPlatformCountByName(String displayPlatformName) {
        QueryWrapper<DisplayPlatform> wrapper = new QueryWrapper<>();
        wrapper.eq("DISPLAY_PLATFORM_NAME",displayPlatformName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDisplayPlatform(DisplayPlatform displayPlatform) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        if (displayPlatform.getUseFlag()){//启用
            displayPlatform.setStartDate(new Date());
            displayPlatform.setEndDate(null);
        }else{//禁用
            displayPlatform.setEndDate(new Date());
        }
        baseMapper.insert(displayPlatform);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDisplayPlatform(DisplayPlatform displayPlatform) {
//        dropUserRolesByUserId(user.getLoginId());
        if (displayPlatform.getUseFlag()){//启用
            displayPlatform.setStartDate(new Date());
            displayPlatform.setEndDate(null);
        }else{//禁用
            displayPlatform.setEndDate(new Date());
        }
        baseMapper.updateById(displayPlatform);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDisplayPlatform(DisplayPlatform displayPlatform) {
        baseMapper.deleteById(displayPlatform.getDisplayPlatformId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockDisplayPlatform(DisplayPlatform displayPlatform) {
        if (displayPlatform.getUseFlag()){
            displayPlatform.setUseFlag(false);
            displayPlatform.setEndDate(new Date());
        }else{
            displayPlatform.setUseFlag(true);
            displayPlatform.setEndDate(null);
        }
        baseMapper.updateById(displayPlatform);
    }

}
