package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kexin.admin.entity.tables.SysFunctions;
import com.kexin.admin.entity.tables.SysMenusMeta;
import com.kexin.admin.entity.tables.SysRoleMenus;
import com.kexin.admin.entity.vo.MenuTree;
import com.kexin.admin.service.RoleMenuService;
import com.kexin.admin.service.SysFunctionService;
import com.kexin.admin.service.SysMenusMetaService;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.util.ResponseEty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/menu")
public class MenuController {

//    @Autowired
//    SysMenusService sysMenusService;

    @Autowired
    SysFunctionService sysFunctionService;

    @Autowired
    SysMenusMetaService sysMenusMetaService;

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
        sysFunctionService.getSysFunctions();
        ResponseEty responseEty=new ResponseEty();

        QueryWrapper<SysFunctions> sysMenusQueryWrapper = new QueryWrapper<>();
        sysMenusQueryWrapper.isNull("FUNCTON_PARENT_ID");

//        List<SysFunctions> sysMenusList=sysMenusService.list(sysMenusQueryWrapper);
        List<SysFunctions> sysMenusList=sysFunctionService.list(sysMenusQueryWrapper);
        MenuTree[] menuTree=new MenuTree[sysMenusList.size()];//总返回的菜单树
        MenuTree menuTree1;
        for (int i=0;i<sysMenusList.size();i++) {//SysFunctions menu:sysMenusList
            menuTree1=new MenuTree();
            menuTree1.setId(sysMenusList.get(i).getFunctonId());
            //把一级主菜单的描述信息放进去
            SysMenusMeta sysMenusMeta=sysMenusMetaService.getById(sysMenusList.get(i).getFunctonId());
            if (sysMenusMeta!=null){
                menuTree1.setLabel(sysMenusList.get(i).getTitle());
            }
            menuTree[i]=menuTree1;
            if (StringUtils.isNotEmpty(sysMenusList.get(i).getChildrenIds())){
                String[] childrenIds=StringUtils.split(sysMenusList.get(i).getChildrenIds(),',');
                QueryWrapper<SysFunctions> sysMenusChildQueryWrapper = new QueryWrapper<>();
                sysMenusChildQueryWrapper.in("FUNCTON_ID",childrenIds);
                List<SysFunctions> sysMenusChildList=sysFunctionService.list(sysMenusChildQueryWrapper);
                MenuTree[] menuTreeChild=new MenuTree[sysMenusChildList.size()];//子菜单树
                MenuTree menuTree2;
                for (int j=0;j<sysMenusChildList.size();j++){// menuChild:sysMenusChildList
                    //把二级主菜单的描述信息放进去
                    menuTree2=new MenuTree();
                    menuTree2.setId(sysMenusChildList.get(j).getFunctonId());
                    if (sysMenusMeta!=null){
                        menuTree2.setLabel(sysMenusChildList.get(j).getTitle());
                    }
                    menuTreeChild[j]=menuTree2;
                }
                menuTree1.setChildren(menuTreeChild);

            }

        }

        responseEty.setSuccess(20000);
        responseEty.setAny("menuTree",menuTree);
        return  responseEty;
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
    public ResponseEty listOwn(@RequestParam(value = "roleId",required = false)Integer roleId){
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        if(roleId==null){
            return ResponseEty.failure("参数错误");
        }
        QueryWrapper<SysRoleMenus> roleMenusQueryWrapper = new QueryWrapper<>();
        roleMenusQueryWrapper.eq("ROLE_ID",roleId);
        List<SysRoleMenus> sysRoleMenusList=roleMenuService.list(roleMenusQueryWrapper);
        if (sysRoleMenusList.size()!=0){
            Integer[] menuIds=new Integer[sysRoleMenusList.size()];
            for (int i = 0; i <sysRoleMenusList.size() ; i++) {
                menuIds[i]=sysRoleMenusList.get(i).getFunctionId();
            }
            responseEty.setAny("menuIds",menuIds);
            return responseEty;
        }
        responseEty.setAny("menuIds",null);
        return responseEty;
    }


}
