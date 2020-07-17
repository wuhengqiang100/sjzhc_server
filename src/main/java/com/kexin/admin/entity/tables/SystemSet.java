package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;

/**
 * 系统信息
 */
@TableName("SET_SYSTEM")
@KeySequence(value = "SQ_SET_SYSTEM", clazz = Integer.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemSet {

    @TableId(type = IdType.INPUT)
    @TableField(value = "FACTORY_ID")
    private Integer factoryId;//系统主键id

    @TableField(value = "FACTORY_CODE")
    private String factoryCode;//系统编号

    @TableField(value = "FACTORY_NAME")
    private String factoryName;//系统名称

    @TableField(value = "FTP_IP")
    private String ftpIp;//ip地址

    @TableField(value = "FTP_NAME")
    private String ftpName;//用户名

    @TableField(value = "FTP_PASS")
    private String ftpPass;//密码

    /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "MACHINE_CODE_MES")
    private Boolean machineCodeMes;

    /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "MACHINE_WASTE_NO_JUDGE")
    private Boolean machineWasteNoJudge;

    /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "OPERATION_CODE_MES")
    private Boolean operationCodeMes;

    /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "OPERATOR_CODE_MES")
    private Boolean operatorCodeMes;

    /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "PRODUCT_CODE_MES")
    private Boolean productCodeMes;

        /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "PRODUCT_CARTNUM_FIRST_ID")
    private Boolean productCartNumFirstId;

        /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "PRODUCT_CARTNUM_FIRST_DATE")
    private Boolean productCartNumFirstDate;

        /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "PRODUCT_CARTNUM_FIRST_COUNT")
    private Boolean productCartNumFirstCount;

    /**
     * 大张废
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "PRODUCT_SHEET_WASTER_NUM")
    private Boolean productSheetWasterNum;

        /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "PRODUCT_QA_CODE_NAME")
    private Boolean productQaCodeName;

        /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "PRODUCT_LOCAL_PRODUCT_NAME")
    private Boolean productLocalProductName;



    /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "WORK_UNIT_CODE_MES")
    private Boolean workUnitCodeMes;

    /**
     * 登陆页面背景
     */
//    @TableField(value = "LOGIN_BG")
//    private String loginBg;


}
