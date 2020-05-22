package com.kexin.admin.entity.vo.AuditParameter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kexin.admin.entity.tables.AuditParameterType;
import com.kexin.admin.entity.tables.Machine;
import com.kexin.admin.entity.tables.Operation;
import com.kexin.admin.entity.tables.Products;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditParameterSelect {



    private String operationName;//工序名称

    private Integer operationId;

    private String productName;//产品名称
    private Integer productId;


    private String machineName;//设备名称
    private Integer machineId;


    private List<AuditParameterDetail> details;//可变的审核参数类型,名称+值



    private Map valueMap;

    @TableField(exist = false)
    private String names;//参数的名称串

    @TableField(exist = false)
    private String values;//参数的值串

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
