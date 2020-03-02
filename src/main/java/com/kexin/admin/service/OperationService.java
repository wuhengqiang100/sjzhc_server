package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.Operation;
import org.apache.ibatis.annotations.Param;

/**
 * 节点配置,service接口层
 */
public interface OperationService extends IService<Operation> {


    /**
     * 根据机器编码计算数量,当前机器的code的数量
     * @param operationCode
     * @return
     */
    Integer operationCountByCode(@Param("operationCode") String operationCode);


    /**
     * 根据机器名称计算数量
     * @param operationName
     * @return
     */
    Integer operationCountByName(@Param("operationName") String operationName);

    /**
     * 保存节点
     * @param operation
     */
    void saveOperation(@Param("operation") Operation operation);


    /**
     * 修改更新节点
     * @param operation
     */
    void updateOperation(@Param("operation") Operation operation);

    /**
     * 删除节点(单个)
     * @param operation
     */
    void deleteOperation(@Param("operation") Operation operation);

    /**
     * 禁用或者启用节点
     * @param operation
     */
    void lockOperation(@Param("operation") Operation operation);
}
