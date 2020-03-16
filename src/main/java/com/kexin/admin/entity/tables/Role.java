package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kexin.admin.entity.vo.AllFunction;
import com.kexin.common.base.TableEntity;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色实体类
 */
@TableName("SYS_ROLE")
@KeySequence(value = "SQ_SYS_ROLE", clazz = Integer.class)
public class Role{

    @TableId(type = IdType.INPUT)
    @TableField(value = "ROLE_ID")
    private Integer roleId;//角色id

     @TableField(value = "ROLE_NAME")
    private String roleName; //角色名称

    @TableField(exist = false)
    private Integer [] menuIds;//菜单权限ids

    /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "USE_FLAG", fill = FieldFill.INSERT_UPDATE)
    protected Boolean useFlag;

    /**
     * 说明
     */
    @TableField(value = "NOTE")
    protected String note;


    public Integer[] getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(Integer[] menuIds) {
        this.menuIds = menuIds;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Boolean getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(Boolean useFlag) {
        this.useFlag = useFlag;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
