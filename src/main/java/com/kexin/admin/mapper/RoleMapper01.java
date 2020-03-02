package com.kexin.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kexin.admin.entity.Menu;
import com.kexin.admin.entity.Role01;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

public interface RoleMapper01 extends BaseMapper<Role01> {

    void saveRoleMenus(@Param("roleId") String roleId,  @Param("menus") Set<Menu> menuSet);

    Role01 selectRoleById(@Param("roleId") String roleId);

    void dropRoleMenus(@Param("roleId") String roleId);

    void dropRoleUsers(@Param("roleId") String roleId);
}