package com.kexin.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.FacilityGroup;
import com.kexin.admin.entity.vo.CheckOptionsType;
import com.kexin.common.util.ResponseEntity;

import java.util.List;

/**
 * 用户身份类别,service接口层
 */
public interface FacilityGroupService extends IService<FacilityGroup> {

    IPage<FacilityGroup> selecFacilityGroupPage(Page<FacilityGroup> page, Integer useFlag, String groupName);

    ResponseEntity forbiddenFacilityGroup(int facilityGroupId);

    /**
     * 更新设备与分组之间的关系表
     * @param machineId
     * @param groupIds
     * @return
     */
    Boolean updateGrantGroupAndMachines(int machineId,int[] groupIds);

    List<FacilityGroup> getOwnFacilityByMachineId(int machineId);

    /**
     * 删除设备与分组之间的关联表信息
     * @param machineId
     * @return
     */
    Integer deleteGrantMachineAndGroup(int machineId);

    /**
     * 控制台获得所有的分组信息
     * @return
     */
    List<CheckOptionsType> getFacilityGroupMontor();
}
