package com.kexin.admin.entity.vo.AuditParameter;

import lombok.Data;

@Data
public class ParameterByIds {
    private Integer operationId;
    private Integer productId;
    private Integer machineId;
}
