package com.kexin.admin.controller;


import com.kexin.admin.service.LoginUserService;
import com.kexin.admin.service.SysFunctionService;
import com.kexin.admin.service.SysMenusService;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.component.TestComponent;
import com.kexin.common.util.ResponseEty;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
    public ResponseEty logout(HttpSession session){
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setData("success");
        SecurityUtils.getSubject().logout();
//        testComponent.list();//测试组件
        return responseEty;
    }

}
