package com.kexin.admin.webService.impl;

/**
 * @Description:
 * @Author: 巫恒强
 * @Date: 2019/12/17 15:32
 */

import com.kexin.admin.entity.pojo.UserWeb;
import com.kexin.admin.webService.UserService;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName:UserServiceImpl
 * @Description:测试服务接口实现类
 */
//@WebService(serviceName="UserService",//对外发布的服务名
//        targetNamespace="http://service.webservice.kexin.com",//指定你想要的名称空间，通常使用使用包名反转
//        endpointInterface="com.kexin.admin.webService.impl.UserServiceImpl")//服务接口全路径, 指定做SEI（Service EndPoint Interface）服务端点接口
//@Component
public class UserServiceImpl implements UserService {

//    private Map<String, UserWeb> userMap = new HashMap<String, UserWeb>();
//    public UserServiceImpl() {
//        System.out.println("向实体类插入数据");
//        UserWeb user = new UserWeb();
//        user.setUserId(UUID.randomUUID().toString().replace("-", ""));
//        user.setUserName("mracale01");
//        user.setEmail("mracale01@163.xom");
//        userMap.put(user.getUserId(), user);
//
//        user = new UserWeb();
//        user.setUserId(UUID.randomUUID().toString().replace("-", ""));
//        user.setUserName("mracale02");
//        user.setEmail("mracale02@163.xom");
//        userMap.put(user.getUserId(), user);
//
//        user = new UserWeb();
//        user.setUserId(UUID.randomUUID().toString().replace("-", ""));
//        user.setUserName("mracale03");
//        user.setEmail("mracale03@163.xom");
//        userMap.put(user.getUserId(), user);
//    }
//    @Override
//    public String getUserName(String userId) {
//        return "userId为：" + userId;
//    }
//
//    @Override
//    public Map<String, UserWeb> getAllUserData() {
//        return userMap;
//    }
//
//    @Override
//    public UserWeb getUser(String userId) {
//        System.out.println("userMap是:"+userMap);
//        return userMap.get(userId);
//    }
}