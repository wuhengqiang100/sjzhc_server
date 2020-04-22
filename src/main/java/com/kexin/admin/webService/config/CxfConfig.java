package com.kexin.admin.webService.config;

import com.kexin.admin.webService.UserService;
import com.kexin.admin.webService.impl.UserServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * @Description:
 * @Author: 巫恒强
 * @Date: 2019/12/17 15:37
 */
//@Configuration
public class CxfConfig {

    /**
     * 此方法作用是改变项目中服务名的前缀名，此处127.0.0.1或者localhost不能访问时，请使用ipconfig查看本机ip来访问
     * 此方法被注释后:wsdl访问地址为http://127.0.0.1:8088/services/user?wsdl
     * 去掉注释后：wsdl访问地址为：http://127.0.0.1:8088/kexin/webservice/user?wsdl

     * @return
     */
//    @SuppressWarnings("all")
//    @Bean
//    public ServletRegistrationBean dispatcherServlet() {
//        return new ServletRegistrationBean(new CXFServlet(), "/kexin/webservice/*");
//    }
//
//    @Bean(name = Bus.DEFAULT_BUS_ID)
//    public SpringBus springBus()
//    {
//        return  new SpringBus();
//    }
//
//    @Bean
//    public UserService userService()
//    {
//        return  new UserServiceImpl();
//    }
//
//    @Bean
//    public Endpoint endpoint() {
//        EndpointImpl endpoint=new EndpointImpl(springBus(), userService());//绑定要发布的服务
////        endpoint.publish("/user"); //显示要发布的名称
//        return endpoint;
//    }
}