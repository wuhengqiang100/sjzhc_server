package com.kexin.admin;

import com.kexin.admin.service.LoginUserService;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 把所有的账户密码通过base64加密
 */
@SpringBootTest
public class TestParameterType {

    @Resource
    LoginUserService loginUserService;

    @Test
    public void testParameterType(){

    }

    //复制所有的账户名信息
//    @Test
//    public void testCopyLoginName() {
//
//        List<LoginUser> loginUserList = loginUserService.list();
//        loginUserList.forEach(loginUser -> {
//            loginUser.setLoginUserName(loginUser.getLoginName());
//            loginUser.setLoginUserPass(loginUser.getLoginPass());
//        });
//        boolean b = loginUserService.saveOrUpdateBatch(loginUserList);
//        if (b){
//            System.out.println("复制登陆用户名,及密码成功");
//        }else{
//            System.out.println("复制登陆用户名,及密码失败");
//        }
//    }
//
//    //加密所有的账户的密码
//    @Test
//    public void testEncodeBase64() {
//
//        List<LoginUser> loginUserList = loginUserService.list();
//        loginUserList.forEach(loginUser -> {
//            loginUser.setLoginUserPass(CryptographyUtil.encodeBase64(loginUser.getLoginUserPass()));
//        });
//        boolean b = loginUserService.saveOrUpdateBatch(loginUserList);
//        if (b){
//            System.out.println("加密所有的新密码成功");
//        }else{
//            System.out.println("加密所有的新密码失败");
//        }
//    }
////
//    //解密所有的账户密码
//    @Test
//    public void testDecodeBase64() {
//
//        List<LoginUser> loginUserList = loginUserService.list();
//        loginUserList.forEach(loginUser -> {
//            loginUser.setLoginUserPass(CryptographyUtil.decodeBase64String(loginUser.getLoginUserPass()));
//        });
//        boolean b = loginUserService.saveOrUpdateBatch(loginUserList);
//        if (b){
//            System.out.println("解密所有的新密码成功");
//        }else{
//            System.out.println("解密所有的新密码失败");
//        }
//    }

}
