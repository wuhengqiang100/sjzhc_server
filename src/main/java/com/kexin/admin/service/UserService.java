package com.kexin.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.Role;
import com.kexin.admin.entity.tables.User;
import com.kexin.common.util.ResponseEntity;

import java.util.List;


public interface UserService extends IService<User> {
    /**
     * 用户登陆服务
     * @param userName
     * @param password
     * @return
     */
    ResponseEntity userLogin(String userName,String password);

    IPage<User> selectUserPage(Page<User> page, Integer useFlag, String userName);

    ResponseEntity forbiddenUser(int userId);

    Boolean updateRoleString(Role oldRole, Role newRole);

    /**
     * 获取用户数据,带身份以及角色roleString字段
     * @param userId
     * @return
     */
    User findUserById(int userId);

    /**
     * 用户的基础信息更改
     * 包括用户编号
     * 用户名称
     * 账户名
     * 备注
     * 4个字段
     * @param user
     * @return
     */
    Integer updateUserinfo(User user);

    /**
     * 获取用户的所用信息,特别是角色信息
     * @param userId
     * @return
     */
    User getUserAllInfoById(int userId);

    /**
     * 根据user里面的,用户编号,用户名称,账户名称查询该字段用的数量
     * @param user
     * @return
     */
    Integer selectByMapCount(User user);

    /**
     * 根据角色id获取该类角色下的用户
     * @param roleId
     * @return
     */
    List<User> selectStaffList(int roleId);

    /**
     * 用户解锁服务
     * @param userId
     * @param password
     * @return
     */
    ResponseEntity userLock(String userId,String password);
}
