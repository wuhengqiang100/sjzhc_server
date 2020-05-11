package com.kexin.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.*;
import com.kexin.admin.service.*;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.base.Data;
import com.kexin.common.base.PageDataBase;
import com.kexin.common.util.ResponseEty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:角色控制controller
 * @Author: 巫恒强  @Date: 2019/10/23 12:48
 * @Param:
 * @Return: 
 */
@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    RoleMenuService roleMenuService;//角色菜单关系service


    @Autowired
    SysFunctionService sysFunctionService;//权限service

    @Autowired
    UserRoleService userRoleService;//用户和角色关系表

    @Autowired
    MachineCheckQueryService machineCheckQueryService;//获取查询条件


    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @GetMapping("list")
    @ResponseBody
    @SysLog("角色列表获取")
    public PageDataBase<Role> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                      @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                      @RequestParam(value = "sort")String sort,
                                      @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                      @RequestParam(value = "title",defaultValue = "") String title,
                                      ServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<Role> rolePageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<Role> roleWrapper = new QueryWrapper<>();
        roleWrapper.eq("USE_FLAG",1);
        if (sort.equals("+id")){
            roleWrapper.orderByAsc("ROLE_ID");
        }else{
            roleWrapper.orderByDesc("ROLE_ID");
        }

        if (StringUtils.isNotEmpty(useFlag)){
            roleWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            roleWrapper.like("ROLE_NAME",title);
        }
        IPage<Role> rolePage = roleService.page(new Page<>(page,limit),roleWrapper);
        data.setTotal(rolePage.getTotal());
        data.setItems(rolePage.getRecords());
        rolePageData.setData(data);
        return rolePageData;
    }

/*    private List<Role> setRoleTypeToRole(List<Role> roles){
        roles.forEach(r -> {
            if(r.getRoleTypeId()!=null){
                RoleType roleType=roleTypeService.getById(r.getRoleTypeId());
                r.setRoleType(roleType);
            }
        });
         return roles;
    }*/
    @GetMapping("listOption")
    @ResponseBody
    @SysLog("角色类别options列表获取")
    public ResponseEty listopetion(){
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("roleOptions",roleService.listRoleOption());
        responseEty.setAny("operatorOption",machineCheckQueryService.getOperatorSelectOption());
        return responseEty;
    }

    @PostMapping("create")
    @ResponseBody
    @SysLog("新增角色数据")
    public ResponseEty create(@RequestBody  Role role){

        if(StringUtils.isBlank(role.getRoleName())){
            return ResponseEty.failure("角色名称不能为空");
        }
        if (roleService.roleCountByName(role.getRoleName())>0){
            return ResponseEty.failure("角色名称已使用,请重新输入");
        }
        roleService.saveRole(role);
        if(role.getRoleId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        Integer roleId=role.getRoleId();
        //保存b端权限
        if (role.getMenuIds()!=null){
            Integer [] menuIds=role.getMenuIds();

            SysRoleMenus sysRoleMenu=null;
            for (Integer menuId:menuIds) {
                //保存b端权限和角色之间的关系表
                sysRoleMenu=new SysRoleMenus();
                sysRoleMenu.setRoleId(roleId);
                sysRoleMenu.setFunctionId(menuId);
                roleMenuService.saveSysRoleMenus(sysRoleMenu);

            }
        }
        //保存c端权限
        if (role.getCheckedPermiss()!=null){
            String [] checkedPermiss=role.getCheckedPermiss();//根据function 表里面的title字段查找
            SysRoleMenus sysRoleMenu=null;
            for (String permiss:checkedPermiss){
                QueryWrapper<SysFunctions> sysFunctionsQueryWrapper=new QueryWrapper<>();
                sysFunctionsQueryWrapper.eq("TITLE",permiss);
                SysFunctions sysFunction=sysFunctionService.getOne(sysFunctionsQueryWrapper);
                sysRoleMenu=new SysRoleMenus();
                sysRoleMenu.setRoleId(roleId);
                sysRoleMenu.setFunctionId(sysFunction.getFunctionId());
                roleMenuService.saveSysRoleMenus(sysRoleMenu);

            }
        }

        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存角色修改数据")
    public ResponseEty update(@RequestBody  Role role){
        if(role.getRoleId()==null){
            return ResponseEty.failure("角色ID不能为空");
        }

        if(StringUtils.isBlank(role.getRoleName())){
            return ResponseEty.failure("角色名称不能为空");
        }
        Role oldRole = roleService.getById(role.getRoleId());
        if(StringUtils.isNotBlank(role.getRoleName())){
            if(!role.getRoleName().equals(oldRole.getRoleName())){
                if(roleService.roleCountByName(role.getRoleName())>0){
                    return ResponseEty.failure("该角色名称已经使用");
                }
            }
        }


        return  roleService.updateRole(role);
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除角色数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        Role role=roleService.getById(id);
        if(role == null){
            return ResponseEty.failure("角色不存在");
        }


        QueryWrapper<SysUserRoles> sysUserRolesQueryWrapper=new QueryWrapper<>();
        sysUserRolesQueryWrapper.eq("ROLE_ID",id);
        List<SysUserRoles> sysUserRolesList=userRoleService.list(sysUserRolesQueryWrapper);
        if (sysUserRolesList.size()!=0){
            return ResponseEty.failure("角色下有账户,不能删除!");
        }
        //删除关系表中的数据
        roleMenuService.deleleByRoleId(id);
        //删除角色数据
        roleService.deleteRole(role);


        return ResponseEty.success("删除成功");
    }
    //
//    @RequiresPermissions("sys:user:delete")
/*
    @PostMapping("deleteSome")
    @ResponseBody
    @SysLog("删除角色数据(多个)")
    public ResponseEty deleteSome(@RequestBody List<Role> Roles){
        if(Roles == null || Roles.size()==0){
            return ResponseEty.failure("请选择需要删除的信息");
        }
        Roles.forEach(m -> roleService.deleteRole(m));
        return ResponseEty.success("批量删除成功");
    }
*/


    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用角色")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        Role role=roleService.getById(id);
        if(role == null){
            return ResponseEty.failure("角色不存在");
        }
        roleService.lockRole(role);
        return ResponseEty.success("操作成功");
    }
}
