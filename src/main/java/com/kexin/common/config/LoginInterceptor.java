package com.kexin.common.config;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class LoginInterceptor  implements HandlerInterceptor {

    @Override //进入Controller之前执行该方法
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登录拦截的业务逻辑
//        System.out.println("-------登录请求拦截器--------------");
//        System.out.println(request.getRequestURI().toString());
        String token=request.getHeader("token");


        if (StringUtils.isEmpty(token)){
            System.out.println("用户没有登录");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 50008);
            jsonObject.put("message", "您没有权限,不能请求数据222");
            PrintWriter out = null;
            HttpServletResponse res = (HttpServletResponse) response;
            try {
                res.setCharacterEncoding("UTF-8");
                res.setContentType("application/json");
                out = response.getWriter();
                out.println(jsonObject);
            } catch (Exception e) {
            } finally {
                if (null != out) {
                    out.flush();
                    out.close();
                }
            }
            return false;
        }else{
            return true;
        }


    }

}
