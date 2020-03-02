package com.kexin.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.FacilityGroup;
import com.kexin.admin.entity.vo.CheckOptionsType;

import java.util.List;

/**
 * 用户身份类别mapper接口层
 */
public interface FacilityGroupMapper extends BaseMapper<FacilityGroup> {
    IPage<FacilityGroup> selectFacilityGroupPage(Page<FacilityGroup> page, Integer useFlag, String groupName);

    Integer deleteGrantMachineAndGroup(int machineId);

    Integer insertGrantMachineAndGroup(int machineId,int groupId);

    List<FacilityGroup> getOwnFacilityByMachineId(int machineId);

    /**
     * 控制台获得所有的分组信息
     * @return
     */
    List<CheckOptionsType> getFacilityGroupMontor();
}
