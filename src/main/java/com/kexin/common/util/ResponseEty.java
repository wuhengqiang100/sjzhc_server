package com.kexin.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 北京开窗安全线返回实体类
 */
public class ResponseEty extends HashMap<String, Object> {

    /**
     * 返回成功
     * code : 20000
     * @param message
     * @return
     */
    public static ResponseEty success(String message){
        ResponseEty response = new ResponseEty();
        response.setSuccess(20000);
        response.setMessage(message);
        return response;
    }

    /**
     * 失败返回
     * code : 0
     * @param message
     * @return
     */
    public static ResponseEty failure(String message){
        ResponseEty response = new ResponseEty();
        response.setSuccess(0);
        response.setMessage(message);
        return response;
    }

    public static ResponseEty reLogin(){
        ResponseEty response = new ResponseEty();
        response.setSuccess(50008);
        return response;
    }

    public ResponseEty setSuccess(Integer code) {
        if (code != null) put("code", code);
        return this;
    }

    public ResponseEty setMessage(String message) {
        if (message != null) put("message", message);
        return this;
    }
    public ResponseEty setData(Object data) {
        if (data != null) put("data", data);
        return this;
    }

    public ResponseEty setAny(String key, Object value) {
        if (key != null && value != null) put(key, value);
        return this;
    }
}
