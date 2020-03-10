package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.DataupLog;
import org.apache.ibatis.annotations.Param;

public interface DataupLogService extends IService<DataupLog> {

/**
     * 根据机器编码计算数量,当前机器的code的数量
     * @param dataupLogCode
     * @return
     */
    Integer dataupLogCountByCode(@Param("dataupLogCode") String dataupLogCode);


    /**
     * 根据机器名称计算数量
     * @param dataupLogName
     * @return
     */
    Integer dataupLogCountByName(@Param("dataupLogName") String dataupLogName);

    /**
     * 保存上传日志
     * @param dataupLog
     */
    void saveDataupLog(@Param("dataupLog") DataupLog dataupLog);


    /**
     * 修改更新上传日志
     * @param dataupLog
     */
    void updateDataupLog(@Param("dataupLog") DataupLog dataupLog);

    /**
     * 删除上传日志(单个)
     * @param dataupLog
     */
    void deleteDataupLog(@Param("dataupLog") DataupLog dataupLog);

    /**
     * 禁用或者启用上传日志
     * @param dataupLog
     */
    void lockDataupLog(@Param("dataupLog") DataupLog dataupLog);

}
