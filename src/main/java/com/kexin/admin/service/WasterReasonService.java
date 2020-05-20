package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.WasterReason;
import org.apache.ibatis.annotations.Param;

/**
 * 错误类型配置,service接口层
 */
public interface WasterReasonService extends IService<WasterReason> {


    /**
     * 根据机器编码计算数量,当前机器的code的数量
     * @param wasterReasonCode
     * @return
     */
    Integer wasterReasonCountByCode(@Param("wasterReasonCode") String wasterReasonCode);


    /**
     * 根据机器名称计算数量
     * @param wasterReasonName
     * @return
     */
    Integer wasterReasonCountByName(@Param("wasterReasonName") String wasterReasonName);

    /**
     * 保存错误类型
     * @param wasterReason
     */
    void saveWasterReason(@Param("wasterReason") WasterReason wasterReason);


    /**
     * 修改更新错误类型
     * @param wasterReason
     */
    void updateWasterReason(@Param("wasterReason") WasterReason wasterReason);

    /**
     * 删除错误类型(单个)
     * @param wasterReason
     */
    void deleteWasterReason(@Param("wasterReason") WasterReason wasterReason);

    /**
     * 禁用或者启用错误类型
     * @param wasterReason
     */
    void lockWasterReason(@Param("wasterReason") WasterReason wasterReason);
}
