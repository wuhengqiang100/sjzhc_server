package com.kexin.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.LoginUser;
import com.kexin.admin.entity.tables.Role;
import com.kexin.admin.entity.tables.User;
import com.kexin.common.util.ResponseEntity;
import com.kexin.common.util.ResponseEty;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface LoginUserService extends IService<LoginUser> {

    ResponseEty login(Map map);


    ResponseEty userInfo(String token);

}
