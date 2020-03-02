package com.kexin.admin.entity.monitor;

public class MonitorPub {


    private int total;//共计多少台设备

    private int onlineNum;//在线数量

    private int completedNum;//已完成数量

    private int unfinishedNum;//未完成数量

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(int onlineNum) {
        this.onlineNum = onlineNum;
    }

    public int getCompletedNum() {
        return completedNum;
    }

    public void setCompletedNum(int completedNum) {
        this.completedNum = completedNum;
    }

    public int getUnfinishedNum() {
        return unfinishedNum;
    }

    public void setUnfinishedNum(int unfinishedNum) {
        this.unfinishedNum = unfinishedNum;
    }
}
