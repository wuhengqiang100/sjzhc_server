package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.LoginUser;
import com.kexin.admin.entity.tables.SysRoleMenus;
import com.kexin.admin.entity.tables.SysUserRoles;
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
import java.util.List;
import java.util.Map;

/**
 * 用户管理controller层
 */
@Controller
@RequestMapping("loginUser")
public class UserController {
    @Autowired
    LoginUserService loginUserService;
    
    @Autowired
    UserRoleService userRoleService;


    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @GetMapping("list")
    @ResponseBody
    @SysLog("用户列表获取")
    public PageDataBase<LoginUser> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                      @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                      @RequestParam(value = "sort")String sort,
                                      @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                      @RequestParam(value = "title",defaultValue = "") String title,
                                      ServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<LoginUser> loginUserPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<LoginUser> loginUserWrapper = new QueryWrapper<>();
        if (sort.equals("+id")){
            loginUserWrapper.orderByAsc("LOGIN_ID");
        }else{
            loginUserWrapper.orderByDesc("LOGIN_ID");
        }

        if (StringUtils.isNotEmpty(useFlag)){
            loginUserWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            loginUserWrapper.like("LOGIN_NAME",title);
        }
//        if(!map.isEmpty()){
//            String useFlag = (String) map.get("useFlag");
//            if(StringUtils.isNotBlank(useFlag)) {
//                loginUserWrapper.eq("USE_FLAG", useFlag);
//            }
//            String keys = (String) map.get("key");
//            if(StringUtils.isNotBlank(keys)) {
//                loginUserWrapper.and(wrapper -> wrapper.like("MACHINE_NAME", keys));//模糊查询拼接
//            }
//        }
        IPage<LoginUser> loginUserPage = loginUserService.page(new Page<>(page,limit),loginUserWrapper);
        data.setTotal(loginUserPage.getTotal());
        data.setItems(loginUserPage.getRecords());
        loginUserPageData.setData(data);
        return loginUserPageData;
    }

