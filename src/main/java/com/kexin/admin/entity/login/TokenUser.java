package com.kexin.admin.entity.login;

/**
 * @Description:获取token后的用户实体信息
 * @Author: 巫恒强
 * @Date: 2020/2/24 14:52
 */
public class TokenUser {

    private String [] roles;//数组的角色

    private String introduction;//个人介绍

    private String  avatar;//头像信息

    private String  name;//用户昵称

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
