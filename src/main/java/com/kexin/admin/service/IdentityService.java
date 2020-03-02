package com.kexin.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.Identity;
import com.kexin.common.util.ResponseEntity;

/**
 * 用户身份类别,service接口层
 */
public interface IdentityService  extends IService<Identity> {

    IPage<Identity> selecIdentityPage(Page<Identity> page, Integer useFlag, String identityName);

    ResponseEntity forbiddenIdentity(int identityId);
}
