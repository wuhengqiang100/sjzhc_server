package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.DeviceType;
import org.apache.ibatis.annotations.Param;

/**
 * 设备类别类别配置,service接口层
 */
public interface DeviceTypeService extends IService<DeviceType> {
    

    /**
     * 根据设备类别编码计算数量,当前机器的code的数量
     * @param machineTypeCode
     * @return
     */
    Integer machineTypeCountByCode(@Param("machineTypeCode") String machineTypeCode);


    /**
     * 根据设备类别名称计算数量
     * @param machineTypeName
     * @return
     */
    Integer machineTypeCountByName(@Param("machineTypeName") String machineTypeName);
    
    /**
     * 保存设备类别
     * @param machineType
     */
    void saveDeviceType(@Param("machineType") DeviceType machineType);


    /**
     * 修改更新设备类别
     * @param machineType
     */
    void updateDeviceType(@Param("machineType") DeviceType machineType);

    /**
     * 删除设备类别(单个)
     * @param machineType
     */
    void deleteDeviceType(@Param("machineType") DeviceType machineType);

    /**
     * 禁用或者启用设备类别
     * @param machineType
     */
    void lockDeviceType(@Param("machineType") DeviceType machineType);
}
