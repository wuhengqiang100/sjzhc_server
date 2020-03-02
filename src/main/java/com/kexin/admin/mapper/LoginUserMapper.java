package com.kexin.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kexin.admin.entity.tables.LoginUser;
import org.apache.ibatis.annotations.Param;


public interface LoginUserMapper extends BaseMapper<LoginUser> {

    LoginUser selectLoginUserByName(@Param("userName") String userName);
    
    /*IPage<LoginUser> selectPageLoginUser(Page<LoginUser> page, Integer useFlag, String userName);

    
    List<LoginUser> selectLoginUserByRoleId(int roleId);
    *//**
     * 获取用户数据,带身份以及角色roleString字段
     * @param userId
     * @return
     *//*
    LoginUser findLoginUserById(int userId);

    *//**
     * 用户的基础信息更改
     * 包括用户编号
     * 用户名称
     * 账户名
     * 备注
     * 4个字段
     * @param user
     * @return
     *//*
    Integer updateLoginUserinfo(LoginUser user);

    *//**
     * 获取用户的所用信息,特别是角色信息
     * @param userId
     * @return
     *//*
    LoginUser getLoginUserAllInfoById(int userId);

    *//**
     * 根据user里面的,用户编号,用户名称,账户名称查询该字段用的数量
     * @param user
     * @return
     *//*
    Integer selectByMapCount(LoginUser user);

    *//**
     * 根据角色id获取该类角色下的用户
     * @param roleId
     * @return
     *//*
    List<LoginUser> selectStaffList(int roleId);*/
}