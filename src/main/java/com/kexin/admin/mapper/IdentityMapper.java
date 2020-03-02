package com.kexin.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.Identity;

/**
 * 用户身份类别mapper接口层
 */
public interface IdentityMapper  extends BaseMapper<Identity> {
    IPage<Identity> selectIdentityPage(Page<Identity> page, Integer useFlag, String identityName);
}
