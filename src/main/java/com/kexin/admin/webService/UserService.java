package com.kexin.admin.webService;

import com.kexin.admin.entity.pojo.UserWeb;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.Map;

/**
 * @Description:
 * @Author: 巫恒强
 * @Date: 2019/12/17 15:29
 */
//@WebService(targetNamespace="http://service.springboot.mracale.com")如果不添加的话,动态调用invoke的时候,会报找不到接口内的方法,具体原因未知.
//@WebService(targetNamespace="http://service.webservice.kexin.com")
    public interface UserService {

//    @WebMethod//标注该方法为webservice暴露的方法,用于向外公布，它修饰的方法是webservice方法，去掉也没影响的，类似一个注释信息。
//    public UserWeb getUser(@WebParam(name = "userId") String userId);
//
//    @WebMethod
//    @WebResult(name="String",targetNamespace="")
//    public String getUserName(@WebParam(name = "userId") String userId);
//
//    @WebMethod
//    @WebResult(name="Map")
//    public Map<String, UserWeb> getAllUserData();
}