package com.kexin.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kexin.admin.entity.tables.MachineModel;
import org.apache.ibatis.annotations.Param;

/**
 * 设备模板mapper接口层
 */
public interface MachineModelMapper extends BaseMapper<MachineModel> {
    /**
     * 根据模板的工序+设备+品种判断此条添加的数据是不是唯一的
     * @param machineModel
     * @return
     */
    Integer machineModelCountByOperationMachineProduct(@Param("machineModel") MachineModel machineModel);
}
