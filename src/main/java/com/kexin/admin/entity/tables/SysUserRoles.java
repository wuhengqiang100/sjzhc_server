package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 用户和角色对应关系实体类
 * 多对多关系对应扩展类
 */

@KeySequence(value = "SQ_SYS_USER_ROLES", clazz = Integer.class)
@TableName("SYS_USER_ROLES")
@Data
public class SysUserRoles {


    @TableId(type = IdType.INPUT)
    @TableField(value = "USER_ROLE_ID")
    private Integer userRoleId;//关系主键id

    @TableField(value = "LOGIN_ID")
    private Integer loginId;//用户id

//    @TableId(type = IdType.INPUT)
    @TableField(value = "ROLE_ID")
    private Integer roleId;//角色id

}
