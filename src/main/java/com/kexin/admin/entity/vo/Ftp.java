package com.kexin.admin.entity.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description:ftp链接常量
 * @Author: 巫恒强
 * @Date: 2019/12/6 9:40
 */
@Component
@ConfigurationProperties(prefix = "ftpserver")
@Data
public class Ftp {
    private String ipAddr;//ip地址

    private Integer port;//端口号

    private String userName;//用户名

    private String pwd;//密码

    private String path;//aaa路径

    private String encoding;//编码

    private String localpath;//本地ftp目录

    private String remotepath;//远程ftp目录

}