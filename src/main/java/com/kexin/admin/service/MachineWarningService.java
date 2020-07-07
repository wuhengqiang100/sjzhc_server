package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.MachineWarning;
import com.kexin.common.util.ResponseEty;
import org.apache.ibatis.annotations.Param;

/**
 * 设备报警配置,service接口层
 */
public interface MachineWarningService extends IService<MachineWarning> {


    /**
     * 根据机器编码计算数量,当前机器的code的数量
     * @param machineWarningCode
     * @return
     */
//    Integer machineWarningCountByCode(@Param("machineWarningCode") String machineWarningCode);


    /**
     * 根据机器名称计算数量
     * @param machineWarningName
     * @return
     */
//    Integer machineWarningCountByName(@Param("machineWarningName") String machineWarningName);

    /**
     * 获取没有处理的报警数据
     * @return
     */
    ResponseEty listNotDeal();

    /**
     * 保存设备报警
     * @param machineWarning
     */
    void saveMachineWarning(@Param("machineWarning") MachineWarning machineWarning);


    /**
     * 修改更新设备报警
     * @param machineWarning
     */
    void updateMachineWarning(@Param("machineWarning") MachineWarning machineWarning);

    /**
     * 删除设备报警(单个)
     * @param machineWarning
     */
    void deleteMachineWarning(@Param("machineWarning") MachineWarning machineWarning);

    /**
     * 禁用或者启用设备报警
     * @param machineWarning
     */
    void dealMachineWarning(@Param("machineWarning") MachineWarning machineWarning);
}
