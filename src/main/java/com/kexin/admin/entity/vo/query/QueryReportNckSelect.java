package com.kexin.admin.entity.vo.query;

import lombok.Data;

/**
 * 综合查询的主视图查询条件
 */
@Data
public class QueryReportNckSelect extends QueryDateParent {


    private Integer jobId;//生产序号

    private Integer productId;

    private Integer operationId;

    private String sheetNum;//大张号
    private String codeNum;//印码号

}
