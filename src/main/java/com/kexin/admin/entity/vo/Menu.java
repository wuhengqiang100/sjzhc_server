package com.kexin.admin.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kexin.admin.entity.tables.SysFunctions;

import java.util.List;

/**
 * 最终生成的菜单实体
 */
public class Menu {


    private String path;//请求路径


    private String component;//请求的组件


    private String redirect;//重定向


    private String name;//路由名称
    /**
     * 不在侧标栏显示:0 显示,1 不显示,默认显示0
     */
    private Boolean hidden;

    /**
     * 一直显示跟路由:1 显示,0 不显示,默认显示1
     */
    private Boolean alwaysShow;

    private MenuMetas meta;
    private List<Menu> children;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Boolean getAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(Boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public MenuMetas getMeta() {
        return meta;
    }

    public void setMeta(MenuMetas meta) {
        this.meta = meta;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }
}
