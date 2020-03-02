package com.kexin.admin.entity.pojo;

/**
 * 系统的说明信息
 */
public class InitApp {
    private String name;//系统名称
    private String description;//系统描述

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "InitApp{" +
                "name:'" + name + '\'' +
                ", description:'" + description + '\'' +
                '}';
    }
}
