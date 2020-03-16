package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * 角色菜单关系实体类
 * 多对多关系对应扩展类
 */

@KeySequence(value = "SQ_SYS_ROLE_MENUS", clazz = Integer.class)
@TableName("SYS_ROLE_MENUS")
public class SysRoleMenus {


    @TableId(type = IdType.INPUT)
    @TableField(value = "ID")
    private Integer id;//菜单主键id
    
//    @TableId(type = IdType.INPUT)
    @TableField(value = "ROLE_ID")
    private Integer roleId;//角色id

    @TableField(value = "MENU_ID")
    private Integer menuId;//菜单id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}
