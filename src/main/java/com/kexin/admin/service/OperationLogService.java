package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.OperationLog;
import org.apache.ibatis.annotations.Param;

public interface OperationLogService extends IService<OperationLog> {

/**
     * 根据机器编码计算数量,当前机器的code的数量
     * @param operationLogCode
     * @return
     */
    Integer operationLogCountByCode(@Param("operationLogCode") String operationLogCode);


    /**
     * 根据机器名称计算数量
     * @param operationLogName
     * @return
     */
    Integer operationLogCountByName(@Param("operationLogName") String operationLogName);

    /**
     * 保存上传日志
     * @param operationLog
     */
    void saveOperationLog(@Param("operationLog") OperationLog operationLog);


    /**
     * 修改更新上传日志
     * @param operationLog
     */
    void updateOperationLog(@Param("operationLog") OperationLog operationLog);

    /**
     * 删除上传日志(单个)
     * @param operationLog
     */
    void deleteOperationLog(@Param("operationLog") OperationLog operationLog);

    /**
     * 禁用或者启用上传日志
     * @param operationLog
     */
    void lockOperationLog(@Param("operationLog") OperationLog operationLog);

}
