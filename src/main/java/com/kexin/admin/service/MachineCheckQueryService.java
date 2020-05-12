package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.MachineCheckQuery;
import com.kexin.admin.entity.vo.webQuery.SelectOption;
import com.kexin.common.util.ResponseEty;

import java.util.List;

public interface MachineCheckQueryService extends IService<MachineCheckQuery> {


    /**
     * 综合查询的条件获取select option
     * @return
     */
    ResponseEty getQuerySelectOption();

    /**
     * 获取查询条件的用户select option
     * @return
     */
    List<SelectOption> getOperatorSelectOption();
}
