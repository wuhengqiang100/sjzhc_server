package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @Description:网站系统操作日志对应实体
 * @Author: 巫恒强
 * @Date: 2020/1/3 9:41
 */
@Data
@TableName(value = "LOG_SYS_ACTIONS")
@KeySequence(value = "SQ_LOG_SYS_ACTIONS")
public class SystemLog {

    @TableId(type = IdType.INPUT)
    @TableField(value = "LOG_SYS_ID")
    private Integer logSysId;
    @TableField(value = "LOG_DATE")
    private Date logDate;

    @TableField(value = "OPERATOR_ID")
    private Integer operatorId;

    @TableField(value = "OPERATOR_NAME")
    private String operatorName;

    @TableField(value = "LOG_TYPE")
    private String logType;

    @TableField(value = "NOTE")
    private String note;


}
