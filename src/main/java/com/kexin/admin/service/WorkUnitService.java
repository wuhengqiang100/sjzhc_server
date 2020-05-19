package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.WorkUnit;
import org.apache.ibatis.annotations.Param;

/**
 * 机台配置,service接口层
 */
public interface WorkUnitService extends IService<WorkUnit> {


    /**
     * 根据机器编码计算数量,当前机器的code的数量
     * @param workUnitCode
     * @return
     */
    Integer workUnitCountByCode(@Param("workUnitCode") String workUnitCode);


    /**
     * 根据机器名称计算数量
     * @param workUnitName
     * @return
     */
    Integer workUnitCountByName(@Param("workUnitName") String workUnitName);

    /**
     * 保存机台
     * @param workUnit
     */
    void saveWorkUnit(@Param("workUnit") WorkUnit workUnit);


    /**
     * 修改更新机台
     * @param workUnit
     */
    void updateWorkUnit(@Param("workUnit") WorkUnit workUnit);

    /**
     * 删除机台(单个)
     * @param workUnit
     */
    void deleteWorkUnit(@Param("workUnit") WorkUnit workUnit);

    /**
     * 禁用或者启用机台
     * @param workUnit
     */
    void lockWorkUnit(@Param("workUnit") WorkUnit workUnit);
}
