package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.Identity;
import com.kexin.admin.mapper.IdentityMapper;
import com.kexin.admin.service.IdentityService;
import com.kexin.common.util.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户身份类别service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class IdentityServiceImpl extends ServiceImpl<IdentityMapper, Identity> implements IdentityService {
    @Override
    public IPage<Identity> selecIdentityPage(Page<Identity> page, Integer useFlag, String identityName) {
        return baseMapper.selectIdentityPage(page,useFlag,identityName);
    }

    @Override
    public ResponseEntity forbiddenIdentity(int identityId) {
        Identity identity=baseMapper.selectById(identityId);
        if (!identity.getUseFlag()){
            identity.setUseFlag(true);
            Integer i=baseMapper.updateById(identity);
            if (i!=0){
                return ResponseEntity.success("启用类别成功");
            }else{
                return ResponseEntity.success("启用类别失败");
            }
        }else{
            identity.setUseFlag(false);
            Integer i=baseMapper.updateById(identity);
            if (i!=0){
                return ResponseEntity.success("禁止类别成功");
            }else{
                return ResponseEntity.success("禁止类别失败");
            }
        }
    }
}
