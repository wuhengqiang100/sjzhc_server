package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.DeviceType;
import com.kexin.admin.mapper.DeviceTypeMapper;
import com.kexin.admin.service.DeviceTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 设备类别配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DeviceTypeServiceImpl extends ServiceImpl<DeviceTypeMapper, DeviceType> implements DeviceTypeService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)
    
    @Override
    public Integer machineTypeCountByCode(String machineTypeCode) {
        QueryWrapper<DeviceType> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_TYPE_CODE",machineTypeCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer machineTypeCountByName(String machineTypeName) {
        QueryWrapper<DeviceType> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_TYPE_NAME",machineTypeName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDeviceType(DeviceType machineType) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        baseMapper.insert(machineType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDeviceType(DeviceType machineType) {
//        dropUserRolesByUserId(user.getLoginId());
        baseMapper.updateById(machineType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDeviceType(DeviceType machineType) {
        baseMapper.deleteById(machineType.getMachineTypeId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockDeviceType(DeviceType machineType) {
        if (machineType.getUseFlag()){
            machineType.setUseFlag(false);
            machineType.setEndDate(new Date());
        }else{
            machineType.setUseFlag(true);
            machineType.setEndDate(null);
        }
        baseMapper.updateById(machineType);
    }
}
