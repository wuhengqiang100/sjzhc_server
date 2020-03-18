package com.kexin.admin.entity.vo;

import java.util.List;

/**
 * @Description:审核的穿梭框返回总实体类,包括审核的所有数据
 * @Author: 巫恒强
 * @Date: 2019/12/3 16:32
 */
public class QaInspectDatas {
    private List<QaInspectTransfers> qaInspectTransfers;//所有的审核信息
    private Integer[] value;//已审核的的信息

    public List<QaInspectTransfers> getQaInspectTransfers() {
        return qaInspectTransfers;
    }

    public void setQaInspectTransfers(List<QaInspectTransfers> qaInspectTransfers) {
        this.qaInspectTransfers = qaInspectTransfers;
    }

    public Integer[] getValue() {
        return value;
    }

    public void setValue(Integer[] value) {
        this.value = value;
    }
}
