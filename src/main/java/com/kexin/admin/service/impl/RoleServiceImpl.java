package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.GrantRoleFunctions;
import com.kexin.admin.entity.tables.Role;
import com.kexin.admin.mapper.RoleMapper;
import com.kexin.admin.service.RoleService;
import com.kexin.common.util.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色服务实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

//    @Override
//    public IPage<Role> selectUserPage(Page<Role> page, Integer useFlag) {
//        return baseMapper.selectPageRole(page,useFlag);
//    }

    @Override
    public IPage<Role> selecRolePage(Page<Role> page, Integer useFlag, String roleName) {
        return baseMapper.selectPageRole(page,useFlag,roleName);
    }

    @Override
    public IPage<Role> selectUserPageNotGroupById(Page<Role> page, Integer useFlag) {
        return baseMapper.selectUserPageNotGroupById(page,useFlag);
    }

    @Override
    public Boolean updateGrantRoleAndFunctions(int roleId, int functionId) {
        return baseMapper.updateGrantRoleAndFunctions(roleId,functionId);
    }

    @Override
    public Role getByMap(Role role) {
        return baseMapper.getByMap(role);
    }

    @Override
    public Role getByMapOr(Role role) {
        return baseMapper.getByMapOr(role);
    }


    @Override
    public Role saveNewRole(Role role) {
        return baseMapper.saveNewRole(role);
    }

    @Override
    public Integer updateRole(Role role) {
        return baseMapper.updateRole(role);
    }

    @Override
    public ResponseEntity deleteRoleAbout(int roleId) {
        try {
            baseMapper.deleteGrantRole(roleId);//删除关联表信息
            baseMapper.deleteById(roleId);//删除角色表信息
            return ResponseEntity.success("删除角色成功");
        } catch (Exception e) {
            return ResponseEntity.success("删除角色失败");
        }
    }

    @Override
    public ResponseEntity forbiddenRole(int roleId) {
        Role role=baseMapper.selectById(roleId);
        if (!role.getUseFlag()){
            role.setUseFlag(true);
            Integer i=baseMapper.updateRole(role);
            if (i!=0){
                return ResponseEntity.success("启用角色成功");
            }else{
                return ResponseEntity.success("启用角色失败");
            }
        }else{
            role.setUseFlag(false);
            Integer i=baseMapper.updateRole(role);
            if (i!=0){
                return ResponseEntity.success("禁止角色成功");
            }else{
                return ResponseEntity.success("禁止角色失败");
            }
        }

    }

    @Override
    public Integer deleteGrantRole(int roleId) {
        return baseMapper.deleteGrantRole(roleId);
    }

    @Override
    public Boolean updateGrantRoleAndOperator(int operatorId, int[] roleIds) {
        //先删除再更新
        Integer i=baseMapper.deleteGrantOperatorAndRole(operatorId);
        if (i>=0){
            try {
                for (int groupId:roleIds) {
                    baseMapper.insertGrantOperatorAndRole(operatorId,groupId);//新增关系数据
                }
                return  true;
            } catch (Exception e) {
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public List<Role> getOwnRoleByOperatorId(int operatorId) {
        return baseMapper.getOwnRoleByOperatorId(operatorId);
    }

    @Override
    public Integer deleteGrantOperatorAndRole(int operatorId) {
        return baseMapper.deleteGrantOperatorAndRole(operatorId);
    }

    @Override
    public List<GrantRoleFunctions> getFunctionIdsByRoleId(int roleId) {
        return baseMapper.getFunctionIdsByRoleId(roleId);
    }
}
