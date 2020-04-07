package com.kexin.admin.entity.vo.webQuery;

import lombok.Data;

/**
 * 返回前台的select实体
 */
@Data
public class SelectOption {

    private Integer value;
    private String label;
    private Boolean disabled =false;

}
