package com.kexin.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kexin.admin.entity.tables.SysFunctions;

/**
 * 菜单mapper接口层
 */
public interface SysFunctionMapper extends BaseMapper<SysFunctions> {

    /**
     * 计算这个权限使用的次数
     * @param functionId
     * @return
     */
    Integer countFunctionUseNum(Integer functionId);
}
