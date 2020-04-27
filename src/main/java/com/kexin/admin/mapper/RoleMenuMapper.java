package com.kexin.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kexin.admin.entity.tables.SysRoleMenus;
import org.apache.ibatis.annotations.Param;

/**
 * 节点mapper接口层
 */
public interface RoleMenuMapper extends BaseMapper<SysRoleMenus> {


    /**
     * 根据角色id,删除角色菜单关系表数据
     * @param roleId
     */
    void deleleByRoleId(@Param("roleId") Integer roleId);
}
