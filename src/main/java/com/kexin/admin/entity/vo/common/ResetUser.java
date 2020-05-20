package com.kexin.admin.entity.vo.common;

import lombok.Data;

@Data
public class ResetUser {

    private Integer loginId;

    private String oldPassword;

    private String newPassword;

    private String confirmPassword;

}
