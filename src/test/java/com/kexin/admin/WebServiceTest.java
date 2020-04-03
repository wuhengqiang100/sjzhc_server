package com.kexin.admin;

import com.kexin.admin.webService.TestWebService;
import com.kexin.admin.webService.impl.TestWebServiceImpl;

//import javax.xml.ws.Endpoint;

public class WebServiceTest {
    public static void main(String[] args) {
//        TestWebService TestWebService=new TestWebServiceImpl();
        System.out.println("web service start");
        TestWebService implementor=new TestWebServiceImpl();
//        HelloWorld implementor=new HelloWorldImpl();
        String address="http://192.168.117.2/helloWorld";
//        Endpoint.publish(address, implementor); // jdk实现 暴露webservice接口
//        JaxWsServerFactoryBean factoryBean=new JaxWsServerFactoryBean();
//        factoryBean.setAddress(address); // 设置暴露地址
//        factoryBean.setServiceClass(HelloWorld.class); // 接口类
//        factoryBean.setServiceBean(implementor); // 设置实现类
//        factoryBean.create(); // 创建webservice接口
        System.out.println("web service started");

    }
}
