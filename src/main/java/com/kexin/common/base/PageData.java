package com.kexin.common.base;

import java.util.List;

public class PageData<T> {

    private Integer code = 0;
    private Long total;
    private List<T> results;
    private String msg = "";

/*    private Integer code = 20000;


    private Long count;
    private List<T> data;
    private String msg = "";*/

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
