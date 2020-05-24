package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.MachineLog;
import com.kexin.admin.entity.tables.WorkUnit;
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
     * 新增机台配置日志
     * @param workUnit
     * @param token
     */
    void saveMachineLog(WorkUnit workUnit,Integer token);


    /**
     * 保存机台配置日志
     * @param workUnit
     * @param token
     */
    void updateMachineLog(WorkUnit workUnit,Integer token);

    /**
     * 删除机台配置日志
     * @param workUnit
     * @param token
     */
    void deleteMachineLog(WorkUnit workUnit,Integer token);



}
