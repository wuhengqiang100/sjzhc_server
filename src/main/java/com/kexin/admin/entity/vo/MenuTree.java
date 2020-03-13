package com.kexin.admin.entity.vo;

/**
 * @version v1.0
 * @ProjectName: server
 * @ClassName: MenuTree
 * @Description: TODO(角色权限分配时需要用到的菜单权限实体)
 * @Author: 13015
 * @Date: 2020/3/13 15:48
 */
public class MenuTree {

    private Integer id;

    private String label;// 名称

    private MenuTree[] children;//嵌套的menuTree

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public MenuTree[] getChildren() {
        return children;
    }

    public void setChildren(MenuTree[] children) {
        this.children = children;
    }
}
