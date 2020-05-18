package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.AuditParameter;
import org.apache.ibatis.annotations.Param;

/**
 * 审核参数配置,service接口层
 */
public interface AuditParameterService extends IService<AuditParameter> {


    /**
     * 计算当前工序和产品下的配置参数唯一,count数量
     * @param auditParameter
     * @return
     */
    Integer countParameterByTypeOperationProductMachine(AuditParameter auditParameter);

    /**
     * 根据机器编码计算数量,当前机器的code的数量
     * @param auditParameterCode
     * @return
     */
    Integer auditParameterCountByCode(@Param("auditParameterCode") String auditParameterCode);


    /**
     * 根据机器名称计算数量
     * @param auditParameterName
     * @return
     */
    Integer auditParameterCountByName(@Param("auditParameterName") String auditParameterName);

    /**
     * 保存审核参数
     * @param auditParameter
     */
    void saveAuditParameter(@Param("auditParameter") AuditParameter auditParameter);


    /**
     * 修改更新审核参数
     * @param auditParameter
     */
    void updateAuditParameter(@Param("auditParameter") AuditParameter auditParameter);

    /**
     * 删除审核参数(单个)
     * @param auditParameter
     */
    void deleteAuditParameter(@Param("auditParameter") AuditParameter auditParameter);

    /**
     * 禁用或者启用审核参数
     * @param auditParameter
     */
    void lockAuditParameter(@Param("auditParameter") AuditParameter auditParameter);
}
