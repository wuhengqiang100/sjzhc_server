package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;

/**
 * 登录用户实体类
 */
@TableName("SYS_LOGIN_USERS")
@KeySequence(value = "SQ_SYS_LOGIN_USERS", clazz = Integer.class)
public class LoginUser{
    @TableId(type = IdType.INPUT)
    @TableField(value = "LOGIN_ID")
    private Integer loginId;//人员序号

    @TableField(value = "OPERATOR_ID")
    private Integer operatorId; //用户Id,外键

    @TableField(value = "LOGIN_NAME")
    private String loginName; //登录名称

    @TableField(value = "LOGIN_PASS")
    private String loginPass; //登录密码


    /**
     * 工作状态:0 未工作,1 在工作
     */
    @TableField(value = "USER_INWORK")
    protected Boolean userInWork;
    /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "USE_FLAG")
    protected Boolean useFlag;
    @TableField(exist = false)
    private Integer [] roleIds;//角色ids

    public Integer[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Integer[] roleIds) {
        this.roleIds = roleIds;
    }

    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }

    public Boolean getUserInWork() {
        return userInWork;
    }

    public void setUserInWork(Boolean userInWork) {
        this.userInWork = userInWork;
    }

    public Boolean getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(Boolean useFlag) {
        this.useFlag = useFlag;
    }
}
