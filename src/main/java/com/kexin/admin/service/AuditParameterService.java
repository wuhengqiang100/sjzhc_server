package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.AuditParameter;
import com.kexin.admin.entity.vo.AuditParameter.AuditParameterDelete;
import com.kexin.admin.entity.vo.AuditParameter.AuditParameterDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 审核参数配置,service接口层
 */
public interface AuditParameterService extends IService<AuditParameter> {

    /**
     * 根据工序,产品,设备 获取参数list
     * @param auditParameter
     * @return
     */
    List<AuditParameterDetail> getAuditParameterDetail(AuditParameter auditParameter);


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
     * 删除工序+产品+设备下的所有配置数据
     * @param auditParameterDelete
     */
    Integer deleteAuditParameter(@Param("auditParameterDelete") AuditParameterDelete auditParameterDelete);

    /**
     * 禁用或者启用审核参数
     * @param auditParameter
     */
    void lockAuditParameter(@Param("auditParameter") AuditParameter auditParameter);
}
