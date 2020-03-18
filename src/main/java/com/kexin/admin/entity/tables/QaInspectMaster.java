package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Operators;


/**
 * 质量表头信息表
 */
//@KeySequence(value = "SQ_DIC_MACHINES", clazz = Integer.class)
@TableName(value = "QA_INSPECT_MASTER")
public class QaInspectMaster {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    @TableField(value = "INSPECTM_ID")
    private Integer inspectmId;//主键


    @TableField(value = "MACHINE_WASTER_NUMBER")
    private Integer machineWasterNumber;//整万错误数量

    @TableField(value = "INFO_NUMBER")
    private Integer infoNumber;//整万信息数量



    @TableField(value = "ALLOW_JUDGE")
    private Integer allowJudge;//状态编号

    @TableField(exist = false)
    private WipProdLogs wipProdLogs;//生产日志信息

    @TableField(exist = false)
    private WipJobs wipJobs;//基础生产信息

     @TableField(exist = false)
    private Products product;//产品种类

     @TableField(exist = false)
    private Operation operation;//产品工序

     @TableField(exist = false)
    private Operators operator;//操作员信息

    @TableField(exist = false)
    private Machine machine;//机器信息

    @TableField(exist = false)
    private DicWorkUnits dicWorkUnits;//操作台信息

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getMachineWasterNumber() {
        return machineWasterNumber;
    }

    public void setMachineWasterNumber(Integer machineWasterNumber) {
        this.machineWasterNumber = machineWasterNumber;
    }

    public Integer getInfoNumber() {
        return infoNumber;
    }

    public void setInfoNumber(Integer infoNumber) {
        this.infoNumber = infoNumber;
    }

    public Integer getInspectmId() {
        return inspectmId;
    }

    public void setInspectmId(Integer inspectmId) {
        this.inspectmId = inspectmId;
    }

    public Integer getAllowJudge() {
        return allowJudge;
    }

    public void setAllowJudge(Integer allowJudge) {
        this.allowJudge = allowJudge;
    }

    public WipProdLogs getWipProdLogs() {
        return wipProdLogs;
    }

    public void setWipProdLogs(WipProdLogs wipProdLogs) {
        this.wipProdLogs = wipProdLogs;
    }

    public WipJobs getWipJobs() {
        return wipJobs;
    }

    public void setWipJobs(WipJobs wipJobs) {
        this.wipJobs = wipJobs;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Operators getOperator() {
        return operator;
    }

    public void setOperator(Operators operator) {
        this.operator = operator;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public DicWorkUnits getDicWorkUnits() {
        return dicWorkUnits;
    }

    public void setDicWorkUnits(DicWorkUnits dicWorkUnits) {
        this.dicWorkUnits = dicWorkUnits;
    }
}
