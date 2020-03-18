package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Description:生产日志表对应实体
 * @Author: 巫恒强
 * @Date: 2020/1/3 9:41
 */
@TableName(value = "LOG_PROD_ACTIONS")
public class ProduceLog {

    @TableId(type = IdType.INPUT)
    @TableField(value = "LOG_PROD_ID")
    private Integer logProdId;

    @TableField(value = "LOG_DATE")
    private Date logDate;

    @TableField(value = "PRODUCT_ID")
    private Integer productId;

    @TableField(exist = false)
    private String productName="产品名称";

    @TableField(value = "CART_NUMBER")
    private String cartNumber;

    @TableField(exist = false)
    private Integer totalNum=10000;
    @TableField(exist = false)
    private Integer errorNum=50;

    @TableField(exist = false)
    private Integer notCheckNum=20;

    @TableField(exist = false)
    private String flag01="设备01";

    @TableField(exist = false)
    private String flag02="工序1";



    @TableField(value = "OPERATION_ID")
    private Integer operationId;

    @TableField(exist = false)
    private String operationName="操作名称";

    @TableField(value = "OPERATOR_ID")
    private Integer operatorId;


    @TableField(value = "OPERATOR_NAME")
    private String operatorName;

    @TableField(value = "LOG_TYPE")
    private String logType;

    @TableField(value = "START_DATE")
    private Date startDate;

    @TableField(value = "END_DATE")
    private Date endDate;

    @TableField(value = "ACTION_COUNT")
    private Integer actionCount;

    @TableField(value = "NOTE")
    private String note;

    @TableField(value = "ITEM_FLAG")
    private Integer itemFlag;


    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(Integer errorNum) {
        this.errorNum = errorNum;
    }

    public Integer getNotCheckNum() {
        return notCheckNum;
    }

    public void setNotCheckNum(Integer notCheckNum) {
        this.notCheckNum = notCheckNum;
    }

    public String getFlag01() {
        return flag01;
    }

    public void setFlag01(String flag01) {
        this.flag01 = flag01;
    }

    public String getFlag02() {
        return flag02;
    }

    public void setFlag02(String flag02) {
        this.flag02 = flag02;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public Integer getLogProdId() {
        return logProdId;
    }

    public void setLogProdId(Integer logProdId) {
        this.logProdId = logProdId;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getCartNumber() {
        return cartNumber;
    }

    public void setCartNumber(String cartNumber) {
        this.cartNumber = cartNumber;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
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

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getActionCount() {
        return actionCount;
    }

    public void setActionCount(Integer actionCount) {
        this.actionCount = actionCount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getItemFlag() {
        return itemFlag;
    }

    public void setItemFlag(Integer itemFlag) {
        this.itemFlag = itemFlag;
    }
}
