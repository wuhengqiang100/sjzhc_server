package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.io.File;
import java.sql.Blob;
import java.util.Date;

/**
 * @Description:核查综合查询的缺陷查询
 * @Author: 巫恒强
 * @Date: 2020/1/3 9:41
 */

@TableName(value = "qa_report_qa_data")
@Data
public class QueryReportQa {

    @TableId(type = IdType.INPUT)
    @TableField(value = "JOB_ID")
    private Integer jobId; //基础生产信息主键id


    @TableField(value = "PRODUCT_ID")
    private Integer productId;//产品id

    @TableField(value = "PRODUCT_NAME")
    private String productName;//产品名称

    @TableField(value = "OPERATION_ID")
    private Integer operationId;//工序id

    @TableField(value = "OPERATION_NAME")
    private String operationName;//工序名称


    @TableField(value = "qa_id")
    private Integer qaId;//质量序号

    @TableField(value = "sheet_num")
    private String sheetNum;//大张号

    @TableField(value = "code_num")
    private String codeNum;//印码号

    @TableField(value = "convert_num")
    private Integer convertNum;//开位

    @TableField(value = "route_num")
    private Integer routeNum;//路数

    @TableField(value = "thousand_index")
    private Integer thousandIndex;//第几千()1位数

    @TableField(value = "hundred_index")
    private Integer hundredIndex;//百位()


    @TableField(value = "item_flag")
    private Integer itemFlag;//判费类型  判废类型 2:判废 3:审核费


    @TableField(value = "err_flag")
    private Integer errFlag;// 未检原因 1:普通未检 2:机检大张废 9:判费大张废

    @TableField(value = "inspects_id")
    private Integer inspectsId;//

    @TableField(value = "sheet_waster_flag")
    private Integer sheetWasterFlag;//判废大张废标志
//    @JsonIgnore
    @TableField(value = "image_blob",jdbcType = JdbcType.BLOB)
    private byte[] imageBlob;//图像

    @TableField(exist = false)
    private String filePath;

   @TableField(value = "error_note")
    private String errorNote;//错误原因




}
