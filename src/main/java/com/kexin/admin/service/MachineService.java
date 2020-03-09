package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.Machine;
import com.kexin.admin.entity.tables.Machine;
import org.apache.ibatis.annotations.Param;


/**
 * 设备服务接口类
 */
public interface MachineService extends IService<Machine> {


    /**
     * 根据设备编码计算数量,当前机器的code的数量
     * @param machineCode
     * @return
     */
    Integer machineCountByCode(@Param("machineCode") String machineCode);


    /**
     * 根据设备名称计算数量
     * @param machineName
     * @return
     */
    Integer machineCountByName(@Param("machineName") String machineName);

    /**
     * 保存设备
     * @param machine
     */
    void saveMachine(@Param("machine") Machine machine);


    /**
     * 修改更新设备
     * @param machine
     */
    void updateMachine(@Param("machine") Machine machine);

    /**
     * 删除设备(单个)
     * @param machine
     */
    void deleteMachine(@Param("machine") Machine machine);

    /**
     * 禁用或者启用设备
     * @param machine
     */
    void lockMachine(@Param("machine") Machine machine);
}
