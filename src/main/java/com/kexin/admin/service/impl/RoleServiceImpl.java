package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.GrantRoleFunctions;
import com.kexin.admin.entity.tables.Role;
import com.kexin.admin.entity.tables.Role;
import com.kexin.admin.mapper.LoginUserMapper;
import com.kexin.admin.mapper.RoleMapper;
import com.kexin.admin.service.RoleService;
import com.kexin.common.util.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 角色服务实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {



    @Override
    public Integer roleCountByName(String roleName) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("ROLE_NAME",roleName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(Role role) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        baseMapper.insert(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(Role role) {
//        dropUserRolesByUserId(user.getLoginId());
        baseMapper.updateById(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Role role) {
        baseMapper.deleteById(role.getRoleId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockRole(Role role) {
        role.setUseFlag(role.getUseFlag()?false:true);
        baseMapper.updateById(role);
    }

}
