package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 大屏详细配置实体类
 */
@TableName("DIC_DISPLAY_PLATFORM_INFOS")
@KeySequence(value = "SQ_DIC_DISPLAY_PLATFORM_INFOS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisplayPlatformInfo {


    @TableId(type = IdType.INPUT)
    @TableField(value = "INFO_ID")
    private Integer infoId;//大屏详细配置id

    @TableField(value = "DISPLAY_PLATFORM_ID")
    private Integer displayPlatformId; // 大屏id ,一对多关系,一个大屏有多个设备配置

    @TableField(exist = false)
    private DisplayPlatform displayPlatform;

    @TableField(value = "MACHINE_ID")
    private Integer machineId; //设备id

    @TableField(exist = false)
    private Machine machine;

    @TableField(value = "ORDER_NUM")
    private Integer orderNum; //显示顺序




    /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "USE_FLAG")
    private Boolean useFlag;//启用状态
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



}
