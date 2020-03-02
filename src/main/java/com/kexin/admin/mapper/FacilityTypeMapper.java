package com.kexin.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.FacilityType;

/**
 * 用户身份类别mapper接口层
 */
public interface FacilityTypeMapper extends BaseMapper<FacilityType> {
    IPage<FacilityType> selectFacilityTypePage(Page<FacilityType> page, Integer useFlag, String facilityTypeName);
}
