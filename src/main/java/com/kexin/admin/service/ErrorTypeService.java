package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.ErrorType;
import org.apache.ibatis.annotations.Param;

/**
 * 错误类型配置,service接口层
 */
public interface ErrorTypeService extends IService<ErrorType> {


    /**
     * 根据机器编码计算数量,当前机器的code的数量
     * @param errorTypeCode
     * @return
     */
    Integer errorTypeCountByCode(@Param("errorTypeCode") String errorTypeCode);


    /**
     * 根据机器名称计算数量
     * @param errorTypeName
     * @return
     */
    Integer errorTypeCountByName(@Param("errorTypeName") String errorTypeName);

    /**
     * 保存错误类型
     * @param errorType
     */
    void saveErrorType(@Param("errorType") ErrorType errorType);


    /**
     * 修改更新错误类型
     * @param errorType
     */
    void updateErrorType(@Param("errorType") ErrorType errorType);

    /**
     * 删除错误类型(单个)
     * @param errorType
     */
    void deleteErrorType(@Param("errorType") ErrorType errorType);

    /**
     * 禁用或者启用错误类型
     * @param errorType
     */
    void lockErrorType(@Param("errorType") ErrorType errorType);
}
