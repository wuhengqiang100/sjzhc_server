package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.Machine;
import com.kexin.admin.entity.tables.SysMenus;
import com.kexin.admin.mapper.SysMenusMapper;
import com.kexin.admin.service.SysMenusService;
import com.kexin.common.util.ResponseEty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 菜单配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysMenusServiceImpl extends ServiceImpl<SysMenusMapper, SysMenus> implements SysMenusService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)


    @Override
    public ResponseEty getSysMenus() {


        return ResponseEty.success("success");
    }

    @Override
    public Integer sysMenusCountByCode(String sysMenusCode) {
        QueryWrapper<SysMenus> wrapper = new QueryWrapper<>();
        wrapper.eq("SYSMENUS_CODE",sysMenusCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer sysMenusCountByName(String sysMenusName) {
        QueryWrapper<SysMenus> wrapper = new QueryWrapper<>();
        wrapper.eq("SYSMENUS_NAME",sysMenusName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysMenus(SysMenus sysMenus) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        baseMapper.insert(sysMenus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSysMenus(SysMenus sysMenus) {
//        dropUserRolesByUserId(user.getLoginId());
        baseMapper.updateById(sysMenus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSysMenus(SysMenus sysMenus) {
        baseMapper.deleteById(sysMenus.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockSysMenus(SysMenus sysMenus) {
/*        if (sysMenus.getUseFlag()){
            sysMenus.setUseFlag(false);
            sysMenus.setEndDate(new Date());
        }else{
            sysMenus.setUseFlag(true);
            sysMenus.setEndDate(null);
        }*/
        baseMapper.updateById(sysMenus);
    }
}
