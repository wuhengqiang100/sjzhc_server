package com.kexin.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.GrantRoleFunctions;
import com.kexin.admin.entity.tables.Role;

import java.util.List;
import java.util.Map;

/**
 * 角色接口层
 */
public interface RoleMapper extends BaseMapper<Role> {


    /**
     * <p>
     * 查询 : 根据state状态查询用户列表，分页显示
     * 注意!!: 如果入参是有多个,需要加注解指定参数名才能在xml中取值
     * </p>
     *
     * @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
     * @param state 状态
     * @return 分页对象
     */
//    IPage<Role> selectPageRole(Page page, @Param("USE_FLAG") Integer useFlag);
/*    IPage<Role> selectPageRole(Page<Role> page, Integer useFlag,String roleName);
    IPage<Role> selectUserPageNotGroupById(Page<Role> page, Integer useFlag);

    Boolean updateGrantRoleAndFunctions(int roleId,int functionId);

    Role getByMap(Role role);

    Role getByMapOr(Role role);

    Role saveNewRole(Role role);

    Integer updateRole(Role role);

    Integer deleteGrantRole(int roleId);

    Integer forbiddenRole(int roleId);

    *//**
     * 根据账户id查已经拥有的角色list
     * @param operatorId
     * @return
     *//*
    List<Role> getOwnRoleByOperatorId(int operatorId);

    Integer deleteGrantOperatorAndRole(int operatorId);

    Integer insertGrantOperatorAndRole(int operatorId,int roleId);

    List<GrantRoleFunctions> getFunctionIdsByRoleId(int roleId);*/
}
