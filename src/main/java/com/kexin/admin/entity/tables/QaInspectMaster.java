package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


/**
 * 质量表头信息表
 */
//@KeySequence(value = "SQ_DIC_MACHINES", clazz = Integer.class)
@TableName(value = "QA_INSPECT_MASTER")
@Data
public class QaInspectMaster {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    @TableField(value = "INSPECTM_ID")
    private Integer inspectmId;//主键

    @TableField(value = "INFO_NUMBER")
    private Integer infoNumber;//机检检测张数

    @TableField(value = "MACHINE_WASTER_NUMBER")
    private Integer machineWasterNumber;//机检报错条数


    @TableField(value = "NOCHECK_NUMBER")
    private Integer noCheckNum;//未检条数

    @TableField(value = "JUDGE_WASTER_NUMBER")
    private Integer judgeWasterNumber;//判废条数

    @TableField(value = "ALLOW_JUDGE")
    private Integer allowJudge;//状态编号

    @TableField(value = "ITEM_FLAG")
    private Integer itemFlag;//进度标志

    @TableField(value = "NOTE")
    private String note;//备注

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "LAST_UPDATE_TIME")
    private Date lastUpdateTime;//更新的之间

   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "CHECK_DATE")
    private Date checkDate;//审核时间

    @TableField(value = "AUTO_CHECK_FLAG")
    private Integer autoCheckFlag;//自动审核标志: 0 未设定 1 自动审核 2 人工审核

    @TableField(exist = false)
    private Boolean disabled;//回退审核禁止选用标志,默认都可以回退


    @TableField(exist = false)
    private WipProdLogs wipProdLogs;//生产日志信息

    @TableField(exist = false)
    private WipJobs wipJobs;//基础生产信息

     @TableField(exist = false)
    private Products product;//产品种类

     @TableField(exist = false)
    private Operation operation;//产品工序

     @TableField(exist = false)
    private Operator operator;//操作员信息

    @TableField(exist = false)
    private Machine machine;//机器信息

    @TableField(exist = false)
    private DicWorkUnits dicWorkUnits;//操作台信息

}
