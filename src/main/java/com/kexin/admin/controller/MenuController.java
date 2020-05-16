package com.kexin.admin.controller;


import com.kexin.admin.service.RoleMenuService;
import com.kexin.admin.service.SysFunctionService;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.util.ResponseEty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/menu")
public class MenuController {



    @Autowired
    SysFunctionService sysFunctionService;


    @Autowired
    RoleMenuService roleMenuService;//角色菜单关系service





    /**
     * @Title:
     * @Description: TODO(角色权限分配,获取所有菜单)
     * @param @param
     * @return @return
     * @author 巫恒强
     * @throws
     * @date 2020/3/12 14:25
     */
    @GetMapping("listOption")
    @ResponseBody
    @SysLog("角色权限分配,获取所有菜单")
    public ResponseEty listOption(){
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("functionList",sysFunctionService.getAllSysFunctions());
//        responseEty.setAny("menuTree",sysFunctionService.getSysFunctionOptionB()); // 获取b端所有权限的菜单树
//        responseEty.setAny("cPermissOptions",sysFunctionService.getSysFunctionOptionC()); // 获取c端所有权限的菜单树
        return responseEty;
    }


    /**
     * @Title: 获取角色已分配的去权限,修改中使用
     * @param @param
     * @return @return
     * @author 巫恒强
     * @throws
     * @date 2020/3/16 13:56
     */
    @PostMapping("listOwn")
    @ResponseBody
    @SysLog("角色已分配的权限")
    public ResponseEty listOwnB(@RequestParam(value = "roleId",required = false)Integer roleId){
        ResponseEty responseEty=new ResponseEty();
        if(roleId==null){
            return ResponseEty.failure("参数错误");
        }
        responseEty.setSuccess(20000);
        responseEty.setAny("menuIds",sysFunctionService.getSysFunctionOwn(roleId));
//        responseEty.setAny("menuIds",sysFunctionService.getSysFunctionOwnB(roleId));//获取已分配的b端权限
//        responseEty.setAny("checkedcPermiss",sysFunctionService.getSysFunctionOwnC(roleId));//获取已分配的c端权限

        return responseEty;
    }






}
