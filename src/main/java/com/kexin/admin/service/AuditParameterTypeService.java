package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.AuditParameterType;
import com.kexin.admin.entity.vo.webQuery.SelectOption;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 审核参数种类配置,service接口层
 */
public interface AuditParameterTypeService extends IService<AuditParameterType> {


    /**
     * 根据机器编码计算数量,当前机器的code的数量
     * @param auditParameterTypeCode
     * @return
     */
    Integer auditParameterTypeCountByCode(@Param("auditParameterTypeCode") String auditParameterTypeCode);


    /**
     * 根据机器名称计算数量
     * @param auditParameterTypeName
     * @return
     */
    Integer auditParameterTypeCountByName(@Param("auditParameterTypeName") String auditParameterTypeName);

    /**
     * 保存审核参数种类
     * @param auditParameterType
     */
    void saveAuditParameterType(@Param("auditParameterType") AuditParameterType auditParameterType);


    /**
     * 修改更新审核参数种类
     * @param auditParameterType
     */
    void updateAuditParameterType(@Param("auditParameterType") AuditParameterType auditParameterType);

    /**
     * 删除审核参数种类(单个)
     * @param auditParameterType
     */
    void deleteAuditParameterType(@Param("auditParameterType") AuditParameterType auditParameterType);

    /**
     * 禁用或者启用审核参数种类
     * @param auditParameterType
     */
    void lockAuditParameterType(@Param("auditParameterType") AuditParameterType auditParameterType);

    /**
     * 获取查询条件的审核参数种类select option
     * @return
     */
    List<SelectOption> getAuditParameterTypeSelectOption();
}
