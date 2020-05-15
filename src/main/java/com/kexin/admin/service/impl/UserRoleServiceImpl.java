package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.SysUserRoles;
import com.kexin.admin.mapper.UserRoleMapper;
import com.kexin.admin.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户角色管理配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, SysUserRoles> implements UserRoleService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)



    @Override
    public Integer sysUserRolesCountByCode(String sysUserRolesCode) {
        QueryWrapper<SysUserRoles> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_CODE",sysUserRolesCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer sysUserRolesCountByName(String sysUserRolesName) {
        QueryWrapper<SysUserRoles> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_NAME",sysUserRolesName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysUserRoles(SysUserRoles sysUserRoles) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        baseMapper.insert(sysUserRoles);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSysUserRoles(SysUserRoles sysUserRoles) {
//        dropUserRolesByUserId(user.getLoginId());
        baseMapper.updateById(sysUserRoles);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSysUserRoles(SysUserRoles sysUserRoles) {
        baseMapper.deleteById(sysUserRoles.getUserRoleId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockSysUserRoles(SysUserRoles sysUserRoles) {
     /*   if (sysUserRoles.getUseFlag()){
            sysUserRoles.setUseFlag(false);
            sysUserRoles.setEndDate(new Date());
        }else{
            sysUserRoles.setUseFlag(true);
            sysUserRoles.setEndDate(null);
        }*/
        baseMapper.updateById(sysUserRoles);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleleByLoginId(Integer loginId) {
        baseMapper.deleleByLoginId(loginId);
    }
}
