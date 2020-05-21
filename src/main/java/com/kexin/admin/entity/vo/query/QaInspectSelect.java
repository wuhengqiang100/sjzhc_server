package com.kexin.admin.entity.vo.query;

import lombok.Data;

/**
 *
 */
@Data
public class QaInspectSelect extends QueryDateParent {
    private String cartNumber;
    private String productId;
    private String operationId;
    private String machineId;
    private String workUnitId;

}
