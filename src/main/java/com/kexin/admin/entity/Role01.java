package com.kexin.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kexin.admin.entity.tables.User;
import com.kexin.common.base.DataEntityAutoId;

import java.util.Set;

@TableName("sys_role")
public class Role01 extends DataEntityAutoId<Role01> {

//    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    private String name;

    @TableField(exist = false)
    private Set<Menu> menuSet;

    @TableField(exist = false)
    private Set<User> userSet;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Menu> getMenuSet() {
        return menuSet;
    }

    public void setMenuSet(Set<Menu> menuSet) {
        this.menuSet = menuSet;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }
}
