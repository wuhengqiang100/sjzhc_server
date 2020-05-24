package com.kexin.admin.entity.vo.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 综合查询的主视图查询条件
 */
@Data
public class QueryReportMainSelect extends QueryDateParent {
    private String cartNumber;
    private Integer productId;
    private Integer finishedFlag;


}
