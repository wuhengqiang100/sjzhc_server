package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.LoginUser;
import com.kexin.admin.entity.tables.Role;
import com.kexin.admin.entity.tables.SysUserRoles;
import com.kexin.admin.service.*;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.base.Data;
import com.kexin.common.base.PageDataBase;
import com.kexin.common.util.ResponseEty;
import com.kexin.common.util.encry.CryptographyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;

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

    @Autowired
    RoleService roleService;


    @Autowired
    OperatorService operatorService;

    @Autowired
    SystemLogService systemLogService;//系统日志记录service


    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @GetMapping("list")
    @ResponseBody
    @SysLog("用户列表获取")
    public PageDataBase<LoginUser> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                      @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                      @RequestParam(value = "sort")String sort,
                                      @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                      @RequestParam(value = "title",defaultValue = "") String title,
                                      @RequestParam(value = "operatorId",defaultValue = "") Integer operatorId,
                                        @RequestHeader(value="token",required = false) Integer token,
                                        ServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<LoginUser> loginUserPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<LoginUser> loginUserWrapper = new QueryWrapper<>();
//        loginUserWrapper.orderByDesc("START_DATE");
        loginUserWrapper.orderByDesc("LOGIN_ID");
        if (sort.equals("+id")){
            loginUserWrapper.orderByAsc("LOGIN_ID");
        }else{
            loginUserWrapper.orderByDesc("LOGIN_ID");
        }

        if (StringUtils.isNotEmpty(useFlag)){
            loginUserWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            loginUserWrapper.like("LOGIN_USER_NAME",title);
        }
        if (operatorId!=null){
            loginUserWrapper.like("OPERATOR_ID",operatorId);
        }

        IPage<LoginUser> loginUserPage = loginUserService.page(new Page<>(page,limit),loginUserWrapper);
        data.setTotal(loginUserPage.getTotal());
        loginUserPage.getRecords().forEach(r->{
            r.setOperator(operatorService.getById(r.getOperatorId()));
            r.setRoleIds(roleService.getRoleOptionOwn(r.getLoginId()));
            if (r.getRoleIds()!=null){
                r.setRoleString( roleService.getRoleString(r.getRoleIds()));
            }
            });
        data.setItems(loginUserPage.getRecords());
        loginUserPageData.setData(data);
        systemLogService.saveMachineLog(token,"查询","查询了用户列表");
        return loginUserPageData;
    }


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
    public ResponseEty listOwn(@RequestParam(value = "loginId",required = false)Integer loginId){
        ResponseEty responseEty=new ResponseEty();
        if(loginId==null){
            return ResponseEty.failure("参数错误");
        }
        responseEty.setSuccess(20000);
        responseEty.setAny("checkedRoles",roleService.getRoleOptionOwn(loginId));

        return responseEty;
    }


    @PostMapping("create")
    @ResponseBody
    @SysLog("新增用户数据")
    public ResponseEty create(@RequestBody  LoginUser loginUser,@RequestHeader(value="token",required = false) Integer token){
        if(loginUser.getOperatorId()==null){
            return ResponseEty.failure("请选择用户");
        }
        if (loginUserService.loginUserCountByOperatorId(loginUser.getOperatorId())>0){
            return ResponseEty.failure("一个用户只能有一个账户,该用户已经有账户");
        }
        if(StringUtils.isBlank(loginUser.getLoginUserName())){
            return ResponseEty.failure("登陆名称不能为空");
        }
        if(StringUtils.isBlank(loginUser.getLoginUserPass())){
            return ResponseEty.failure("用户密码不能为空");
        }
        loginUser.setLoginUserPass(CryptographyUtil.encodeBase64(loginUser.getLoginUserPass()));
        if (loginUserService.loginUserCountByName(loginUser.getLoginUserName())>0){
            return ResponseEty.failure("用户名称已使用,请重新输入");
        }

        return loginUserService.saveLoginUser(loginUser,token);
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存用户修改数据")
    public ResponseEty update(@RequestBody  LoginUser loginUser,@RequestHeader(value="token",required = false) Integer token){
        if(loginUser.getOperatorId()==null){
            return ResponseEty.failure("用户ID不能为空");
        }

        if(StringUtils.isBlank(loginUser.getLoginUserName())){
            return ResponseEty.failure("登陆名称不能为空");
        }
        if(StringUtils.isBlank(loginUser.getLoginUserPass())){
            return ResponseEty.failure("用户密码不能为空");
        }
        LoginUser oldLoginUser = loginUserService.getById(loginUser.getLoginId());
        if(StringUtils.isNotBlank(loginUser.getLoginUserName())){
            if(!loginUser.getLoginUserName().equals(oldLoginUser.getLoginUserName())){
                if(loginUserService.loginUserCountByName(loginUser.getLoginUserName())>0){
                    return ResponseEty.failure("该用户名称已经使用");
                }
            }
        }
        if (!loginUser.getOperatorId().equals(oldLoginUser.getOperatorId())){
            if (loginUserService.loginUserCountByOperatorId(loginUser.getOperatorId())>0){
                return ResponseEty.failure("一个用户只能有一个账户,该用户已经有账户");
            }
        }

        //判断修改密码
        if (StringUtils.isNotBlank(loginUser.getLoginUserPass())){
            if(!loginUser.getLoginUserPass().equals(oldLoginUser.getLoginUserPass())){
                loginUser.setLoginUserPass(CryptographyUtil.encodeBase64(loginUser.getLoginUserPass()));
            }
        }

        return loginUserService.updateLoginUser(loginUser,token);
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除用户数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id,@RequestHeader(value="token",required = false) Integer token){
        return  loginUserService.deleteLoginUser(id,token);
    }
    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用用户")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id,@RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        LoginUser loginUser=loginUserService.getById(id);
        if(loginUser == null){
            return ResponseEty.failure("用户不存在");
        }
        loginUserService.lockLoginUser(loginUser);
        systemLogService.saveMachineLog(token,"禁用","禁用了用户:"+loginUser.getLoginUserName());

        return ResponseEty.success("操作成功");
    }
    @PostMapping("resetPassword")
    @ResponseBody
    @SysLog("重置用户密码为123456")
    public ResponseEty resetPassword(@RequestParam(value = "id",required = false)Integer id,@RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        LoginUser loginUser=loginUserService.getById(id);
        if(loginUser == null){
            return ResponseEty.failure("用户不存在");
        }

        loginUser.setLoginUserPass(CryptographyUtil.encodeBase64("123456"));
        loginUserService.updateById(loginUser);
        systemLogService.saveMachineLog(token,"更新","重置了用户:"+loginUser.getLoginUserName()+"的密码");

        return ResponseEty.success("重置密码为123456成功");
    }

}
