package com.kexin.admin.entity.vo.query;

import lombok.Data;

/**
 *
 */
@Data
public class QaInspectSelect extends QueryDateParent {
    private String cartNumber;
    private String productName;
    private String operationName;
    private String machineName;
    private String workUnitName;

}
