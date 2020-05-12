package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 角色实体类
 */
@TableName("SYS_ROLES")
@KeySequence(value = "SQ_SYS_ROLE", clazz = Integer.class)
@Data
public class Role{

    @TableId(type = IdType.INPUT)
    @TableField(value = "ROLE_ID")
    private Integer roleId;//角色id
    @TableField(value = "ROLE_CODE")
    private String roleCode;//角色code

     @TableField(value = "ROLE_NAME")
    private String roleName; //角色名称

    @TableField(exist = false)
    private Integer [] menuIds;//菜单权限ids

     @TableField(exist = false)
    private String [] checkedPermiss;//c端权限的titles


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


}
