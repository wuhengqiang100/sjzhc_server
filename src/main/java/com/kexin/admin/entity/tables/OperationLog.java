package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Description:操作日志表对应实体
 * @Author: 巫恒强
 * @Date: 2020/1/3 9:41
 */
//QA_LOG_OPERATION_NOTES
@KeySequence(value = "QA_LOG_OPERATION_NOTES")
@TableName(value = "LOG_OPERATION_NOTES")
@Data
public class OperationLog {

    @TableId(type = IdType.INPUT)
    @TableField(value = "LOG_OPERATION_NOTE_ID")
    private Integer logOperationNoteId;

    @TableField(value = "JOB_ID")
    private Integer jobId;

    @TableField(value = "OPERATOR_ID")
    private Integer operatorId;

    @TableField(value = "OPERATOR_NAME")
    private String operatorName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "START_DATE")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "END_DATE")
    private Date endDate;

    @TableField(value = "ITEM_FLAG")
    private Integer itemFlag;

    @TableField(value = "NOTE")
    private String note;

    @TableField(value = "OPERATION_NOTE_TYPE")
    private String operationNoteType;

}
