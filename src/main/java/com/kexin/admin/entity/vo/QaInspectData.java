package com.kexin.admin.entity.vo;

import java.util.List;

/**
 * @Description:审核的穿梭框返回总实体类
 * @Author: 巫恒强
 * @Date: 2019/12/3 16:32
 */
public class QaInspectData {
    private List<QaInspectTransfer> qaInspectTransfer;//所有的审核信息
    private String[] value;//已审核的的信息

    public void setQaInspectTransfer(List<QaInspectTransfer> qaInspectTransfer) {
        this.qaInspectTransfer = qaInspectTransfer;
    }

    public List<QaInspectTransfer> getQaInspectTransfer() {
        return qaInspectTransfer;
    }

    public String[] getValue() {
        return value;
    }

    public void setValue(String[] value) {
        this.value = value;
    }
}
