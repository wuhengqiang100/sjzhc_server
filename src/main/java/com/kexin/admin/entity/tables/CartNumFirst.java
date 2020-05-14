package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 车号首字母
 */
@TableName("DIC_CARTNUM_FIRST")
@KeySequence(value = "SQ_DIC_CARTNUM_FIRST")
@Data
public class CartNumFirst {
    @TableId(type = IdType.INPUT)
    @TableField(value = "NUM_ID")
    private Integer numId;//车号首字母主键id


    @TableField(value = "NUM_CODE")
    private String numCode;//车号首字母编号

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
