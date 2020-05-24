package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.SystemSet;
import org.apache.ibatis.annotations.Param;

/**
 * 系统配置配置,service接口层
 */
public interface SystemSetService extends IService<SystemSet> {

    
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
