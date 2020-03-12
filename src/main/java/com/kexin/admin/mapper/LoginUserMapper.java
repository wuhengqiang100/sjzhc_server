package com.kexin.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kexin.admin.entity.tables.LoginUser;
import org.apache.ibatis.annotations.Param;


public interface LoginUserMapper extends BaseMapper<LoginUser> {

    LoginUser selectLoginUserByName(@Param("userName") String userName);
    

}