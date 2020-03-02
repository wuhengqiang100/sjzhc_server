package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.FacilityType;
import com.kexin.admin.mapper.FacilityTypeMapper;
import com.kexin.admin.service.FacilityTypeService;
import com.kexin.common.util.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户身份类别service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FacilityTypeServiceImpl extends ServiceImpl<FacilityTypeMapper, FacilityType> implements FacilityTypeService {
    @Override
    public IPage<FacilityType> selecFacilityTypePage(Page<FacilityType> page, Integer useFlag, String facilityTypeName) {
        return baseMapper.selectFacilityTypePage(page,useFlag,facilityTypeName);
    }

    @Override
    public ResponseEntity forbiddenFacilityType(int facilityTypeId) {
        FacilityType facilityType=baseMapper.selectById(facilityTypeId);
        if (!facilityType.getUseFlag()){
            facilityType.setUseFlag(true);
            Integer i=baseMapper.updateById(facilityType);
            if (i!=0){
                return ResponseEntity.success("启用类别成功");
            }else{
                return ResponseEntity.success("启用类别失败");
            }
        }else{
            facilityType.setUseFlag(false);
            Integer i=baseMapper.updateById(facilityType);
            if (i!=0){
                return ResponseEntity.success("禁止类别成功");
            }else{
                return ResponseEntity.success("禁止类别失败");
            }
        }
    }
}
