package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kexin.common.base.TableEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 产品信息类
 */
@TableName(value = "DIC_PRODUCTS")
@KeySequence(value = "SQ_DIC_PRODUCTS", clazz = Integer.class)
public class Products{
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    @TableField(value = "PRODUCT_ID")
    private Integer productId;//产品id

    @TableField(value = "PRODUCT_CODE")
    private String productCode;//产品编号

    @TableField(value = "PRODUCT_NAME")
    private String productName;//产品名称

    @TableField(value = "CARTNUM_FIRST_ID", strategy = FieldStrategy.IGNORED)
    private Integer cartnumFirstId;//外键（首字母）

    @TableField(value = "CARTNUM_FIRST_DATE")
    private Date cartnumFirstDate;//首字母启用日期

    @TableField(value = "CARTNUM_FIRST_COUNT")
    private Integer cartnumFirstCount;//首字母启用次数

    @TableField(value = "ROW_NUMBER")
    private Integer rowNumber;//印刷行数

    @TableField(value = "COL_NUMBER")
    private Integer colNumber;//印刷列数

    @TableField(value = "CONVERT_SHEET_NUMBER")
    private Integer convertSheetNumber;//总开数

    @TableField(value = "USE_FLAG")
        private Boolean useFlag;//启用状态

    @TableField(value = "START_DATE", strategy = FieldStrategy.IGNORED)
    private Date startDate;//开始日期

    @TableField(value = "END_DATE", strategy = FieldStrategy.IGNORED)
    private Date endDate;//结束日期

    @TableField(value = "NOTE")
    private String note;//备注

    @TableField(value = "SHEET_WASTER_NUM")
    private Integer sheetWasterNum;//大张废数量

//    @TableField(value = "FTP_REPORT_PATH")
//    private String ftpReportPath;//ftp上传路径

//    @TableField(value = "PRODUCT_CODE_MES")
//    private String productCodeMes;//MES产品代码




    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getCartnumFirstId() {
        return cartnumFirstId;
    }

    public void setCartnumFirstId(Integer cartnumFirstId) {
        this.cartnumFirstId = cartnumFirstId;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCartnumFirstDate() {
        return cartnumFirstDate;
    }

    public void setCartnumFirstDate(Date cartnumFirstDate) {
        this.cartnumFirstDate = cartnumFirstDate;
    }

    public Integer getCartnumFirstCount() {
        return cartnumFirstCount;
    }

    public void setCartnumFirstCount(Integer cartnumFirstCount) {
        this.cartnumFirstCount = cartnumFirstCount;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Integer getColNumber() {
        return colNumber;
    }

    public void setColNumber(Integer colNumber) {
        this.colNumber = colNumber;
    }

    public Integer getConvertSheetNumber() {
        return convertSheetNumber;
    }

    public void setConvertSheetNumber(Integer convertSheetNumber) {
        this.convertSheetNumber = convertSheetNumber;
    }

    public Boolean getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(Boolean useFlag) {
        this.useFlag = useFlag;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getSheetWasterNum() {
        return sheetWasterNum;
    }

    public void setSheetWasterNum(Integer sheetWasterNum) {
        this.sheetWasterNum = sheetWasterNum;
    }

}
