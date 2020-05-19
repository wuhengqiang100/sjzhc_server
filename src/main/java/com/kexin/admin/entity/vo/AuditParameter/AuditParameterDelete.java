package com.kexin.admin.entity.vo.AuditParameter;

import lombok.Data;

@Data
public class AuditParameterDelete {

    private String operationName;
    private String productName;
    private String machineName;
}
