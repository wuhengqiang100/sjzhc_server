package com.kexin.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.FacilityType;
import com.kexin.common.util.ResponseEntity;

/**
 * 用户身份类别,service接口层
 */
public interface FacilityTypeService extends IService<FacilityType> {

    IPage<FacilityType> selecFacilityTypePage(Page<FacilityType> page, Integer useFlag, String facilityTypeName);

    ResponseEntity forbiddenFacilityType(int facilityTypeId);
}
