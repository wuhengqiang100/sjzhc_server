package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.SysMenusMeta;
import com.kexin.admin.mapper.SysMenusMetaMapper;
import com.kexin.admin.service.SysMenusMetaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 菜单描述配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysMenusMetaServiceImpl extends ServiceImpl<SysMenusMetaMapper, SysMenusMeta> implements SysMenusMetaService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)



    @Override
    public Integer sysMenusMetaCountByCode(String sysMenusMetaCode) {
        QueryWrapper<SysMenusMeta> wrapper = new QueryWrapper<>();
        wrapper.eq("SYSMENUSMETA_CODE",sysMenusMetaCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer sysMenusMetaCountByName(String sysMenusMetaName) {
        QueryWrapper<SysMenusMeta> wrapper = new QueryWrapper<>();
        wrapper.eq("SYSMENUSMETA_NAME",sysMenusMetaName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysMenusMeta(SysMenusMeta sysMenusMeta) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        baseMapper.insert(sysMenusMeta);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSysMenusMeta(SysMenusMeta sysMenusMeta) {
//        dropUserRolesByUserId(user.getLoginId());
        baseMapper.updateById(sysMenusMeta);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSysMenusMeta(SysMenusMeta sysMenusMeta) {
        baseMapper.deleteById(sysMenusMeta.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockSysMenusMeta(SysMenusMeta sysMenusMeta) {
/*        if (sysMenusMeta.getUseFlag()){
            sysMenusMeta.setUseFlag(false);
            sysMenusMeta.setEndDate(new Date());
        }else{
            sysMenusMeta.setUseFlag(true);
            sysMenusMeta.setEndDate(null);
        }*/
        baseMapper.updateById(sysMenusMeta);
    }
}
