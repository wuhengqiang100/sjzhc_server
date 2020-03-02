package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.Device;
import com.kexin.common.util.ResponseEty;
import org.apache.ibatis.annotations.Param;

/**
 * 设备配置,service接口层
 */
public interface DeviceService extends IService<Device> {

    /**
     * 根据machineId更新useFlag禁用状态
     * @param device
     * @return
     */
    Integer updateUseFlag(@Param("device") Device device);

    /**
     * 根据机器编码计算数量,当前机器的code的数量
     * @param deviceCode
     * @return
     */
    Integer deviceCountByCode(@Param("deviceCode") String deviceCode);


    /**
     * 根据机器名称计算数量
     * @param deviceName
     * @return
     */
    Integer deviceCountByName(@Param("deviceName") String deviceName);

    /**
     * 根据机器Ip计算数量
     * @param deviceIp
     * @return
     */
    Integer deviceCountByIp(@Param("deviceIp") String deviceIp);
    /**
     * 保存设备
     * @param device
     */
    void saveDevice(@Param("device") Device device);


    /**
     * 修改更新设备
     * @param device
     */
    void updateDevice(@Param("device") Device device);

    /**
     * 删除设备(单个)
     * @param device
     */
    void deleteDevice(@Param("device") Device device);

    /**
     * 禁用或者启用设备
     * @param device
     */
    void lockDevice(@Param("device") Device device);
}
