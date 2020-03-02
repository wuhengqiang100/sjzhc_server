package com.kexin.admin.entity.vo;

/**
 * 编辑设备时,导出已选的设备类型实体类
 */
public class CheckOptionsType {

    private String label;//标签(分组名称)

    private String value;//值(分组id)



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

}
