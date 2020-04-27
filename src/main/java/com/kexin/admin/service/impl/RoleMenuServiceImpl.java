package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.SysRoleMenus;
import com.kexin.admin.mapper.RoleMenuMapper;
import com.kexin.admin.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色菜单管理配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, SysRoleMenus> implements RoleMenuService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)



    @Override
    public Integer sysRoleMenusCountByCode(String sysRoleMenusCode) {
        QueryWrapper<SysRoleMenus> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_CODE",sysRoleMenusCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer sysRoleMenusCountByName(String sysRoleMenusName) {
        QueryWrapper<SysRoleMenus> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_NAME",sysRoleMenusName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysRoleMenus(SysRoleMenus sysRoleMenus) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        baseMapper.insert(sysRoleMenus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSysRoleMenus(SysRoleMenus sysRoleMenus) {
//        dropUserRolesByUserId(user.getLoginId());
        baseMapper.updateById(sysRoleMenus);
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public void deleteSysRoleMenus(SysRoleMenus sysRoleMenus) {
        baseMapper.deleteById(sysRoleMenus.getFunctionId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockSysRoleMenus(SysRoleMenus sysRoleMenus) {
     /*   if (sysRoleMenus.getUseFlag()){
            sysRoleMenus.setUseFlag(false);
            sysRoleMenus.setEndDate(new Date());
        }else{
            sysRoleMenus.setUseFlag(true);
            sysRoleMenus.setEndDate(null);
        }*/
        baseMapper.updateById(sysRoleMenus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleleByRoleId(Integer roleId) {
        baseMapper.deleleByRoleId(roleId);
    }
}
