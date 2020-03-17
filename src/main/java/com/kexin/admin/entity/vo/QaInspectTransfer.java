package com.kexin.admin.entity.vo;

/**
 * @Description:
 * @Author: 巫恒强
 * @Date: 2019/12/3 16:14
 */
public class QaInspectTransfer {

    private String value;//值

    private String title;//标题

    private Boolean disabled;//是否禁用

    private Boolean checked;//是否选中


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