/*    private List<LoginUser> setLoginUserTypeToLoginUser(List<LoginUser> loginUsers){
        loginUsers.forEach(r -> {
            if(r.getLoginUserTypeId()!=null){
                LoginUserType loginUserType=loginUserTypeService.getById(r.getLoginUserTypeId());
                r.setLoginUserType(loginUserType);
            }
        });
         return loginUsers;
    }*/

    /**
     * @Title: 获取用户已拥有的角色
     * @param @param
     * @return @return
     * @author 巫恒强
     * @throws
     * @date 2020/3/16 13:56
     */
    @PostMapping("listOwn")
    @ResponseBody
    @SysLog("用户已分配的角色")
    public ResponseEty listOwn(@RequestParam(value = "userId",required = false)Integer userId){
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        if(userId==null){
            return ResponseEty.failure("参数错误");
        }
        QueryWrapper<SysUserRoles> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("USER_ID",userId);
        List<SysUserRoles> sysRoleMenusList=userRoleService.list(userRoleQueryWrapper);
        if (sysRoleMenusList.size()!=0){
            Integer[] roleIds=new Integer[sysRoleMenusList.size()];
            for (int i = 0; i <sysRoleMenusList.size() ; i++) {
                roleIds[i]=sysRoleMenusList.get(i).getRoleId();
            }
            responseEty.setAny("roleIds",roleIds);
            return responseEty;
        }
        responseEty.setAny("roleIds",null);
        return responseEty;
    }


    @PostMapping("create")
    @ResponseBody
    @SysLog("新增用户数据")
    public ResponseEty create(@RequestBody  LoginUser loginUser){
        if(loginUser.getOperatorId()==null){
            return ResponseEty.failure("用户编号不能为空");
        }
        if(StringUtils.isBlank(loginUser.getLoginName())){
            return ResponseEty.failure("用户名称不能为空");
        }
        if(StringUtils.isBlank(loginUser.getLoginPass())){
            return ResponseEty.failure("用户密码不能为空");
        }
        if (loginUserService.loginUserCountByName(loginUser.getLoginName())>0){
            return ResponseEty.failure("用户名称已使用,请重新输入");
        }
        loginUserService.saveLoginUser(loginUser);
        if(loginUser.getLoginId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        Integer operatorId=loginUser.getOperatorId();
        if (loginUser.getRoleIds()!=null){
            Integer [] roleIds=loginUser.getRoleIds();
            SysUserRoles sysUserRoles=null;
            for (Integer roleId:roleIds) {
                sysUserRoles=new SysUserRoles();
                sysUserRoles.setUserId(operatorId);
                sysUserRoles.setRoleId(roleId);
                userRoleService.saveSysUserRoles(sysUserRoles);
            }
            return ResponseEty.success("保存成功");
        }
        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存用户修改数据")
    public ResponseEty update(@RequestBody  LoginUser loginUser){
        if(loginUser.getOperatorId()==null){
            return ResponseEty.failure("用户ID不能为空");
        }
        if(StringUtils.isBlank(loginUser.getLoginName())){
            return ResponseEty.failure("用户名称不能为空");
        }
        if(StringUtils.isBlank(loginUser.getLoginPass())){
            return ResponseEty.failure("用户密码不能为空");
        }
        LoginUser oldLoginUser = loginUserService.getById(loginUser.getLoginId());
        if(StringUtils.isNotBlank(loginUser.getLoginName())){
            if(!loginUser.getLoginName().equals(oldLoginUser.getLoginName())){
                if(loginUserService.loginUserCountByName(loginUser.getLoginName())>0){
                    return ResponseEty.failure("该用户名称已经使用");
                }
            }
        }
        loginUserService.updateLoginUser(loginUser);

        if(loginUser.getLoginId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        Integer operatorId=loginUser.getOperatorId();
        if (loginUser.getRoleIds()!=null){
            //先删除原来的关系数据
            QueryWrapper<SysUserRoles> userRolesQueryWrapper = new QueryWrapper<>();
            userRolesQueryWrapper.eq("USER_ID",operatorId);
            userRoleService.remove(userRolesQueryWrapper);
            //再添加新的数据
            Integer [] roleIds=loginUser.getRoleIds();
            SysUserRoles sysUserRoles=null;
            for (Integer roleId:roleIds) {
                sysUserRoles=new SysUserRoles();
                sysUserRoles.setUserId(operatorId);
                sysUserRoles.setRoleId(roleId);
                userRoleService.saveSysUserRoles(sysUserRoles);
            }
            return ResponseEty.success("保存成功");
        }
        return ResponseEty.success("操作成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除用户数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        LoginUser loginUser=loginUserService.getById(id);
        if(loginUser == null){
            return ResponseEty.failure("用户不存在");
        }
        //先删除关系数据
        QueryWrapper<SysUserRoles> roleMenusQueryWrapper = new QueryWrapper<>();
        roleMenusQueryWrapper.eq("USER_ID",id);
        userRoleService.remove(roleMenusQueryWrapper);
        //再删除用户的数据
        loginUserService.deleteLoginUser(loginUser);

        return ResponseEty.success("删除成功");
    }
    //
//    @RequiresPermissions("sys:user:delete")
    @PostMapping("deleteSome")
    @ResponseBody
    @SysLog("删除用户数据(多个)")
    public ResponseEty deleteSome(@RequestBody List<LoginUser> LoginUsers){
        if(LoginUsers == null || LoginUsers.size()==0){
            return ResponseEty.failure("请选择需要删除的信息");
        }
        LoginUsers.forEach(m -> loginUserService.deleteLoginUser(m));
        return ResponseEty.success("批量删除成功");
    }


    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用用户")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        LoginUser loginUser=loginUserService.getById(id);
        if(loginUser == null){
            return ResponseEty.failure("用户不存在");
        }
        loginUserService.lockLoginUser(loginUser);
        return ResponseEty.success("操作成功");
    }

}
