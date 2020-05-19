package com.kexin.admin.entity.vo.query;


import lombok.Data;

@Data
public class QueryProduceLog extends QueryDateParent {
    private Integer  productId;
    private Integer operationId;
    private Integer operatorId;
}
