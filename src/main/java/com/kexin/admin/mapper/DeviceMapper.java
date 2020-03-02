package com.kexin.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kexin.admin.entity.tables.Device;
import com.kexin.common.util.ResponseEty;
import org.apache.ibatis.annotations.Param;

/**
 * 设备mapper接口层
 */
public interface DeviceMapper extends BaseMapper<Device> {


    /**
     * 更新useFlag,禁用状态
     * @param device
     * @return
     */
    Integer updateUseFlag(@Param("device") Device device);
}
