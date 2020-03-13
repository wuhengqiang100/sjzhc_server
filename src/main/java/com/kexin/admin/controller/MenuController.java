package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kexin.admin.entity.tables.SysMenus;
import com.kexin.admin.entity.tables.SysMenusMeta;
import com.kexin.admin.entity.vo.MenuTree;
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
    public ResponseEty create(){
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
/*

    @Autowired
    MenuService menuService;

    @GetMapping("list")
    @SysLog("跳转菜单列表")
    public String list(){
        return "admin/menu/list";
    }

//    @RequiresPermissions("sys:menu:list")
    @RequestMapping("treeList")
    @ResponseBody
    public ResponseEntity treeList(){
        ResponseEntity responseEntity = ResponseEntity.success("操作成功");
        responseEntity.setAny("code",0);
        responseEntity.setAny("msg","");
        responseEntity.setAny("count","");
        HashMap map = new HashMap();
        map.put("del_flag",false);
        List<Menu> menus = menuService.selectAllMenuList(map);
        menus.forEach( menu -> {
            if(StringUtils.isBlank(menu.getParentId())) {
                menu.setParentId("-1");
            }
        });
        menus.sort(Comparator.comparing(Menu::getSort));
        return responseEntity.setAny("data",menus);
    }

    @GetMapping("add")
    public String add(@RequestParam(value = "parentId",required = false) String parentId, ModelMap modelMap){
        if(parentId != null){
            Menu menu = menuService.selectById(parentId);
            modelMap.put("parentMenu",menu);
        }
        return "admin/menu/add";
    }

//    @RequiresPermissions("sys:menu:add")
    @PostMapping("add")
    @ResponseBody
    @SysLog("保存新增菜单数据")
    public ResponseEntity add(Menu menu){
        if(StringUtils.isBlank(menu.getName())){
            return ResponseEntity.failure("菜单名称不能为空");
        }
        if(menuService.getCountByName(menu.getName())>0){
            return ResponseEntity.failure("菜单名称已存在");
        }
        if(StringUtils.isNotBlank(menu.getPermission())){
            if(menuService.getCountByPermission(menu.getPermission())>0){
                return ResponseEntity.failure("权限标识已经存在");
            }
        }
        if(menu.getParentId() == null){
            menu.setLevel(1);
            menu.setSort(menuService.selectFirstLevelMenuMaxSort());
        }else{
            Menu parentMenu = menuService.selectById(menu.getParentId());
            if(parentMenu==null){
                return ResponseEntity.failure("父菜单不存在");
            }
            menu.setParentIds(parentMenu.getParentIds());
            menu.setLevel(parentMenu.getLevel()+1);
            menu.setSort(menuService.seleclMenuMaxSortByPArentId(menu.getParentId()));
        }
        menuService.saveOrUpdateMenu(menu);
        menu.setParentIds(StringUtils.isBlank(menu.getParentIds()) ? menu.getId()+"," : menu.getParentIds() + menu.getId()+",");
        menuService.saveOrUpdateMenu(menu);
        return ResponseEntity.success("操作成功");
    }

    @GetMapping("edit")
    public String edit(String id,ModelMap modelMap){
        Menu menu = menuService.selectById(id);
        modelMap.addAttribute("menu",menu);
        return "admin/menu/edit";
    }

//    @RequiresPermissions("sys:menu:edit")
    @PostMapping("edit")
    @ResponseBody
    @SysLog("保存编辑菜单数据")
    public ResponseEntity edit(Menu menu){
        if(StringUtils.isBlank(menu.getId())){
            return ResponseEntity.failure("菜单ID不能为空");
        }
        if (StringUtils.isBlank(menu.getName())) {
            return ResponseEntity.failure("菜单名称不能为空");
        }
        Menu oldMenu = menuService.selectById(menu.getId());
        if(!oldMenu.getName().equals(menu.getName())) {
            if(menuService.getCountByName(menu.getName())>0){
                return ResponseEntity.failure("菜单名称已存在");
            }
        }
        if (StringUtils.isNotBlank(menu.getPermission())) {
            if(!oldMenu.getPermission().equals(menu.getPermission())) {
                if (menuService.getCountByPermission(menu.getPermission()) > 0) {
                    return ResponseEntity.failure("权限标识已经存在");
                }
            }
        }
        if(menu.getSort() == null){
            return ResponseEntity.failure("排序值不能为空");
        }
        menuService.saveOrUpdateMenu(menu);
        return ResponseEntity.success("操作成功");
    }

//    @RequiresPermissions("sys:menu:delete")
    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除菜单")
    public ResponseEntity delete(@RequestParam(value = "id",required = false)String id){
        if(StringUtils.isBlank(id)){
            return ResponseEntity.failure("菜单ID不能为空");
        }
        Menu menu = menuService.selectById(id);
        menu.setDelFlag(true);
        menuService.saveOrUpdateMenu(menu);
        return ResponseEntity.success("操作成功");
    }
*/

}
