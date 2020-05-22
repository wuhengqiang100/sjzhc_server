package com.kexin.admin.entity.vo.webQuery;

import lombok.Data;

/**
 * 返回前台的select实体,with  valueData
 */
@Data
public class SelectOptionValue {

    private Integer value;
    private String label;
    private Boolean disabled =false;

    private Integer valueData;
}
