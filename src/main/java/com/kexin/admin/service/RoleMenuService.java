package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.SysRoleMenus;
import org.apache.ibatis.annotations.Param;


/**
 * 角色菜单关系服务接口类
 */
public interface RoleMenuService extends IService<SysRoleMenus> {


    /**
     * 根据角色菜单关系编码计算数量,当前机器的code的数量
     * @param sysRoleMenusCode
     * @return
     */
    Integer sysRoleMenusCountByCode(@Param("sysRoleMenusCode") String sysRoleMenusCode);


    /**
     * 根据角色菜单关系名称计算数量
     * @param sysRoleMenusName
     * @return
     */
    Integer sysRoleMenusCountByName(@Param("sysRoleMenusName") String sysRoleMenusName);

    /**
     * 保存角色菜单关系
     * @param sysRoleMenus
     */
    void saveSysRoleMenus(@Param("sysRoleMenus") SysRoleMenus sysRoleMenus);


    /**
     * 修改更新角色菜单关系
     * @param sysRoleMenus
     */
    void updateSysRoleMenus(@Param("sysRoleMenus") SysRoleMenus sysRoleMenus);

    /**
     * 删除角色菜单关系(单个)
     * @param sysRoleMenus
     */
    void deleteSysRoleMenus(@Param("sysRoleMenus") SysRoleMenus sysRoleMenus);

    /**
     * 禁用或者启用角色菜单关系
     * @param sysRoleMenus
     */
    void lockSysRoleMenus(@Param("sysRoleMenus") SysRoleMenus sysRoleMenus);


    /**
     * 根据角色id,删除角色菜单关系表数据
     * @param roleId
     */
    void deleleByRoleId(@Param("roleId") Integer roleId);
}
