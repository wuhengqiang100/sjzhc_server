package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.ProduceLog;
import org.apache.ibatis.annotations.Param;

public interface ProduceLogService extends IService<ProduceLog> {

/**
     * 根据机器编码计算数量,当前机器的code的数量
     * @param produceLogCode
     * @return
     */
    Integer produceLogCountByCode(@Param("produceLogCode") String produceLogCode);


    /**
     * 根据机器名称计算数量
     * @param produceLogName
     * @return
     */
    Integer produceLogCountByName(@Param("produceLogName") String produceLogName);

    /**
     * 保存上传日志
     * @param produceLog
     */
    void saveProduceLog(@Param("produceLog") ProduceLog produceLog);


    /**
     * 修改更新上传日志
     * @param produceLog
     */
    void updateProduceLog(@Param("produceLog") ProduceLog produceLog);

    /**
     * 删除上传日志(单个)
     * @param produceLog
     */
    void deleteProduceLog(@Param("produceLog") ProduceLog produceLog);

    /**
     * 禁用或者启用上传日志
     * @param produceLog
     */
    void lockProduceLog(@Param("produceLog") ProduceLog produceLog);

}
