package com.kexin.admin.entity.vo;

/**
 * @Description:审核数据的实体列
 * @Author: 巫恒强
 * @Date: 2019/12/3 16:14
 */
public class QaInspectTransfers {

    private Integer key;//值

    private String label;//标题

    private Boolean disabled;//是否禁用

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
