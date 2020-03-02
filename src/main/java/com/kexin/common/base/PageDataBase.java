package com.kexin.common.base;

import java.util.List;

public class PageDataBase<T> {


    private Integer code = 20000;

    private Data data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
