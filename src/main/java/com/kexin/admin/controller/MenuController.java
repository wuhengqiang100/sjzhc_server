package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kexin.admin.entity.tables.Role;
import com.kexin.admin.entity.tables.SysMenus;
import com.kexin.admin.entity.tables.SysMenusMeta;
import com.kexin.admin.entity.tables.SysRoleMenus;
import com.kexin.admin.entity.vo.MenuTree;
import com.kexin.admin.service.RoleMenuService;
import com.kexin.admin.service.SysMenusMetaService;
import com.kexin.admin.service.SysMenusService;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.util.ResponseEty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    SysMenusService sysMenusService;


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
        ResponseEty responseEty=new ResponseEty();

        QueryWrapper<SysMenus> sysMenusQueryWrapper = new QueryWrapper<>();
        sysMenusQueryWrapper.isNull("PARENT_ID");

        List<SysMenus> sysMenusList=sysMenusService.list(sysMenusQueryWrapper);
        MenuTree[] menuTree=new MenuTree[sysMenusList.size()];//总返回的菜单树
        MenuTree menuTree1;
        for (int i=0;i<sysMenusList.size();i++) {//SysMenus menu:sysMenusList
            menuTree1=new MenuTree();
            menuTree1.setId(sysMenusList.get(i).getId());
            //把一级主菜单的描述信息放进去
            SysMenusMeta sysMenusMeta=sysMenusMetaService.getById(sysMenusList.get(i).getId());
            if (sysMenusMeta!=null){
                sysMenusList.get(i).setMeta(sysMenusMeta);
                menuTree1.setLabel(sysMenusList.get(i).getMeta().getTitle());
            }
            menuTree[i]=menuTree1;
            if (StringUtils.isNotEmpty(sysMenusList.get(i).getChildrenIds())){
                String[] childrenIds=StringUtils.split(sysMenusList.get(i).getChildrenIds(),',');
                QueryWrapper<SysMenus> sysMenusChildQueryWrapper = new QueryWrapper<>();
                sysMenusChildQueryWrapper.in("ID",childrenIds);
                List<SysMenus> sysMenusChildList=sysMenusService.list(sysMenusChildQueryWrapper);
                MenuTree[] menuTreeChild=new MenuTree[sysMenusChildList.size()];//子菜单树
                MenuTree menuTree2;
                for (int j=0;j<sysMenusChildList.size();j++){// menuChild:sysMenusChildList
                    //把二级主菜单的描述信息放进去
                    menuTree2=new MenuTree();
                    menuTree2.setId(sysMenusChildList.get(j).getId());
                    SysMenusMeta sysMenusChildMeta=sysMenusMetaService.getById(sysMenusChildList.get(j).getMetaId());
                    if (sysMenusMeta!=null){
                        sysMenusChildList.get(j).setMeta(sysMenusChildMeta);
                        menuTree2.setLabel(sysMenusChildList.get(j).getMeta().getTitle());
                    }
                    menuTreeChild[j]=menuTree2;
                }
                sysMenusList.get(i).setChildren(sysMenusChildList);
                menuTree1.setChildren(menuTreeChild);

            }

        }

        responseEty.setSuccess(20000);
        responseEty.setAny("menuTree",menuTree);
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
                menuIds[i]=sysRoleMenusList.get(i).getMenuId();
            }
            responseEty.setAny("menuIds",menuIds);
            return responseEty;
        }
        responseEty.setAny("menuIds",null);
        return responseEty;
    }


}
