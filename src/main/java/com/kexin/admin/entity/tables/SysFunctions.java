package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * 功能权限菜单表实体
 */

//@KeySequence(value = "SQ_DIC_MACHINES", clazz = Integer.class)
//@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("SYS_FUNCTIONS")
public class SysFunctions {
    
    @TableId(type = IdType.INPUT)
    @TableField(value = "FUNCTON_ID")
    private Integer functionId;//菜单主键id

    @TableField(value = "FUNCTON_TYPE_ID")
    private Integer functionTypeId;//模块类型：1  B端权限 2 C端权限

    @TableField(value = "FUNCTON_CODE")
    private String functionCode;//模块编码

    @TableField(value = "FUNCTON_NAME")
    private String name;//模块名称

    @TableField(value = "FUNCTON_PARENT_ID")
    private Integer parentId;//父类id

    @TableField(value = "FUNCTON_LEVEL_ID")
    private Integer functionLevelId;//模块层级（用于菜单）

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

    @TableField(value = "TITLE")
    private String title;//设置该路由在侧边栏和面包屑中展示的名字


    @TableField(value = "ICON")
    private String icon;//设置该路由的图标

    @TableField(value = "NO_CACHE")
    protected Boolean noCache;//如果设置为true，则不会被 <keep-alive> 缓存(默认 false)

    @TableField(value = "BREADCRUMB")
    protected Boolean breadcrumb;//如果设置为false，则不会在breadcrumb面包屑中显示


    @TableField(value = "REDIRECT")
    private String redirect;//重定向

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public Integer getFunctionTypeId() {
        return functionTypeId;
    }

    public void setFunctionTypeId(Integer functionTypeId) {
        this.functionTypeId = functionTypeId;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getFunctionLevelId() {
        return functionLevelId;
    }

    public void setFunctionLevelId(Integer functionLevelId) {
        this.functionLevelId = functionLevelId;
    }

    public String getChildrenIds() {
        return childrenIds;
    }

    public void setChildrenIds(String childrenIds) {
        this.childrenIds = childrenIds;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Boolean getAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(Boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getNoCache() {
        return noCache;
    }

    public void setNoCache(Boolean noCache) {
        this.noCache = noCache;
    }

    public Boolean getBreadcrumb() {
        return breadcrumb;
    }

    public void setBreadcrumb(Boolean breadcrumb) {
        this.breadcrumb = breadcrumb;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
}
