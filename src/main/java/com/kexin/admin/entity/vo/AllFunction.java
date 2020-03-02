package com.kexin.admin.entity.vo;

/**
 * 所有权限实体类
 */
public class AllFunction {

    //true 表示拥有 false 没有
    private Boolean user=false;//用户管理
    private Boolean facility=false;//设备管理
    private Boolean role=false;//角色管理
    private Boolean sysrepair=false;//系统维护
    private Boolean monitor=false;//现场监控
    private Boolean identity=false;//现场监控
    private Boolean fType=false;//设备类别
    private Boolean fGroup=false;//设备分组
    private Boolean analyze=false;//数据分析
    private Boolean task=false;//实时任务

    public Boolean getTask() {
        return task;
    }

    public void setTask(Boolean task) {
        this.task = task;
    }

    public Boolean getAnalyze() {
        return analyze;
    }

    public void setAnalyze(Boolean analyze) {
        this.analyze = analyze;
    }

    public Boolean getfType() {
        return fType;
    }

    public void setfType(Boolean fType) {
        this.fType = fType;
    }

    public Boolean getfGroup() {
        return fGroup;
    }

    public void setfGroup(Boolean fGroup) {
        this.fGroup = fGroup;
    }

    public Boolean getIdentity() {
        return identity;
    }

    public void setIdentity(Boolean identity) {
        this.identity = identity;
    }

    public Boolean getUser() {
        return user;
    }

    public void setUser(Boolean user) {
        this.user = user;
    }

    public Boolean getFacility() {
        return facility;
    }

    public void setFacility(Boolean facility) {
        this.facility = facility;
    }

    public Boolean getRole() {
        return role;
    }

    public void setRole(Boolean role) {
        this.role = role;
    }

    public Boolean getSysrepair() {
        return sysrepair;
    }

    public void setSysrepair(Boolean sysrepair) {
        this.sysrepair = sysrepair;
    }

    public Boolean getMonitor() {
        return monitor;
    }

    public void setMonitor(Boolean monitor) {
        this.monitor = monitor;
    }
}
