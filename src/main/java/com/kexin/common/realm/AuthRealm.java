package com.kexin.common.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kexin.admin.entity.tables.LoginUser;
import com.kexin.admin.entity.tables.Operator;
import com.kexin.admin.entity.tables.Role;
import com.kexin.admin.entity.tables.SysUserRoles;
import com.kexin.admin.service.LoginUserService;
import com.kexin.admin.service.OperatorService;
import com.kexin.admin.service.RoleService;
import com.kexin.admin.service.UserRoleService;
import com.kexin.common.util.Constants;
import com.kexin.common.util.encry.CryptographyUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.subject.WebSubject;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component(value = "authRealm")
public class AuthRealm extends AuthorizingRealm {


    @Resource
    @Lazy
    private LoginUserService loginUserService;

    @Resource
    @Lazy
    private OperatorService operatorService;

    @Resource
    @Lazy
    private UserRoleService userRoleService;
    @Resource
    @Lazy
    private RoleService roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        ShiroUser shiroUser = (ShiroUser)principals.getPrimaryPrincipal();
        Operator operator=operatorService.getById(shiroUser.id);
        QueryWrapper<LoginUser> loginUserQueryWrapper=new QueryWrapper<>();
        loginUserQueryWrapper.eq("OPERATOR_ID",operator.getOperatorId());
        LoginUser loginUser=loginUserService.getOne(loginUserQueryWrapper);

        QueryWrapper<SysUserRoles> sysUserRolesQueryWrapper=new QueryWrapper<>();
        sysUserRolesQueryWrapper.eq("LOGIN_ID",loginUser.getOperatorId());
        List<SysUserRoles> sysUserRolesList=userRoleService.list(sysUserRolesQueryWrapper);


//        Set<Role> roles = user.getRoleLists();
        Set<String> roleNames = new HashSet();
        Role role;
        for (SysUserRoles userRole : sysUserRolesList) {
    /*        if(StringUtils.isNotBlank(userRole.getName())){
                roleNames.add(role.getName());
            }*/
            role=roleService.getById(userRole.getRoleId());
            roleNames.add(role.getRoleName());

        }
//        Set<Menu> menus = user.getMenus();
//        Set<String> permissions =  new HashSet();
//        for (Menu menu : menus) {
//            if(StringUtils.isNotBlank(menu.getPermission())){
//                permissions.add(menu.getPermission());
//            }
//        }
        info.setRoles(roleNames);
//        info.setStringPermissions(permissions);

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = (String)token.getPrincipal();
//        Object password = token.getCredentials();

        LoginUser user = loginUserService.selectLoginUserByName(username);
        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }

        ServletRequest request = ((WebSubject)SecurityUtils.getSubject()).getServletRequest();
        HttpSession httpSession = ((HttpServletRequest)request).getSession();

        ByteSource salt = ByteSource.Util.bytes(user.getLoginUserName());
        //暂时系统id先写这 1 测试数据
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                new ShiroUser(user.getLoginId(),user.getLoginUserName()),
                CryptographyUtil.md5(user.getLoginUserPass(),user.getLoginUserName()),//先加密再解密
//                user.getLoginPass(), //密码
                salt,
                getName()  //realm name
        );
/*        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                new ShiroUser(user.getOperatorId(),user.getLoginName()),
                CryptographyUtil.encodeBase64(user.getLoginPass())
                ,//先加密再解密
                getName()  //realm name
        );*/

        return authenticationInfo;
    }

    public void removeUserAuthorizationInfoCache(String username) {
        SimplePrincipalCollection pc = new SimplePrincipalCollection();
        pc.add(username, super.getName());
        super.clearCachedAuthorizationInfo(pc);
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Constants.HASH_ALGORITHM);
        matcher.setHashIterations(Constants.HASH_INTERATIONS);
        // storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
//        matcher.setStoredCredentialsHexEncoded(false);
        setCredentialsMatcher(matcher);
    }

    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public static class ShiroUser implements Serializable {

        private static final long serialVersionUID = -1373760761780840081L;

        public Integer id;
        public String loginName;
//        public String nickName;
//        public String icon;

        public ShiroUser(Integer id, String loginName) {
            this.id = id;
            this.loginName = loginName;
//            this.nickName = nickName;
//            this.icon=icon;
        }

        public String getloginName() {
            return loginName;
        }
//        public String getNickName() {
//            return nickName;
//        }
        //        public String getIcon() {
//            return icon;
//        }
        public Integer getId() {
            return id;
        }



        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
//        @Override
//        public String toString() {
//            return nickName;
//        }

        /**
         * 重载hashCode,只计算loginName;
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(loginName);
        }

        /**
         * 重载equals,只计算loginName;
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ShiroUser other = (ShiroUser) obj;
            if (loginName == null) {
                return other.loginName == null;
            } else return loginName.equals(other.loginName);
        }
    }
}
