package com.kexin.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.login.TokenUser;
import com.kexin.admin.entity.login.Tokens;
import com.kexin.admin.entity.tables.LoginUser;
import com.kexin.admin.entity.tables.Operator;
import com.kexin.admin.entity.tables.Role;
import com.kexin.admin.entity.tables.SysUserRoles;
import com.kexin.admin.entity.vo.Ftp;
import com.kexin.admin.entity.vo.SystemWebApi;
import com.kexin.admin.mapper.LoginUserMapper;
import com.kexin.admin.mapper.OperatorMapper;
import com.kexin.admin.mapper.RoleMapper;
import com.kexin.admin.mapper.UserRoleMapper;
import com.kexin.admin.service.LoginUserService;
import com.kexin.admin.service.SystemLogService;
import com.kexin.common.util.ResponseEty;
import com.kexin.common.util.encry.CryptographyUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class LoginUserServiceImpl extends ServiceImpl<LoginUserMapper, LoginUser> implements LoginUserService {


    @Autowired
    SystemWebApi systemWebApi;

    @Autowired
    SystemLogService systemLogService;//系统日志记录service

    @Override
    public ResponseEty login(Map map, HttpSession session) {
        ResponseEty responseEty=new ResponseEty();
        if (map.size()!=2){
            return ResponseEty.failure("请输入用户名或者密码!");
        }

        String userName= (String) map.get("username");
        String password= (String) map.get("password");

        LoginUser loginUser=baseMapper.selectLoginUserByName(userName);

        if (null==loginUser){
            responseEty.setSuccess(60204);
            responseEty.setMessage("没有这个用户");
            return responseEty;
        }
        String errorMsg = null;
        Subject user = SecurityUtils.getSubject();
//            byte[] salt = Encodes.decodeHex("08c5900125b80cd2");
//            ByteSource.Util.bytes(salt);
//            Encodes.encodeHex(password);
//            new Md5Hash(password).toString();
//            UsernamePasswordToken token = new UsernamePasswordToken(username, CryptographyUtil.md5(password,password) ,Boolean.valueOf(rememberMe));
//            UsernamePasswordToken token = new UsernamePasswordToken(username,  CryptographyUtil.md5(password,password) ,Boolean.valueOf(rememberMe));

        UsernamePasswordToken token = new UsernamePasswordToken(userName, CryptographyUtil.encodeBase64(password) ,false);
//            System.out.println("token密码:"+CryptographyUtil.md5NotSalt(password));
        try {
            user.login(token);

            Tokens tokens=new Tokens();
            // 讲用户的operatorId作为用户登陆的token
            tokens.setToken(String.valueOf(loginUser.getLoginId()));//后台shiro和前台权限token都是operatorId
            responseEty.setSuccess(20000);
            responseEty.setData(tokens);
            systemLogService.saveMachineLog(loginUser.getLoginId(),"登陆","登陆了系统");
//            session.setAttribute("tokenName",userName);
//            return responseEty;
        }catch (IncorrectCredentialsException e) {
            System.out.println("异常:"+e);
            errorMsg = "用户名密码错误!";
            responseEty.setSuccess(60204);
            responseEty.setMessage(errorMsg);
        }catch (UnknownAccountException e) {
            errorMsg = "账户不存在!";
            responseEty.setSuccess(60204);
            responseEty.setMessage(errorMsg);
        }finally {
            return  responseEty;
        }
/*        if (password.equals(loginUser.getLoginPass())){
            Tokens tokens=new Tokens();
            // 讲用户的operatorId作为用户登陆的token
            tokens.setToken(String.valueOf(loginUser.getLoginId()));
            responseEty.setSuccess(20000);
            responseEty.setData(tokens);
            session.setAttribute("tokenName",userName);
            return responseEty;
        }*/
//        return ResponseEty.failure("服务器请求失败!");
    }

    // 用户和角色关系mapper
    @Resource
    UserRoleMapper userRoleMapper;

    // 角色mapper
    @Resource
    RoleMapper roleMapper;

    @Resource
    OperatorMapper operatorMapper;

    @Resource
    LoginUserMapper loginUserMapper;



    @Override
    public ResponseEty userInfo(String token) {
        ResponseEty responseEty=new ResponseEty();
        TokenUser tokenUser=new TokenUser();
        LoginUser loginUser=loginUserMapper.selectById(Integer.parseInt(token));
        Operator operator=operatorMapper.selectById(loginUser.getOperatorId());
        QueryWrapper<SysUserRoles> sysUserRolesQueryWrapper=new QueryWrapper<>();
        sysUserRolesQueryWrapper.eq("LOGIN_ID",token);
        List<SysUserRoles> sysUserRolesList=userRoleMapper.selectList(sysUserRolesQueryWrapper);
        if(sysUserRolesList.size()==0){
            return  responseEty.setMessage("该用户没有角色权限,请联系管理员授权");
        }
        String[] roles=new String[sysUserRolesList.size()];
        SysUserRoles sysUserRoles;
        for (int i = 0; i < sysUserRolesList.size(); i++) {
            sysUserRoles=sysUserRolesList.get(i);
            Role role=roleMapper.selectById(sysUserRoles.getRoleId());
            roles[i]=role.getRoleName();
        }
//        roles[0]="admin";
        tokenUser.setRoles(roles);

//        tokenUser.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        tokenUser.setAvatar("http://"+systemWebApi.getAddress()+":"+systemWebApi.getPort()+"/static/admin/img/touxiangDa.gif");

        tokenUser.setIntroduction("I am a super administrator");
        tokenUser.setName(operator.getOperatorName());
        responseEty.setSuccess(20000);
        responseEty.setData(tokenUser);
        return responseEty;
    }

    @Override
    public LoginUser selectLoginUserByName(String userName) {
        return baseMapper.selectLoginUserByName(userName);
    }

    @Override
    public Integer loginUserCountByOperatorId(Integer operatorId) {
        QueryWrapper<LoginUser> wrapper = new QueryWrapper<>();
        wrapper.eq("OPERATOR_ID",operatorId);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)


    @Override
    public Integer loginUserCountByName(String loginName) {
        QueryWrapper<LoginUser> wrapper = new QueryWrapper<>();
        wrapper.eq("LOGIN_USER_NAME",loginName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEty saveLoginUser(LoginUser loginUser,Integer  token) {


        baseMapper.insert(loginUser);
        if(loginUser.getLoginId()==null){
            return ResponseEty.failure("保存信息出错");
        }
//        Integer operatorId=loginUser.getOperatorId();
        Integer loginId=loginUser.getLoginId();
        if (loginUser.getRoleIds()!=null){
            //先删除原来的关系数据
//            userRoleMapper.deleleByLoginId(loginId);
            //再添加新的数据
            Integer [] checkedRole=loginUser.getRoleIds();
            SysUserRoles sysUserRoles=null;
            for (Integer roleId:checkedRole) {
                sysUserRoles=new SysUserRoles();
                sysUserRoles.setLoginId(loginId);
                sysUserRoles.setRoleId(roleId);
                userRoleMapper.insert(sysUserRoles);
            }
        }
        systemLogService.saveMachineLog(token,"新增","新增了用户:"+loginUser.getLoginUserName());

        return ResponseEty.success("保存成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEty updateLoginUser(LoginUser loginUser,Integer  token) {
//        dropUserRolesByUserId(user.getLoginId());

        baseMapper.updateById(loginUser);
        if(loginUser.getLoginId()==null){
            return ResponseEty.failure("保存信息出错");
        }
//        Integer operatorId=loginUser.getOperatorId();
        Integer loginId=loginUser.getLoginId();
        if (loginUser.getRoleIds()!=null){
            //先删除原来的关系数据
            QueryWrapper<SysUserRoles> sysUserRolesQueryWrapper=new QueryWrapper<>();
            sysUserRolesQueryWrapper.eq("LOGIN_ID",loginId);
            userRoleMapper.delete(sysUserRolesQueryWrapper);
            //再添加新的数据
            Integer [] checkedRole=loginUser.getRoleIds();
            SysUserRoles sysUserRoles=null;
            for (Integer roleId:checkedRole) {
                sysUserRoles=new SysUserRoles();
                sysUserRoles.setLoginId(loginId);
                sysUserRoles.setRoleId(roleId);
                userRoleMapper.insert(sysUserRoles);
            }
        }
        systemLogService.saveMachineLog(token,"更新","更新了用户:"+loginUser.getLoginUserName());

        return ResponseEty.success("保存成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEty deleteLoginUser(Integer id,Integer  token) {
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        LoginUser loginUser=baseMapper.selectById(id);
        if(loginUser == null){
            return ResponseEty.failure("用户不存在");
        }
        //删除关系表数据
        QueryWrapper<SysUserRoles> sysUserRolesQueryWrapper=new QueryWrapper<>();
        sysUserRolesQueryWrapper.eq("LOGIN_ID",loginUser.getLoginId());
        userRoleMapper.delete(sysUserRolesQueryWrapper);

        //删除用户数据
        baseMapper.deleteById(loginUser.getLoginId());
        systemLogService.saveMachineLog(token,"删除","删除了用户:"+loginUser.getLoginUserName());
        return ResponseEty.success("删除成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockLoginUser(LoginUser loginUser) {
//        loginUser.setUseFlag(loginUser.getUseFlag()?false:true);
        baseMapper.updateById(loginUser);
    }
}
