package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.SystemSet;
import com.kexin.common.util.ResponseEty;
import org.apache.ibatis.annotations.Param;

/**
 * 系统配置配置,service接口层
 */
public interface SystemSetService extends IService<SystemSet> {


    /**
     * 获取系统的配置项
     * @param factoryId
     * @return
     */
    SystemSet getSystemSetById(@Param("factoryId") Integer factoryId);

    /**
     * 系统配置只能有一条
     * @return
     */
    SystemSet getSystemSet();

    /**
     * 保存系统配置
     * @param systemSet
     */
    void saveSystemSet(@Param("systemSet") SystemSet systemSet);


    /**
     * 修改更新系统配置
     * @param systemSet
     */
    void updateSystemSet(@Param("systemSet") SystemSet systemSet);


}
