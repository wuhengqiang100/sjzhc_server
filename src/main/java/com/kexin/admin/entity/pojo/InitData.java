package com.kexin.admin.entity.pojo;


import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 登录成功后才到以及用户信息,公用信息的初始化
 */
public class InitData {
    private InitApp app;//系统说明信息

    private InitUser user;//初始化用户信息


    private JSONArray menu;

//    private InitMenu[] menu;

    public InitApp getApp() {
        return app;
    }

    public void setApp(InitApp app) {
        this.app = app;
    }

    public InitUser getUser() {
        return user;
    }

    public void setUser(InitUser user) {
        this.user = user;
    }

    public JSONArray getMenu() {
        return menu;
    }

    public void setMenu(JSONArray menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "InitData{" +
                "app:" + app +
                ", user:" + user +
                ", menu:" + menu +
                '}';
    }
}
