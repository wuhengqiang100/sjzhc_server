package com.kexin.admin.controller;


import com.kexin.admin.service.LoginUserService;
import com.kexin.admin.service.SysFunctionService;
import com.kexin.admin.service.SysMenusMetaService;
import com.kexin.admin.service.SysMenusService;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.util.ResponseEty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    SysMenusMetaService sysMenusMetaService;

    @Autowired
    LoginUserService loginUserService;

    /**
     * @Title:
     * @Description: TODO(数据库动态获取router)
     * @param @param
     * @return @return
     * @author 13015
     * @throws
     * @date 2020/3/12 14:25
     */
/*
    @PostMapping("menu1")
    @ResponseBody
    @SysLog("获取动态路由菜单")
    public ResponseEty create(){
        ResponseEty responseEty=new ResponseEty();
        QueryWrapper<SysMenus> sysMenusQueryWrapper = new QueryWrapper<>();
        sysMenusQueryWrapper.isNull("PARENT_ID");
        List<SysMenus> sysMenusList=sysMenusService.list(sysMenusQueryWrapper);
        for (SysMenus menu:sysMenusList) {
            //把一级主菜单的描述信息放进去
            SysMenusMeta sysMenusMeta=sysMenusMetaService.getById(menu.getMetaId());
            if (sysMenusMeta!=null){
                menu.setMeta(sysMenusMeta);
            }
            if (StringUtils.isNotEmpty(menu.getChildrenIds())){
                String[] childrenIds=StringUtils.split(menu.getChildrenIds(),',');
                QueryWrapper<SysMenus> sysMenusChildQueryWrapper = new QueryWrapper<>();
                sysMenusChildQueryWrapper.in("FUNCTION_ID",childrenIds);
                List<SysMenus> sysMenusChildList=sysMenusService.list(sysMenusChildQueryWrapper);
                for (SysMenus menuChild:sysMenusChildList){
                    //把二级主菜单的描述信息放进去
                    SysMenusMeta sysMenusChildMeta=sysMenusMetaService.getById(menuChild.getMetaId());
                    if (sysMenusMeta!=null){
                        menuChild.setMeta(sysMenusChildMeta);
                    }
                }
                menu.setChildren(sysMenusChildList);
            }
        }
        responseEty.setSuccess(20000);
        responseEty.setAny("asyncRoutes",sysMenusList);
        return responseEty;
    }
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
    @PostMapping("menu")
    @ResponseBody
    @SysLog("获取动态路由菜单")
    public ResponseEty menu(){
       return  sysFunctionService.getSysFunctions();
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
        System.out.println(token);
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
        session.removeAttribute("tokenName");
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setData("success");
        return responseEty;
    }

}
