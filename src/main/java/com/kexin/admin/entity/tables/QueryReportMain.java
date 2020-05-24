package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Description:核查综合查询的主视图查询
 * @Author: 巫恒强
 * @Date: 2020/1/3 9:41
 */

@TableName(value = "qa_report_main_data")
@Data
public class QueryReportMain {

    @TableId(type = IdType.INPUT)
    @TableField(value = "JOB_ID")
    private Integer jobId; //基础生产信息主键id

    @TableField(value = "CART_NUMBER")
    private String cartNumber;//生产车号（大万编号）

    @TableField(value = "PRODUCT_ID")
    private Integer productId;//产品id

    @TableField(value = "PRODUCT_NAME")
    private String productName;//产品名称

    @TableField(value = "prod_quantity")
    private Integer prodQuantity;//生产数量

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "START_DATE")
    private Date startDate;//日志开始时间

    @TableField(value = "finished_flag")
    private Integer finishedFlag;// 完成标志 0,1生产中 2:已生成数据 3:已打印报表 4:ocr稽核中 5:ocr稽核完成

    @TableField(value = "head")
    private String head; //冠号

    @TableField(value = "ymhk_check_flag")
    private Integer ymhkCheckFlag;//印码号控核查标志 -2:是没有 -1:数据未处理完成 0:未生成数据 1已生成数据 2:已打印


    @TableField(value = "ympm_check_flag")
    private Integer ympmCheckFlag;//印码票面核查标志 -2:是没有 -1:数据未处理完成 0:未生成数据 1已生成数据 2:已打印


    @TableField(value = "ymtb_check_flag")
    private Integer ymtbCheckFlag;//印码涂布核查标志 -2:是没有 -1:数据未处理完成 0:未生成数据 1已生成数据 2:已打印


    @TableField(value = "sy_check_flag")
    private Integer syCheckFlag;//丝印核查标志 -2:是没有 -1:数据未处理完成 0:未生成数据 1已生成数据 2:已打印


    @TableField(value = "WJ")
    private Integer wj;//未检数


    @TableField(value = "HKWJ")
    private Integer hkwj;//号控未检


    @TableField(value = "PMWJ")
    private Integer pmwj;//票面未检


    @TableField(value = "TBZZWJ")
    private Integer tbzzwj;//褶子


    @TableField(value = "TBWJ")
    private Integer tbwj;//涂布未检


    @TableField(value = "总剔废数")
    private Integer ztfs;//总剔废数


    @TableField(value = "总拆包数")
    private Integer zcbs;//总拆包数





}
