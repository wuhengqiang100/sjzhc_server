package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 角色菜单关系实体类
 * 多对多关系对应扩展类
 */

@KeySequence(value = "SQ_SYS_ROLE_MENUS", clazz = Integer.class)
@TableName("SYS_ROLE_MENUS")
@Data
public class SysRoleMenus {


    @TableId(type = IdType.INPUT)
    @TableField(value = "ID")
    private Integer id;//菜单主键id
    
//    @TableId(type = IdType.INPUT)
    @TableField(value = "ROLE_ID")
    private Integer roleId;//角色id

    @TableField(value = "MENU_ID")
    private Integer functionId;//菜单id
}
