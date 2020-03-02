package com.kexin.common.base;

import java.util.List;

/**
 * @Description:
 * @Author: 巫恒强
 * @Date: 2020/2/25 11:29
 */
public class Data {

    private Long total;//返回的总数目

    private List<?> items;//返回的数据

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getTotal() {
        return total;
    }

    public List<?> getItems() {
        return items;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }
}
