package com.kexin.admin.webService.impl;

import com.kexin.admin.webService.TestWebService;

//import javax.jws.WebService;

//@WebService
public class TestWebServiceImpl implements TestWebService {
    @Override
    public String getTestDemo(String str) {
        return "Hello"+str;
    }
}
