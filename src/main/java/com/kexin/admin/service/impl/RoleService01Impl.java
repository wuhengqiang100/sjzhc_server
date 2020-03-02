package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.Role01;
import com.kexin.admin.mapper.RoleMapper01;
import com.kexin.admin.service.RoleService01;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class RoleService01Impl extends ServiceImpl<RoleMapper01, Role01> implements RoleService01 {

    @Override
    public long getRoleNameCount(String name) {
        QueryWrapper<Role01> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        return baseMapper.selectCount(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role01 saveRole(Role01 role01) {
        baseMapper.insert(role01);
        if(role01.getMenuSet() != null && role01.getMenuSet().size() > 0) {
            baseMapper.saveRoleMenus(role01.getId(), role01.getMenuSet());
        }
        return role01;
    }

    @Override
    public Role01 getRoleById(String id) {
        return baseMapper.selectRoleById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(Role01 role01) {
        baseMapper.updateById(role01);
        baseMapper.dropRoleMenus(role01.getId());
        if(role01.getMenuSet() != null && role01.getMenuSet().size() > 0) {
            baseMapper.saveRoleMenus(role01.getId(), role01.getMenuSet());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Role01 role01) {
        role01.setDelFlag(true);
        baseMapper.updateById(role01);
        baseMapper.dropRoleMenus(role01.getId());
        baseMapper.dropRoleUsers(role01.getId());
    }

    @Override
    public List<Role01> selectAll() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("del_flag",false);
        return baseMapper.selectList(wrapper);
    }
}
