package com.kexin.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kexin.admin.entity.pojo.InitApp;
import com.kexin.admin.entity.pojo.InitData;
import com.kexin.admin.entity.pojo.InitUser;
import com.kexin.admin.entity.tables.GrantRoleFunctions;
import com.kexin.admin.entity.tables.Role;
import com.kexin.admin.entity.tables.User;
import com.kexin.admin.service.InitService;
import com.kexin.admin.service.RoleService;
import com.kexin.admin.service.UserService;
import com.kexin.common.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class InitServiceImpl implements InitService {

    /*@Autowired
    UserService userService;*/

    @Autowired
    RoleService roleService;

    @Override
    public InitData systemInit(int userId) {

        InitData initData=new InitData();
        //主页
        JSONArray menus=new JSONArray();
        //控制台菜单
        JSONObject menu=new JSONObject();
        menu.put("text","主导航");
        menu.put("i18n","menu.main");
        menu.put("group",true);
        menu.put("hideInBreadcrumb",true);
        JSONArray children=new JSONArray();
        JSONObject yibianpanMenu=getYiBiaoPanMenu();
        //用户管理菜单
        JSONObject useInfoMenu=getUserInfoMenu();
        JSONArray userInfoChild= (JSONArray) useInfoMenu.get("children");
        //设备管理菜单
        JSONObject facilityMenu=getFacilityMenu();
        JSONArray facilityChild= (JSONArray) facilityMenu.get("children");
        //系统设置菜单
        JSONObject systemSettingMenu=getSystemSettingMenu();
        JSONArray systemChild= (JSONArray) systemSettingMenu.get("children");
        //工作台菜单
        JSONObject workbenchMenu=getWorkbenchMenu();
        JSONArray workbenchChild= (JSONArray) workbenchMenu.get("children");

        User user=new User();
//        User user=userService.getUserAllInfoById(userId);
        List<Role> roleList=user.getRoleList();
        for (Role r: roleList) {
            List<GrantRoleFunctions> functionList=roleService.getFunctionIdsByRoleId(r.getRoleId());
            for (GrantRoleFunctions s:functionList) {
                if (s.getFunctionId()== Constants.USER_FUNCTION){//用户管理1
                    userInfoChild.add(getUserManage());
                }  if (s.getFunctionId()== Constants.FACILITY){//设备管理2
                    facilityChild.add(getFacilityList());
                }  if (s.getFunctionId()== Constants.ROLE_FUNCTION){//角色管理3
                    systemChild.add(getSystemRoleList());
                }  if (s.getFunctionId()== Constants.SYSRAPAIR_FUNCTION){//系统维护4
                    systemChild.add(getSystemRepairList());
                }  if (s.getFunctionId()== Constants.MONITOR_FUNCTION){//现场监控5
                    workbenchChild.add(getWorkbenchMonitor());
                }  if (s.getFunctionId()== Constants.IDENTITY_FUNCTION){//身份管理6
                    userInfoChild.add(getIdentityManage());
                }  if (s.getFunctionId()== Constants.FACILITY_TYPE){//设备类别7
                    facilityChild.add(getFacilityTypesList());
                } if (s.getFunctionId()== Constants.FACILITY_GROUP){//设备分组8
                    facilityChild.add(getFacilityGroupsList());
                }if (s.getFunctionId()== Constants.ANALYZE_FUNCTION){//数据分析9
                    workbenchChild.add(getWorkbenchAnalyze());
                }if (s.getFunctionId()== Constants.RT_TASK){//实时任务
                    workbenchChild.add(getWorkbenchTask());
                }
            }

        }
//        children.add(getYiBiaoPanMenu());//仪表盘菜单
        if (workbenchChild.size()>0){
            workbenchMenu.put("children",workbenchChild);
            children.add(workbenchMenu);//工作台菜单
        }
        if (facilityChild.size()>0){
            facilityMenu.put("children",facilityChild);
            children.add(facilityMenu);//设备管理菜单
        }
        if (userInfoChild.size()>0){
            useInfoMenu.put("children",userInfoChild);
            children.add(useInfoMenu); //用户管理菜单
        }
        if (systemChild.size()>0){
            systemSettingMenu.put("children",systemChild);
            children.add(systemSettingMenu);//系统设置菜单
        }








        menu.put("children",children);
        menus.add(menu);
        initData.setApp(getAppInfo());
        initData.setUser(getUserInfo(userId));
        initData.setMenu(menus);
        return initData;
    }

    /**
     * 获取系统的基本信息
     * @return
     */
    private InitApp getAppInfo(){
        //系统信息
        InitApp app=new InitApp();
        app.setName("复点人民币系统");
        app.setDescription("中钞科信");
        return app;
    }

    /**
     * 获取用户信息
     * @return
     */
    private InitUser getUserInfo(int userId){
        //用户信息
        User currentUser=new User();
//        User currentUser=userService.getById(userId);
        InitUser user=new InitUser();
        user.setName(currentUser.getOperatorName());
        user.setEmail(currentUser.getOperatorCode());
        user.setAvatar("./assets/tmp/img/avatar.jpg");
        return user;
    }

    private JSONObject getYiBiaoPanMenu(){
        JSONObject yibiaopan=new JSONObject();
        JSONArray yibiaopanChild=new JSONArray();
        yibiaopan.put("text","仪表盘");
        yibiaopan.put("i18n","menu.dashboard");
        yibiaopan.put("icon","anticon-dashboard");
        yibiaopan.put("children",yibiaopanChild);

        JSONObject yibiaopanV1=new JSONObject();
        yibiaopanV1.put("text","仪表盘V1");
        yibiaopanV1.put("link","/dashboard/v1");
        yibiaopanV1.put("i18n","menu.dashboard.v1");
//        yibiaopanV1.put("reuse",true);

        JSONObject fenxiye=new JSONObject();
        fenxiye.put("text","分析页");
        fenxiye.put("link","/dashboard/analysis");
        fenxiye.put("i18n","menu.dashboard.analysis");

        JSONObject jiankongye=new JSONObject();
        jiankongye.put("text","监控页");
        jiankongye.put("link","/dashboard/monitor");
        jiankongye.put("i18n","menu.dashboard.monitor");
        JSONObject gongzuotai=new JSONObject();
        gongzuotai.put("text","工作台");
        gongzuotai.put("link","/dashboard/workplace");
        gongzuotai.put("i18n","menu.dashboard.workplace");

        yibiaopanChild.add(yibiaopanV1);
        yibiaopanChild.add(fenxiye);
        yibiaopanChild.add(jiankongye);
        yibiaopanChild.add(gongzuotai);
        return yibiaopan;
    }
    /**
     * 获取用户信息管理菜单
     * @return
     */
    private JSONObject getUserInfoMenu(){
        JSONObject userInfo=new JSONObject();
        JSONArray userInfoChild=new JSONArray();
        userInfo.put("text","用户管理");
//        userInfo.put("i18n","menu.dashboard");
        userInfo.put("icon","anticon-user");
        userInfo.put("children",userInfoChild);
        return userInfo;
    }

    //用户管理
    private JSONObject getUserManage(){
        JSONObject userInfoList=new JSONObject();
        userInfoList.put("text","用户管理");
        userInfoList.put("link","/user/list");
        return userInfoList;
    }
    //身份管理
    private JSONObject getIdentityManage(){
        JSONObject systemIdentityList=new JSONObject();
        systemIdentityList.put("text","身份管理");
        systemIdentityList.put("link","/user/identity");
        return systemIdentityList;
    }

    /**
     * 获取设备管理菜单
     * @return
     */
    private JSONObject getFacilityMenu(){
        JSONObject facility=new JSONObject();
        JSONArray facilityChild=new JSONArray();
        facility.put("text","设备管理");
//        facility.put("i18n","menu.dashboard");
        facility.put("icon","anticon-profile");
        facility.put("children",facilityChild);
        return facility;
    }

    //设备信息列表
    private JSONObject getFacilityList(){
        JSONObject facilityList=new JSONObject();
        facilityList.put("text","设备信息列表");
        facilityList.put("link","/facility/list");
//        facilityV1.put("i18n","menu.dashboard.v1");
        return facilityList;
    }

    //设备类别
    private JSONObject getFacilityTypesList(){
        JSONObject facilityTypesList=new JSONObject();
        facilityTypesList.put("text","设备类别");
        facilityTypesList.put("link","/facility/type");
        return facilityTypesList;
    }
    //设备分组
    private JSONObject getFacilityGroupsList(){

        JSONObject facilityGroupsList=new JSONObject();
        facilityGroupsList.put("text","设备分组");
        facilityGroupsList.put("link","/facility/group");
        return facilityGroupsList;
    }



    /**
     * 获取系统设置菜单信息,包括角色权限管理,系统基本维护服务
     * @return
     */
    private JSONObject getSystemSettingMenu(){
        JSONObject system=new JSONObject();
        JSONArray systemChild=new JSONArray();
        system.put("text","系统设置");
//        system.put("i18n","menu.dashboard");
        system.put("icon","anticon-appstore");

        system.put("children",systemChild);
        return system;
    }
    //角色管理
    private JSONObject getSystemRoleList(){
        JSONObject systemRoleList=new JSONObject();
        systemRoleList.put("text","角色管理");
        systemRoleList.put("link","/system/role");
//        systemV1.put("i18n","menu.dashboard.v1");
        return systemRoleList;
    }
    //系统维护
    private JSONObject getSystemRepairList(){
        JSONObject systemRepairList=new JSONObject();
        systemRepairList.put("text","系统维护");
        systemRepairList.put("link","/system/repair");
//        systemV1.put("i18n","menu.dashboard.v1");
        return systemRepairList;
    }
    /**
     *  获取工作台菜单
     * @return
     */
    private JSONObject getWorkbenchMenu(){
        JSONObject workbench=new JSONObject();
        JSONArray workbenchChild=new JSONArray();
        workbench.put("text","工作台");
        workbench.put("icon","anticon-dashboard");
        workbench.put("children",workbenchChild);
        return workbench;
    }
    //现场监控
    private JSONObject getWorkbenchMonitor(){
        JSONObject workbenchMonitor=new JSONObject();
        workbenchMonitor.put("text","现场监控");
        workbenchMonitor.put("link","/works/monitor");
        return  workbenchMonitor;
    }
    //数据分析
    private JSONObject getWorkbenchAnalyze(){
        JSONObject workbenchMonitor=new JSONObject();
        workbenchMonitor.put("text","数据分析");
        workbenchMonitor.put("link","/works/analyze");
        return  workbenchMonitor;
    }
    //实时任务
    private JSONObject getWorkbenchTask(){
        JSONObject workbenchMonitor=new JSONObject();
        workbenchMonitor.put("text","实时任务");
        workbenchMonitor.put("link","/works/task");
//        workbenchMonitor.put("reuse",true);
        return  workbenchMonitor;
    }

/**
     * 根据角色,权限获取菜单信息
     * 重点在权限信息,拼接对应的小菜单,包括一级,二级,三级菜单
     * @param role
     * @return
     *//*

    public JSONArray getMenuByRole(String role){
        JSONArray menu=new JSONArray();

        return menu;
    }
*/

}
