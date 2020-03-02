package com.kexin.admin.entity.pojo;

import com.kexin.admin.entity.vo.ShowMenuVo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 系统初始化时的菜单加载类
 */
public class InitMenu {

    private String text;//导航名称

    private String i18n;//国际化

    private Boolean group;//分组

    private Boolean hideInBreadcrumb;//是否隐藏

    private List<InitMenu> children=new ArrayList<>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getI18n() {
        return i18n;
    }

    public void setI18n(String i18n) {
        this.i18n = i18n;
    }

    public Boolean getGroup() {
        return group;
    }

    public void setGroup(Boolean group) {
        this.group = group;
    }

    public Boolean getHideInBreadcrumb() {
        return hideInBreadcrumb;
    }

    public void setHideInBreadcrumb(Boolean hideInBreadcrumb) {
        this.hideInBreadcrumb = hideInBreadcrumb;
    }

    public List<InitMenu> getChildren() {
        return children;
    }

    public void setChildren(List<InitMenu> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "InitMenu{" +
                "text:'" + text + '\'' +
                ", i18n:'" + i18n + '\'' +
                ", group:" + group +
                ", hideInBreadcrumb:" + hideInBreadcrumb +
                ", children:" +"["+ children +"]"+
                '}';
    }
}
