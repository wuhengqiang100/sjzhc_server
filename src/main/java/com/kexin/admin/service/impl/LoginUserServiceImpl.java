package com.kexin.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.login.TokenUser;
import com.kexin.admin.entity.login.Tokens;
import com.kexin.admin.entity.tables.LoginUser;
import com.kexin.admin.entity.tables.User;
import com.kexin.admin.mapper.LoginUserMapper;
import com.kexin.admin.service.LoginUserService;
import com.kexin.common.util.ResponseEty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class LoginUserServiceImpl extends ServiceImpl<LoginUserMapper, LoginUser> implements LoginUserService {


    @Override
    public ResponseEty login(Map map) {

        if (map.size()!=2){
            return ResponseEty.failure("请输入用户名或者密码!");
        }
        String userName= (String) map.get("username");
        String password= (String) map.get("password");

        LoginUser loginUser=baseMapper.selectLoginUserByName(userName);
        ResponseEty responseEty=new ResponseEty();
        if (null==loginUser){
            responseEty.setSuccess(60204);
            responseEty.setMessage("没有这个用户");
            return responseEty;
        }
        if (password.equals(loginUser.getLoginPass())){
            Tokens tokens=new Tokens();
            tokens.setToken("admin-token");
            responseEty.setSuccess(20000);
            responseEty.setData(tokens);
            return responseEty;
        }
        return ResponseEty.failure("服务器请求失败!");
    }

    @Override
    public ResponseEty userInfo(String token) {
        ResponseEty responseEty=new ResponseEty();
        TokenUser tokenUser=new TokenUser();
        String[] roles=new String[1];
        roles[0]="admin";
        tokenUser.setRoles(roles);
        tokenUser.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        tokenUser.setIntroduction("I am a super administrator");
        tokenUser.setName("Super Admin");
        responseEty.setSuccess(20000);
        responseEty.setData(tokenUser);
        return responseEty;
    }

    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)





    @Override
    public Integer loginUserCountByName(String loginName) {
        QueryWrapper<LoginUser> wrapper = new QueryWrapper<>();
        wrapper.eq("LOGIN_NAME",loginName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveLoginUser(LoginUser loginUser) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        baseMapper.insert(loginUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLoginUser(LoginUser loginUser) {
//        dropUserRolesByUserId(user.getLoginId());
        baseMapper.updateById(loginUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLoginUser(LoginUser loginUser) {
        baseMapper.deleteById(loginUser.getLoginId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockLoginUser(LoginUser loginUser) {
        loginUser.setUseFlag(loginUser.getUseFlag()?false:true);
        baseMapper.updateById(loginUser);
    }
}
