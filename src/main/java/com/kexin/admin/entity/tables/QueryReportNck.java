package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Description:核查综合查询的未检消息查询
 * @Author: 巫恒强
 * @Date: 2020/1/3 9:41
 */

@TableName(value = "qa_report_nck_data")
@Data
public class QueryReportNck {

    @TableId(type = IdType.INPUT)
    @TableField(value = "JOB_ID")
    private Integer jobId; //基础生产信息主键id

    @TableField(value = "product_id")
    private Integer productId;//产品id

    @TableField(value = "product_name")
    private String productName;//产品名称

    @TableField(value = "nck_id")
    private Integer nckId; //未检id

    @TableField(value = "sheet_num")
    private String sheetNum;//大张号

    @TableField(value = "code_num")
    private String codeNum;//印码号

    @TableField(value = "operation_id")
    private Integer operationId;//工序id

    @TableField(value = "operation_name")
    private String operationName;//工序名称

    @TableField(value = "thousand_index")
    private Integer thousandIndex;//第几千()1位数

    @TableField(value = "hundred_index")
    private Integer hundredIndex;//百位()


    @TableField(value = "err_flag")
    private Integer errFlag;// 未检原因 1:普通未检 2:机检大张废 9:判费大张废





}
