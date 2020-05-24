package com.kexin.admin.controller;


import com.kexin.admin.entity.tables.LoginUser;
import com.kexin.admin.entity.tables.Operator;
import com.kexin.admin.entity.tables.WasterReason;
import com.kexin.admin.entity.vo.common.ResetUser;
import com.kexin.admin.service.*;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.component.TestComponent;
import com.kexin.common.util.ResponseEty;
import com.kexin.common.util.encry.CryptographyUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 公用controller
 */

@Controller
@RequestMapping("common")
public class CommonController {

    @Autowired
    SysFunctionService sysFunctionService;

    @Autowired
    SysMenusService sysMenusService;


    @Autowired
    LoginUserService loginUserService;

    @Autowired
    TestComponent testComponent;

    @Autowired
    SystemLogService systemLogService;//系统日志记录service

    @Autowired
    OperatorService operatorService;

    /**
     * @Title:
     * @Description: TODO(数据库动态获取router)
     * @param @param
     * @return @return
     * @author 13015
     * @throws
     * @date 2020/3/12 14:25
     */


    /**
     * @Title:
     * @Description: TODO(数据库动态获取router)
     * @param @param
     * @return @return
     * @author 13015
     * @throws
     * @date 2020/3/12 14:25
     */
    @GetMapping("menu")
    @ResponseBody
    @SysLog("获取动态路由菜单")
    public ResponseEty menu(@RequestParam(name = "token", required = false) Integer token){
       return  sysFunctionService.getSysFunctions(token);
    }


    /**
     * @Title:用户登陆
     * @param @param
     * @return @return
     * @author 巫恒强
     * @throws
     * @date 2020/3/12 14:26
     */
    @PostMapping("login")
    @ResponseBody
    public ResponseEty login(@RequestBody Map map,HttpSession session){

        return loginUserService.login(map,session);
    }

    /**
     * 后台登录失败重定向到前台
     * @param request
     * @return
     */
/*    @GetMapping("login")
    @ResponseBody
    public ResponseEty resetLogin(@RequestBody Map map,HttpSession session){
        return loginUserService.login(map,session);
    }*/
    @GetMapping("login")
    @ResponseBody
    public ResponseEty resetLogin(HttpServletRequest request){
        ResponseEty responseEty=new ResponseEty();

        Subject s = SecurityUtils.getSubject();
//        modelMap.put("server",server);
        if(s.isAuthenticated()) {
            responseEty.setMessage("已认证,有权限");
            responseEty.setSuccess(20000);
        }else{
            responseEty.setMessage("您没有权限,不能请求数据");
            responseEty.setSuccess(50008);
        }


        return responseEty;
    }

    /**
     * 登录成功页面
     * @param request
     * @return
     */
    @GetMapping("index")
    @ResponseBody
    public ResponseEty index(HttpServletRequest request){
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        return responseEty;
    }


    /**
     * @Title:获取用户的基本信息
     * @param @param
     * @return @return
     * @author 巫恒强
     * @throws
     * @date 2020/3/12 14:27
     */
    @GetMapping("info")
    @ResponseBody
    public ResponseEty info(@RequestParam String token){

        return loginUserService.userInfo(token);
    }

    /**
     * 系统用户退出登录
     * @param session
     * @return
     */
    @GetMapping("logout")
    @ResponseBody
    public ResponseEty logout(HttpSession session,@RequestHeader(value="token",required = false) Integer token){
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setData("success");
        SecurityUtils.getSubject().logout();
        LoginUser loginUser=loginUserService.getById(token);
        Operator operator=operatorService.getById(loginUser.getOperatorId());
        systemLogService.saveMachineLog(token,"退出系统","退出了系统");
//        testComponent.list();//测试组件
        return responseEty;
    }

    @PostMapping("resetPassword")
    @ResponseBody
    @SysLog("保存错误类型修改数据")
    public ResponseEty resetPassword(@RequestBody ResetUser resetUser,@RequestHeader(value="token",required = false) Integer token){
        if (resetUser.getLoginId()==null){
            return ResponseEty.failure("请重新登录");
        }
        LoginUser oldLoginUser=loginUserService.getById(resetUser.getLoginId());
        if (StringUtils.isEmpty(resetUser.getOldPassword())){
            return ResponseEty.failure("请输入旧密码");
        }if (StringUtils.isEmpty(resetUser.getNewPassword())){
            return ResponseEty.failure("请输入新密码");
        }if (StringUtils.isEmpty(resetUser.getConfirmPassword())){
            return ResponseEty.failure("请确认密码");
        }if (!resetUser.getOldPassword().equals(CryptographyUtil.decodeBase64String(oldLoginUser.getLoginUserPass()))){
            return ResponseEty.failure("旧密码不正确");
        }if (!resetUser.getConfirmPassword().equals(resetUser.getNewPassword())){
            return ResponseEty.failure("两次新密码输入不同,请重新输入");
        }
        LoginUser loginUser=new LoginUser();
        loginUser.setLoginId(resetUser.getLoginId());
        loginUser.setLoginUserPass(CryptographyUtil.encodeBase64(resetUser.getNewPassword()));
        loginUserService.updateById(loginUser);

        Operator operator=operatorService.getById(loginUser.getOperatorId());
        systemLogService.saveMachineLog(token,"重置密码","重置了密码");
        return ResponseEty.success("修改密码成功,下次登录生效");
    }


}
