package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.Role;
import com.kexin.admin.entity.vo.QaInspectChange;
import com.kexin.admin.entity.vo.webQuery.RoleChange;
import com.kexin.common.util.ResponseEty;
import org.apache.ibatis.annotations.Param;

public interface RoleService extends IService<Role> {


    /**
     * 根据roleId[]数组获取角色string,用于显示
     * @param roleIds
     * @return
     */
    String getRoleString(Integer[] roleIds);

    /**
     * 获取所有的角色checkbox用,option
     * @return
     */
    String[] listRoleOption();

    /**
     * 获取已拥有的角色
     * @param userId
     * @return
     */
    Integer[] getRoleOptionOwn(@Param("userId") Integer userId);


    /**
     * 根据角色名称计算数量
     * @param roleName
     * @return
     */
    Integer roleCountByName(@Param("roleName") String roleName);

  /*  *//**
     * 保存角色
     * @param role
     *//*
    void saveRole(@Param("role") Role role);
*/
    /**
     * 修改更新角色
     * @param role
     */
    ResponseEty saveRole(@Param("role") Role role,Integer token);
    /**
     * 修改更新角色
     * @param role
     */
    ResponseEty updateRole(@Param("role") Role role,Integer token);

    /**
     * 删除角色(单个)
     * @param role
     */
    void deleteRole(@Param("role") Role role);

    /**
     * 禁用或者启用角色
     * @param role
     */
    void lockRole(@Param("role") Role role);



}
