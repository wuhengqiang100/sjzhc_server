package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.LoginUser;
import com.kexin.common.util.ResponseEty;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpSession;
import java.util.Map;


public interface LoginUserService extends IService<LoginUser> {

    ResponseEty login(Map map, HttpSession session);

    ResponseEty userInfo(String token);

    LoginUser selectLoginUserByName(@Param("userName") String userName);



    /**
     * 根据用户名称计算数量
     * @param loginUserName
     * @return
     */
    Integer loginUserCountByName(@Param("loginUserName") String loginUserName);

    /**
     * 保存用户
     * @param loginUser
     */
    ResponseEty saveLoginUser(@Param("loginUser") LoginUser loginUser);


    /**
     * 修改更新用户
     * @param loginUser
     */
    ResponseEty updateLoginUser(@Param("loginUser") LoginUser loginUser);

    /**
     * 删除用户(单个)
     * @param id
     */
    ResponseEty deleteLoginUser( Integer id);

    /**
     * 禁用或者启用用户
     * @param loginUser
     */
    void lockLoginUser(@Param("loginUser") LoginUser loginUser);
}
