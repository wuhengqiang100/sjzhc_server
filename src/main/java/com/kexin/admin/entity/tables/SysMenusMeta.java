package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;

/**
 * 系统路由描述实体类
 */

//@KeySequence(value = "SQ_DIC_MACHINES", clazz = Integer.class)
@TableName("SYS_MENUS_META")
public class SysMenusMeta {
    
    @TableId(type = IdType.INPUT)
    @TableField(value = "ID")
    @JsonIgnore
    private Integer id;//菜单描述主键id

    @TableField(value = "TITLE")
    private String title;//设置该路由在侧边栏和面包屑中展示的名字

    @TableField(value = "ROLES")
    private String[] roles;//设置该路由进入的权限，支持多个权限叠加['admin','editor']

    @TableField(value = "ICON")
    private String icon;//设置该路由的图标

    @TableField(value = "NO_CACHE")
    protected Boolean noCache;//如果设置为true，则不会被 <keep-alive> 缓存(默认 false)

    @TableField(value = "BREADCRUMB")
    protected Boolean breadcrumb;//如果设置为false，则不会在breadcrumb面包屑中显示

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
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

    @Override
    public String toString() {
        return "SysMenusMeta{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", roles=" + Arrays.toString(roles) +
                ", icon='" + icon + '\'' +
                ", noCache=" + noCache +
                ", breadcrumb=" + breadcrumb +
                '}';
    }
}
