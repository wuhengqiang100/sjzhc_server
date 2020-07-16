package com.kexin.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kexin.admin.entity.tables.SystemSet;
import org.apache.ibatis.annotations.Param;

import java.io.File;
import java.sql.Blob;

/**
 * 系统设置mapper接口层
 */
public interface SystemSetMapper extends BaseMapper<SystemSet> {

    int uploadLoginBg(@Param("factoryId") Integer factoryId,@Param("loginBg") byte[] loginBg);

}
