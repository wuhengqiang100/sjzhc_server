package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 设备模型信息主表
 */
@TableName("DIC_MACHINE_MODELS")
@KeySequence(value = "SQ_DIC_MACHINE_MODELS")
@Data
public class MachineModel {
    
    @TableId(type = IdType.INPUT)
    @TableField(value = "MACHINE_MODEL_ID")
    private Integer machineModelId;//设备模型主键id

    @TableField(value = "OPERATION_ID")
    private Integer operationId;//外键：工序ID

    @TableField(value = "MACHINE_ID")
    private Integer machineId;//外键:机器id



    @TableField(value = "PRODUCT_ID")
    private Integer productId;//外键：产品ID


    @TableField(value = "MACHINE_MODEL_CODE")
    private String machineModelCode;//设备模型编号

    @TableField(value = "MACHINE_MODEL_NAME")
    private String machineModelName;//设备模型名称

    @TableField(value = "MACHINE_MODEL_NUM")
    private Integer machineModelNum;//模型版本号，每次上载后加1

    @TableField(value = "MACHINE_MODEL_PATH")
    private String machineModelPath;//模型压缩包路径



    /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "USE_FLAG")
    protected Boolean useFlag;
    /**
     * 启用时间,写入时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "START_DATE")
    protected Date startDate;
    /**
     * 禁用时间,结束时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "END_DATE")
    protected Date endDate;
    /**
     * 说明
     */
    @TableField(value = "NOTE")
    protected String note;

    @TableField(exist = false)
    private Machine machine;//机器实体
     @TableField(exist = false)
    private Operation operation;//工序实体
    @TableField(exist = false)
    private Products product;//产品实体

    @TableField(exist = false)
    private String fileName;//临时拼接的模板名
    @TableField(exist = false)
    private String machineModelNumString;//临时num变量"0001"

}
