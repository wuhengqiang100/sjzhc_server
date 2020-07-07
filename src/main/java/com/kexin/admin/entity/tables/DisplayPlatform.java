package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 大屏配置实体类
 */
@TableName("DIC_DISPLAY_PLATFORMS")
@KeySequence(value = "SQ_DIC_DISPLAY_PLATFORMS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisplayPlatform {

    @TableId(type = IdType.INPUT)
    @TableField(value = "DISPLAY_PLATFORM_ID")
    private Integer displayPlatformId;//大屏配置id

    @TableField(value = "DISPLAY_PLATFORM_CODE")
    private String displayPlatformCode; //大屏配置代码

    @TableField(value = "DISPLAY_PLATFORM_NAME")
    private String displayPlatformName; //大屏配置名称

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
