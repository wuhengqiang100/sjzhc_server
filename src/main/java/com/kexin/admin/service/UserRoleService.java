package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.SysUserRoles;
import org.apache.ibatis.annotations.Param;


/**
 * 用户用户角色服务接口类
 */
public interface UserRoleService extends IService<SysUserRoles> {


    /**
     * 根据用户用户角色编码计算数量,当前机器的code的数量
     * @param sysUserRolesCode
     * @return
     */
    Integer sysUserRolesCountByCode(@Param("sysUserRolesCode") String sysUserRolesCode);


    /**
     * 根据用户用户角色名称计算数量
     * @param sysUserRolesName
     * @return
     */
    Integer sysUserRolesCountByName(@Param("sysUserRolesName") String sysUserRolesName);

    /**
     * 保存用户用户角色
     * @param sysUserRoles
     */
    void saveSysUserRoles(@Param("sysUserRoles") SysUserRoles sysUserRoles);


    /**
     * 修改更新用户用户角色
     * @param sysUserRoles
     */
    void updateSysUserRoles(@Param("sysUserRoles") SysUserRoles sysUserRoles);

    /**
     * 删除用户用户角色(单个)
     * @param sysUserRoles
     */
    void deleteSysUserRoles(@Param("sysUserRoles") SysUserRoles sysUserRoles);

    /**
     * 禁用或者启用用户用户角色
     * @param sysUserRoles
     */
    void lockSysUserRoles(@Param("sysUserRoles") SysUserRoles sysUserRoles);

    /**
     * 根据用户id,删除用户和角色关系表数据
     * @param userId
     */
    void deleleByLoginId(@Param("userId") Integer userId);
}
