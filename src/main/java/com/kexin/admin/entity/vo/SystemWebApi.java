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
@ConfigurationProperties(prefix = "server")
@Data
public class SystemWebApi {

    private Integer port;//端口号

    private String address;//服务器ip地址


}