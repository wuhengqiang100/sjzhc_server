package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 登录用户实体类
 */
@TableName("SYS_LOGIN_USERS")
@KeySequence(value = "SQ_SYS_LOGIN_USERS")
@Data
public class LoginUser{
    @TableId(type = IdType.INPUT)
    @TableField(value = "LOGIN_ID")
    private Integer loginId;//人员序号

    @TableField(value = "OPERATOR_ID")
    private Integer operatorId; //用户Id,外键



    @TableField(value = "LOGIN_USER_NAME")
    private String loginUserName; //登录名称 (新系统)

    @TableField(value = "LOGIN_USER_PASS")
    private String loginUserPass; //登录密码 (新系统)

    /*@TableField(value = "LOGIN_NAME")
    private String loginName; //登录名称 (老系统)//已废弃

    @TableField(value = "LOGIN_PASS")
    private String loginPass; //登录密码 (老系统)//已废弃*/


/*    *//**
     * 工作状态:0 未工作,1 在工作
     *//*
    @TableField(value = "USER_INWORK")
    protected Boolean userInWork;*/
    /**
     * 启用状态:0 禁止,1 启用
     */
/*    @TableField(value = "USE_FLAG")
    protected Boolean useFlag;*/
    @TableField(exist = false)
    private Integer [] roleIds;//角色ids

    @TableField(exist = false)
    private Operator operator;

    @TableField(exist = false)
    private String roleString;//用户拥有的角色只用于显示

}
