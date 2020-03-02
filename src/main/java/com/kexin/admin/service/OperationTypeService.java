package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.OperationType;
import org.apache.ibatis.annotations.Param;

/**
 * 节点类别配置,service接口层
 */
public interface OperationTypeService extends IService<OperationType> {
    

    /**
     * 根据节点类别编码计算数量,当前机器的code的数量
     * @param operationTypeCode
     * @return
     */
    Integer operationTypeCountByCode(@Param("operationTypeCode") String operationTypeCode);


    /**
     * 根据节点类别名称计算数量
     * @param operationTypeName
     * @return
     */
    Integer operationTypeCountByName(@Param("operationTypeName") String operationTypeName);
    
    /**
     * 保存节点类别
     * @param operationType
     */
    void saveOperationType(@Param("operationType") OperationType operationType);


    /**
     * 修改更新节点类别
     * @param operationType
     */
    void updateOperationType(@Param("operationType") OperationType operationType);

    /**
     * 删除节点类别(单个)
     * @param operationType
     */
    void deleteOperationType(@Param("operationType") OperationType operationType);

    /**
     * 禁用或者启用节点类别
     * @param operationType
     */
    void lockOperationType(@Param("operationType") OperationType operationType);
}
