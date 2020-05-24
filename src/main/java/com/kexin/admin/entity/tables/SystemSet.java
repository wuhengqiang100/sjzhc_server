package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Blob;

/**
 * 系统信息
 */
@TableName("SET_SYSTEM")
@Data
public class SystemSet {
    
    @TableId(type = IdType.INPUT)
    @TableField(value = "SYSTEM_ID")
    private Integer systemId;//系统主键id

    @TableField(value = "SYSTEM_CODE")
    private String systemCode;//系统编号

    @TableField(value = "SYSTEM_NAME")
    private String systemName;//系统名称

    @TableField(value = "FTP_IP")
    private String ipAddr;//ip地址
    @TableField(value = "FTP_PORT")
    private Integer port;//端口号
    @TableField(value = "FTP_USER_NAME")
    private String userName;//用户名
    @TableField(value = "FTP_PASSWORD")
    private String pwd;//密码
    @TableField(value = "FTP_PATH")
    private String path;//aaa路径
    @TableField(value = "FTP_ENCODING")
    private String encoding;//编码
    @TableField(value = "FTP_LOCALPATH")
    private String localpath;//本地ftp目录
    @TableField(value = "FTP_REMOTEPATH")
    private String remotepath;//远程ftp目录

    @TableField(value = "FACTORY_LOGO")
    private Blob factoryLogo;//工厂logo


    @TableField(value = "SYSTEM_LOGO")
    private Blob systemLogo;//工厂logo

    @TableField(value = "SYSTEM_BG")
    private Blob systemBg;//工厂logo


    /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "MACHINE_CODE_MES")
    private Boolean machineCodeMes;
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
    @TableField(value = "WORK_UNIT_CODE_MES")
    private Boolean workUnitCodeMes;



}
