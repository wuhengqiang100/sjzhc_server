package com.kexin.admin.entity.vo;

/**
 * 编辑设备时,导出已选的分组实体类
 */
public class CheckOptionsGroup {

    private String label;//标签(分组名称)

    private String value;//值(分组id)

    private Boolean checked=false;//是否已选(是否属于这个分组,默认不属于)

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
