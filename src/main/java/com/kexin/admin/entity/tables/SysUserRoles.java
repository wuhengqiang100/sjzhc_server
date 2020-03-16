package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;

/**
 * 用户和角色对应关系实体类
 * 多对多关系对应扩展类
 */

@KeySequence(value = "SQ_SYS_USER_ROLES", clazz = Integer.class)
@TableName("SYS_USER_ROLES")
public class SysUserRoles {


    @TableId(type = IdType.INPUT)
    @TableField(value = "ID")
    private Integer id;//菜单主键id

    @TableField(value = "USER_ID")
    private Integer userId;//用户id

//    @TableId(type = IdType.INPUT)
    @TableField(value = "ROLE_ID")
    private Integer roleId;//角色id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
