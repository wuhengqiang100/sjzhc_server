package com.kexin.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kexin.admin.entity.tables.SysUserRoles;
import org.apache.ibatis.annotations.Param;

/**
 * 节点mapper接口层
 */
public interface UserRoleMapper extends BaseMapper<SysUserRoles> {

    /**
     * 根据用户id,删除用户和角色关系表数据
     * @param loginId"
     */
    void deleleByLoginId(@Param("loginId") Integer loginId);
}
