package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


/**
 * @Description:生产日志视图实查询类
 * @Author: 巫恒强
 * @Date: 2020/1/3 9:41
 */
@Data
@TableName(value = "view_log_prod_actions")
public class ViewProduceLog {

    @TableId(type = IdType.INPUT)
    @TableField(value = "LOG_PROD_ID")
    private Integer logProdId; //生产日志id

    @TableField(value = "LOG_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date logDate;


    @TableField(value = "CART_NUMBER")
    private String cartNumber;
    @TableField(value = "LOG_TYPE")
    private String logType;//日志类型

    @TableField(value = "START_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;

    @TableField(value = "END_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endDate;


    @TableField(value = "ACTION_COUNT")
    private Integer actionCount;

    @TableField(value = "NOTE")
    private String note;

    @TableField(value = "ITEM_FLAG")
    private Integer itemFlag;


    @TableField(value = "PRODUCT_ID")
    private Integer productId;

    @TableField(value = "product_name")
    private String productName;

    @TableField(value = "OPERATION_ID")
    private Integer operationId;


    @TableField(value = "operation_name")
    private String operationName;


    @TableField(value = "OPERATOR_ID")
    private Integer operatorId;

    @TableField(value = "OPERATOR_NAME")
    private String operatorName;





}
