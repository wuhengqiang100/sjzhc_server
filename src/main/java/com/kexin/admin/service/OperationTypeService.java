package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.OperationType;
import com.kexin.admin.entity.vo.webQuery.SelectOption;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工序种类配置,service接口层
 */
public interface OperationTypeService extends IService<OperationType> {


    /**
     * 根据机器编码计算数量,当前机器的code的数量
     * @param operationTypeCode
     * @return
     */
    Integer operationTypeCountByCode(@Param("operationTypeCode") String operationTypeCode);


    /**
     * 根据机器名称计算数量
     * @param operationTypeName
     * @return
     */
    Integer operationTypeCountByName(@Param("operationTypeName") String operationTypeName);

    /**
     * 保存工序种类
     * @param operationType
     */
    void saveOperationType(@Param("operationType") OperationType operationType);


    /**
     * 修改更新工序种类
     * @param operationType
     */
    void updateOperationType(@Param("operationType") OperationType operationType);

    /**
     * 删除工序种类(单个)
     * @param operationType
     */
    void deleteOperationType(@Param("operationType") OperationType operationType);

    /**
     * 禁用或者启用工序种类
     * @param operationType
     */
    void lockOperationType(@Param("operationType") OperationType operationType);

    /**
     * 获取查询条件的工序种类select option
     * @return
     */
    List<SelectOption> getOperationTypeSelectOption();
}
