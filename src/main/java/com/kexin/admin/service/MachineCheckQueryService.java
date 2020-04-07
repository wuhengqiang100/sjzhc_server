package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.MachineCheckQuery;
import com.kexin.common.util.ResponseEty;

public interface MachineCheckQueryService extends IService<MachineCheckQuery> {


    /**
     * 综合查询的条件获取select option
     * @return
     */
    ResponseEty getQuerySelectOption();
}
