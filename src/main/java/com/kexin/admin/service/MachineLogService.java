package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.MachineLog;
import org.apache.ibatis.annotations.Param;

public interface MachineLogService extends IService<MachineLog> {

/**
     * 根据机器编码计算数量,当前机器的code的数量
     * @param machineLogCode
     * @return
     */
    Integer machineLogCountByCode(@Param("machineLogCode") String machineLogCode);


    /**
     * 根据机器名称计算数量
     * @param machineLogName
     * @return
     */
    Integer machineLogCountByName(@Param("machineLogName") String machineLogName);

    /**
     * 保存设备日志
     * @param machineLog
     */
    void saveMachineLog(@Param("machineLog") MachineLog machineLog);


    /**
     * 修改更新设备日志
     * @param machineLog
     */
    void updateMachineLog(@Param("machineLog") MachineLog machineLog);

    /**
     * 删除设备日志(单个)
     * @param machineLog
     */
    void deleteMachineLog(@Param("machineLog") MachineLog machineLog);

    /**
     * 禁用或者启用设备日志
     * @param machineLog
     */
    void lockMachineLog(@Param("machineLog") MachineLog machineLog);

}
