package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.Device;
import com.kexin.admin.mapper.DeviceMapper;
import com.kexin.admin.service.DeviceService;
import com.kexin.common.util.ResponseEty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 设备配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateUseFlag(Device device) {
        return baseMapper.updateUseFlag(device);
    }

    @Override
    public Integer deviceCountByCode(String deviceCode) {
        QueryWrapper<Device> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_CODE",deviceCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer deviceCountByName(String deviceName) {
        QueryWrapper<Device> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_NAME",deviceName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer deviceCountByIp(String deviceIp) {
        QueryWrapper<Device> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_IP",deviceIp);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDevice(Device device) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        baseMapper.insert(device);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDevice(Device device) {
//        dropUserRolesByUserId(user.getLoginId());
        baseMapper.updateById(device);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDevice(Device device) {
        baseMapper.deleteById(device.getMachineId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockDevice(Device device) {
        if (device.getUseFlag()){
            device.setUseFlag(false);
            device.setEndDate(new Date());
        }else{
            device.setUseFlag(true);
            device.setEndDate(null);
        }
        baseMapper.updateById(device);
    }
}
