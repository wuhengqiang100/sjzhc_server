package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 角色实体类
 */
@TableName("SYS_ROLES")
@KeySequence(value = "SQ_SYS_ROLES", clazz = Integer.class)
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
    private Integer [] value;//菜单权限ids

//     @TableField(exist = false)
//    private String [] checkedPermiss;//c端权限的titles

//    @TableField(exist = false)
//    private String direction;//审核的方向  left 回退,right 审核



    /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "USE_FLAG", fill = FieldFill.INSERT_UPDATE)
    protected Boolean useFlag;

    /**
     * 启用时间,写入时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "START_DATE")
    protected Date startDate;
    /**
     * 禁用时间,结束时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "END_DATE")
    protected Date endDate;
    /**
     * 说明
     */
    @TableField(value = "NOTE")
    protected String note;


}
