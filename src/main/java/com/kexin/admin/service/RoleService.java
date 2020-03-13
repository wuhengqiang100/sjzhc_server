package com.kexin.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.GrantRoleFunctions;
import com.kexin.admin.entity.tables.Role;
import com.kexin.admin.entity.tables.Role;
import com.kexin.common.util.ResponseEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleService extends IService<Role> {





    /**
     * 根据角色名称计算数量
     * @param roleName
     * @return
     */
    Integer roleCountByName(@Param("roleName") String roleName);

    /**
     * 保存角色
     * @param role
     */
    void saveRole(@Param("role") Role role);


    /**
     * 修改更新角色
     * @param role
     */
    void updateRole(@Param("role") Role role);

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
