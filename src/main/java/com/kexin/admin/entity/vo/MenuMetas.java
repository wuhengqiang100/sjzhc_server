package com.kexin.admin.entity.vo;

/**
 * menu的附属描述类
 */
public class MenuMetas {


    private String title;//设置该路由在侧边栏和面包屑中展示的名字


    private String[] roles;//设置该路由进入的权限，支持多个权限叠加['admin','editor']


    private String icon;//设置该路由的图标


    private Boolean noCache;//如果设置为true，则不会被 <keep-alive> 缓存(默认 false)


    private Boolean breadcrumb;//如果设置为false，则不会在breadcrumb面包屑中显示


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
}
