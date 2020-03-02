package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 节点实体类
 */
@TableName("DIC_OPERATIONS")
@KeySequence(value = "SQ_DIC_OPERATION", clazz = Integer.class)
public class Operation {

    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.INPUT)
    @TableField(value = "OPERATION_ID")
    private Integer operationId;//节点id

    @TableField(value = "OPERATION_TYPE_ID")
    private Integer operationTypeId;//节点类别id

    @TableField(value = "OPERATION_CODE")
    private String operationCode; //节点代码

    @TableField(value = "OPERATION_NAME")
    private String operationName; //节点名称
    /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "USE_FLAG", fill = FieldFill.INSERT_UPDATE)
    protected Boolean useFlag;
    /**
     * 启用时间,写入时间
     */
    @TableField(value = "START_DATE",strategy= FieldStrategy.IGNORED)
    protected Date startDate;
    /**
     * 禁用时间,结束时间
     */
    @TableField(value = "END_DATE",  strategy = FieldStrategy.IGNORED)
    protected Date endDate;

    /**
     * 说明
     */
    @TableField(value = "NOTE")
    protected String note;

    @TableField(exist = false)
    private OperationType operationType;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public Integer getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(Integer operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public Boolean getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(Boolean useFlag) {
        this.useFlag = useFlag;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getEndDate() {
        return endDate;
    }


}
