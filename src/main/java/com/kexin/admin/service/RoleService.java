package com.kexin.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.GrantRoleFunctions;
import com.kexin.admin.entity.tables.Role;
import com.kexin.common.util.ResponseEntity;

import java.util.List;

public interface RoleService extends IService<Role> {

    IPage<Role> selecRolePage(Page<Role> page, Integer useFlag,String roleName);
    IPage<Role> selectUserPageNotGroupById(Page<Role> page, Integer useFlag);

    Boolean updateGrantRoleAndFunctions(int roleId,int functionId);

    Role getByMap(Role role);
    Role getByMapOr(Role role);
    Role saveNewRole(Role role);

    Integer updateRole(Role role);

    //删除角色关联表信息
    ResponseEntity deleteRoleAbout(int roleId);

    ResponseEntity forbiddenRole(int roleId);

    Integer deleteGrantRole(int roleId);


    Boolean updateGrantRoleAndOperator(int operatorId,int[] roleIds);

    /**
     * 根据账户id查已经拥有的角色list
     * @param operatorId
     * @return
     */
    List<Role> getOwnRoleByOperatorId(int operatorId);

    /**
     * 删除用户与角色之间的分组表
     * @param operatorId
     * @return
     */
    Integer deleteGrantOperatorAndRole(int operatorId);

    List<GrantRoleFunctions> getFunctionIdsByRoleId(int roleId);
}
