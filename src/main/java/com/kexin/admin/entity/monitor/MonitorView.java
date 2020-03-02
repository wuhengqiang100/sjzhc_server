package com.kexin.admin.entity.monitor;

public class MonitorView {

    private int machineId;//设备id
    private String machineName;//设备名称

    private Integer operatorId;//人员id

    private String operatorName;//人员名称

    private String operatorCode;//人员编号

    private int receiveNum;//领用数量

    private int recountNum;//已复点数量

    private int online;//是否在线 0不在线,1在线
    private Integer finished;//是否完成 0未完成,1已完成

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public int getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(int receiveNum) {
        this.receiveNum = receiveNum;
    }

    public int getRecountNum() {
        return recountNum;
    }

    public void setRecountNum(int recountNum) {
        this.recountNum = recountNum;
    }
}
