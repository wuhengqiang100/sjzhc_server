package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.FacilityGroup;
import com.kexin.admin.entity.vo.CheckOptionsType;
import com.kexin.admin.mapper.FacilityGroupMapper;
import com.kexin.admin.service.FacilityGroupService;
import com.kexin.common.util.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户身份类别service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FacilityGroupServiceImpl extends ServiceImpl<FacilityGroupMapper, FacilityGroup> implements FacilityGroupService {
    @Override
    public IPage<FacilityGroup> selecFacilityGroupPage(Page<FacilityGroup> page, Integer useFlag, String groupName) {
        return baseMapper.selectFacilityGroupPage(page,useFlag,groupName);
    }

    @Override
    public ResponseEntity forbiddenFacilityGroup(int facilityGroupId) {
        FacilityGroup facilityGroup=baseMapper.selectById(facilityGroupId);
        if (!facilityGroup.getUseFlag()){
            facilityGroup.setUseFlag(true);
            Integer i=baseMapper.updateById(facilityGroup);
            if (i!=0){
                return ResponseEntity.success("启用分组成功");
            }else{
                return ResponseEntity.success("启用分组失败");
            }
        }else{
            facilityGroup.setUseFlag(false);
            Integer i=baseMapper.updateById(facilityGroup);
            if (i!=0){
                return ResponseEntity.success("禁止分组成功");
            }else{
                return ResponseEntity.success("禁止分组失败");
            }
        }
    }

    @Override
    public Boolean updateGrantGroupAndMachines(int machineId, int[] groupIds) {

        //先删除再更新
        Integer i=baseMapper.deleteGrantMachineAndGroup(machineId);
        if (i>=0){
            try {
                for (int groupId:groupIds) {
                    baseMapper.insertGrantMachineAndGroup(machineId,groupId);//新增关系数据
                }
                return  true;
            } catch (Exception e) {
                return false;
            }
        }else{
            return false;
        }

    }

    @Override
    public List<FacilityGroup> getOwnFacilityByMachineId(int machineId) {
        return baseMapper.getOwnFacilityByMachineId(machineId);
    }

    @Override
    public Integer deleteGrantMachineAndGroup(int machineId) {
        return baseMapper.deleteGrantMachineAndGroup(machineId);
    }

    @Override
    public List<CheckOptionsType> getFacilityGroupMontor() {
        return baseMapper.getFacilityGroupMontor();
    }
}
