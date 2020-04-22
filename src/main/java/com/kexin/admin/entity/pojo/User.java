package com.kexin.admin.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 用于测试redis
 */
@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {

    private String name;
    private Integer age;
}
