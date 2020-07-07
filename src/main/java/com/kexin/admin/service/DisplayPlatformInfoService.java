package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.DisplayPlatformInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 大屏详细配置,service接口层
 */
public interface DisplayPlatformInfoService extends IService<DisplayPlatformInfo> {

    /**
     * 根据全局配置的大屏id,获取该大屏的详细数据信息
     * @param displayPlatformId
     * @return
     */
    List<DisplayPlatformInfo> getDisplayPlatFormById(@Param("displayPlatformId") Integer displayPlatformId);


    /**
     * 一个大屏下的一个机器只能有一个配置,返回这个大屏下的这个设备的配置数目
     * @param displayPlatformInfo
     * @return
     */
    Integer countByMachineAndPlatform(@Param("displayPlatformInfo") DisplayPlatformInfo displayPlatformInfo);

    /**
     * 保存大屏详细
     * @param displayPlatformInfo
     */
    void saveDisplayPlatformInfo(@Param("displayPlatformInfo") DisplayPlatformInfo displayPlatformInfo);


    /**
     * 修改更新大屏详细
     * @param displayPlatformInfo
     */
    void updateDisplayPlatformInfo(@Param("displayPlatformInfo") DisplayPlatformInfo displayPlatformInfo);

    /**
     * 删除大屏详细(单个)
     * @param displayPlatformInfo
     */
    void deleteDisplayPlatformInfo(@Param("displayPlatformInfo") DisplayPlatformInfo displayPlatformInfo);

    /**
     * 禁用或者启用大屏详细
     * @param displayPlatformInfo
     */
    void lockDisplayPlatformInfo(@Param("displayPlatformInfo") DisplayPlatformInfo displayPlatformInfo);
}
