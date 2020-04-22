package com.kexin.admin.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 用于测试webservice
 */
@Component
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class UserWeb implements Serializable {
    private static final long serialVersionUID = -3628469724795296287L;
    private String userId;
    private String userName;
    private String email;

}
