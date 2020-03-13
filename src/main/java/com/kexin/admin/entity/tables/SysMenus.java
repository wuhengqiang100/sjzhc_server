package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

/**
 * 系统路由实体类
 */

//@KeySequence(value = "SQ_DIC_MACHINES", clazz = Integer.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("SYS_MENUS")
public class SysMenus {
    
    @TableId(type = IdType.INPUT)
    @TableField(value = "ID")
    @JsonIgnore
    private Integer id;//菜单主键id
    @JsonIgnore
    @TableField(value = "PARENT_ID")
    private Integer parentId;//菜单父id,只能有一个


    @TableField(value = "PATH")
    private String path;//请求路径

    @TableField(value = "COMPONENT")
    private String component;//请求的组件

    @TableField(value = "REDIRECT")
    private String redirect;//重定向

    @TableField(value = "NAME")
    private String name;//路由名称
    /**
     * 不在侧标栏显示:0 显示,1 不显示,默认显示0
     */
    @TableField(value = "HIDDEN")
    protected Boolean hidden;

    /**
     * 一直显示跟路由:1 显示,0 不显示,默认显示1
     */
    @TableField(value = "ALWAYS_SHOW")
    protected Boolean alwaysShow;

    @JsonIgnore
    @TableField(value = "META_ID")
    private Integer metaId;//路由描述外键

    @TableField(exist = false)
    private SysMenusMeta meta;
    @JsonIgnore
    @TableField(value = "CHILDREN_IDS")
    private String childrenIds;//路由描述外键

    @TableField(exist = false)
    private List<SysMenus> children;
    public SysMenusMeta getMeta() {
        return meta;
    }

    public void setMeta(SysMenusMeta meta) {
        this.meta = meta;
    }

    public List<SysMenus> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenus> children) {
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

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

    public Integer getMetaId() {
        return metaId;
    }

    public void setMetaId(Integer metaId) {
        this.metaId = metaId;
    }

    public String getChildrenIds() {
        return childrenIds;
    }

    public void setChildrenIds(String childrenIds) {
        this.childrenIds = childrenIds;
    }

    @Override
    public String toString() {
        return "SysMenus{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", path='" + path + '\'' +
                ", component='" + component.replace("\"", "") + '\'' +
                ", redirect='" + redirect + '\'' +
                ", name='" + name + '\'' +
                ", hidden=" + hidden +
                ", alwaysShow=" + alwaysShow +
                ", metaId=" + metaId +
                ", meta=" + meta +
                ", childrenIds='" + childrenIds + '\'' +
                ", children=" + children +
                '}';
    }
}
