package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kexin.common.base.TableEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 产品信息类
 */
@TableName(value = "DIC_PRODUCTS")
@KeySequence(value = "SQ_DIC_PRODUCTS")
@Data
public class Products{
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    @TableField(value = "PRODUCT_ID")
    private Integer productId;//产品id

    @TableField(value = "PRODUCT_CODE")
    private String productCode;//产品编号

    @TableField(value = "PRODUCT_NAME")
    private String productName;//产品名称

    @TableField(value = "CARTNUM_FIRST_ID")
    private Integer cartnumFirstId;//外键（首字母）

    @TableField(exist = false)
    private CartNumFirst cartNumFirst;//首字母序号实体

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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

    @TableField(value = "SHEET_WASTER_NUM")
    private Integer sheetWasterNum;//大张废数量

    @TableField(value = "USE_FLAG")
    private Boolean useFlag;//启用状态

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "START_DATE")
    private Date startDate;//开始日期

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "END_DATE")
    private Date endDate;//结束日期

    @TableField(value = "NOTE")
    private String note;//备注

    @TableField(value = "QA_CODE_NAME")
    private String qaCodeName;//防重号系统的名称

    @TableField(value = "LOCAL_PRODUCT_NAME")
    private String localProductName;//机检系统本地产品名称

    @TableField(value = "PRODUCT_CODE_MES")
    private String productCodeMes;//MES产品代码

}
