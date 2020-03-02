package com.kexin.admin.entity.monitor;

/**
 * @Description:控制台搜索实体类
 * @Author: 巫恒强
 * @Date: 2019/10/25 15:27
 */
public class MonitorSearch {

    private Integer groupId;//设备分组id

    private Integer online;//设备是否上线 0不在线,1上线

    private Integer finished;//任务是否完成 0未完成,1已完成

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }
}
