package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.List;


/**
 * 功能权限菜单表实体
 */


//@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("SYS_FUNCTONS")
@KeySequence(value = "SQ_SYS_FUNCTIONS")
@Data
public class SysFunctions {
    
    @TableId(type = IdType.INPUT)
    @TableField(value = "FUNCTON_ID")
    private Integer functonId;//菜单主键id

    @TableField(value = "FUNCTON_TYPE_ID")
    private Integer functonTypeId;//模块类型：1  B端权限 2 C端权限

    @TableField(value = "FUNCTON_CODE")
    private String functonCode;//模块编码

    @TableField(value = "FUNCTON_NAME")
    private String name;//模块名称

    @TableField(value = "TITLE")
    private String title;//设置该路由在侧边栏和面包屑中展示的名字

    @TableField(value = "FUNCTON_PARENT_ID")
    private Integer parentId;//父类id

    @TableField(value = "FUNCTON_LEVEL_ID")
    private Integer functonLevelId;//模块层级（用于菜单）

    @TableField(value = "FUNCTON_PARENT_IDS")
    private String childrenIds;//子模块联集（用于菜单）

    @TableField(value = "FUNCTON_SORT")
    private Integer sort;//模块排序（用于菜单）

    @TableField(value = "FUNCTON_HREF")
    private String path;//链接地址（用于菜单 path字段）

    /**
     * 不在侧标栏显示:0 显示,1 不显示,默认显示0
     */
    @TableField(value = "IS_SHOW")
    private Boolean hidden;

    @TableField(value = "PERMISSION")
    private String roles;//用于菜单(菜单ROLES字段)

    /**
     * 1 删除,默认显示0
     */
    @TableField(value = "DEL_FLAG")
    private Boolean delFlag;

    @TableField(value = "COMPONENT")
    private String component;//请求的组件

    /**
     * 一直显示跟路由:1 显示,0 不显示,默认显示1
     */
    @TableField(value = "ALWAYS_SHOW")
    protected Boolean alwaysShow;



    @TableField(value = "ICON")
    private String icon;//设置该路由的图标

    @TableField(value = "NO_CACHE")
    protected Boolean noCache;//如果设置为true，则不会被 <keep-alive> 缓存(默认 false)

    @TableField(value = "BREADCRUMB")
    protected Boolean breadcrumb;//如果设置为false，则不会在breadcrumb面包屑中显示


    @TableField(value = "REDIRECT")
    private String redirect;//重定向

    @TableField(exist = false)
    private List<SysFunctions> children;

 }
