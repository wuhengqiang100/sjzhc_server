package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
//import com.diboot.core.binding.annotation.BindEntity;
//import com.diboot.core.binding.annotation.BindField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


import java.util.Date;

/**
 * @Description:生产日志表实体列
 * @Author: 巫恒强
 * @Date: 2020/1/3 9:41
 */
@Data
@TableName(value = "LOG_PROD_ACTIONS")
public class ProduceLog {

    @TableId(type = IdType.INPUT)
    @TableField(value = "LOG_PROD_ID")
    private Integer logProdId; //生产日志id

    @TableField(value = "LOG_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date logDate;

    @TableField(value = "PRODUCT_ID")
//    @BindField(entity = Products.class, field="productName", condition="this.PRODUCT_ID=DIC_PRODUCTS.productId")
    private Integer productId;

    @TableField(value = "CART_NUMBER")
    private String cartNumber;

    @TableField(value = "OPERATION_ID")
    private Integer operationId;

    @TableField(value = "OPERATOR_ID")
    private Integer operatorId;

    @TableField(value = "OPERATOR_NAME")
    private String operatorName;

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

}
