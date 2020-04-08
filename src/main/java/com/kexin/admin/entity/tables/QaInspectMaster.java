package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


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


    @TableField(value = "MACHINE_WASTER_NUMBER")
    private Integer machineWasterNumber;//整万错误数量

    @TableField(value = "INFO_NUMBER")
    private Integer infoNumber;//整万信息数量
    @TableField(value = "NO_CHECK_NUM")
    private Integer noCheckNum;//未检信息数量

    @TableField(value = "ALLOW_JUDGE")
    private Integer allowJudge;//状态编号

       @TableField(value = "ITEM_FLAG")
    private Integer itemFlag;//进度标志


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
