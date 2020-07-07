package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.DisplayPlatformInfo;
import com.kexin.admin.mapper.DisplayPlatformInfoMapper;
import com.kexin.admin.service.DisplayPlatformInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 大屏详细配置配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DisplayPlatformInfoServiceImpl extends ServiceImpl<DisplayPlatformInfoMapper, DisplayPlatformInfo> implements DisplayPlatformInfoService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)


    @Override
    public List<DisplayPlatformInfo> getDisplayPlatFormById(Integer displayPlatformId) {
        QueryWrapper<DisplayPlatformInfo> displayPlatformInfoQueryWrapper=new QueryWrapper<>();
        displayPlatformInfoQueryWrapper
                .eq("DISPLAY_PLATFORM_ID",displayPlatformId)
                .orderByAsc("ORDER_NUM");
        List<DisplayPlatformInfo> displayPlatformInfoList=baseMapper.selectList(displayPlatformInfoQueryWrapper);
        return displayPlatformInfoList;
    }

    @Override
    public Integer countByMachineAndPlatform(DisplayPlatformInfo displayPlatformInfo) {
        QueryWrapper<DisplayPlatformInfo> displayPlatformInfoQueryWrapper=new QueryWrapper<>();
        displayPlatformInfoQueryWrapper
                .eq("DISPLAY_PLATFORM_ID",displayPlatformInfo.getDisplayPlatformId())
                .eq("MACHINE_ID",displayPlatformInfo.getMachineId());
        Integer count=baseMapper.selectCount(displayPlatformInfoQueryWrapper);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDisplayPlatformInfo(DisplayPlatformInfo displayPlatformInfo) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        if (displayPlatformInfo.getUseFlag()){//启用
            displayPlatformInfo.setStartDate(new Date());
            displayPlatformInfo.setEndDate(null);
        }else{//禁用
            displayPlatformInfo.setEndDate(new Date());
        }
        baseMapper.insert(displayPlatformInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDisplayPlatformInfo(DisplayPlatformInfo displayPlatformInfo) {
//        dropUserRolesByUserId(user.getLoginId());
        if (displayPlatformInfo.getUseFlag()){//启用
            displayPlatformInfo.setStartDate(new Date());
            displayPlatformInfo.setEndDate(null);
        }else{//禁用
            displayPlatformInfo.setEndDate(new Date());
        }
        baseMapper.updateById(displayPlatformInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDisplayPlatformInfo(DisplayPlatformInfo displayPlatformInfo) {
        baseMapper.deleteById(displayPlatformInfo.getInfoId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockDisplayPlatformInfo(DisplayPlatformInfo displayPlatformInfo) {
        if (displayPlatformInfo.getUseFlag()){
            displayPlatformInfo.setUseFlag(false);
            displayPlatformInfo.setEndDate(new Date());
        }else{
            displayPlatformInfo.setUseFlag(true);
            displayPlatformInfo.setEndDate(null);
        }
        baseMapper.updateById(displayPlatformInfo);
    }
}
