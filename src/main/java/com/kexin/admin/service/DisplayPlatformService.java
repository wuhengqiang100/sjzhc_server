package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.DisplayPlatform;
import com.kexin.common.util.ResponseEty;
import org.apache.ibatis.annotations.Param;

/**
 * 大屏配置,service接口层
 */
public interface DisplayPlatformService extends IService<DisplayPlatform> {

    /**
     * 根据配置获取监控表大屏相关配置
     * @param id
     * @return
     */
    ResponseEty getDisplayPlatform(Integer id);


    /**
     * 根据机器编码计算数量,当前机器的code的数量
     * @param displayPlatformCode
     * @return
     */
    Integer displayPlatformCountByCode(@Param("displayPlatformCode") String displayPlatformCode);


    /**
     * 根据机器名称计算数量
     * @param displayPlatformName
     * @return
     */
    Integer displayPlatformCountByName(@Param("displayPlatformName") String displayPlatformName);

    /**
     * 保存大屏配置
     * @param displayPlatform
     */
    void saveDisplayPlatform(@Param("displayPlatform") DisplayPlatform displayPlatform);


    /**
     * 修改更新大屏配置
     * @param displayPlatform
     */
    void updateDisplayPlatform(@Param("displayPlatform") DisplayPlatform displayPlatform);

    /**
     * 删除大屏配置(单个)
     * @param displayPlatform
     */
    void deleteDisplayPlatform(@Param("displayPlatform") DisplayPlatform displayPlatform);

    /**
     * 禁用或者启用大屏配置
     * @param displayPlatform
     */
    void lockDisplayPlatform(@Param("displayPlatform") DisplayPlatform displayPlatform);
}
