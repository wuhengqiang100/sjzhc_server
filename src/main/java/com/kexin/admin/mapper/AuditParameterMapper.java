package com.kexin.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kexin.admin.entity.tables.AuditParameter;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 审核参数mapper接口层
 */
public interface AuditParameterMapper extends BaseMapper<AuditParameter> {

    String getAuditParameterFirst();

    List<Map<String,Object>> getAuditParameterSecond(@Param("sqlQuery") String sqlQuery);
}
