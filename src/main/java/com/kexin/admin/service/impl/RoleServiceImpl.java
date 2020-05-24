package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.*;
import com.kexin.admin.entity.vo.QaInspectChange;
import com.kexin.admin.entity.vo.webQuery.RoleChange;
import com.kexin.admin.mapper.RoleMapper;
import com.kexin.admin.mapper.RoleMenuMapper;
import com.kexin.admin.mapper.SysFunctionMapper;
import com.kexin.admin.mapper.UserRoleMapper;
import com.kexin.admin.service.RoleService;
import com.kexin.admin.service.SystemLogService;
import com.kexin.common.util.ResponseEty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 角色服务实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


    @Resource
    UserRoleMapper userRoleMapper;

    @Resource
    RoleMenuMapper roleMenuMapper;

    @Resource
    SysFunctionMapper sysFunctionMapper;

    @Autowired
    SystemLogService systemLogService;//系统日志记录service


    @Override
    public String getRoleString(Integer[] roleIds) {
        String roleString = "";
        for (Integer roleId:roleIds) {
            Role role=baseMapper.selectById(roleId);
            if (role!=null){
                roleString=roleString+" "+role.getRoleName();
            }
        }
        return roleString;
    }

    /**
     * 获取所有的角色option,checkbox用
     * @return
     */
    @Override
    public String[] listRoleOption() {

        QueryWrapper<Role> roleWrapper = new QueryWrapper<>();
        List<Role> roleList= baseMapper.selectList(roleWrapper);
        String [] roleOptions=new String[roleList.size()];
        for (int i = 0; i <roleList.size(); i++) {
            roleOptions[i]=roleList.get(i).getRoleName();
        }
        return roleOptions;
    }

    @Override
    public Integer[] getRoleOptionOwn(Integer loginId) {
        QueryWrapper<SysUserRoles> userRolesQueryWrapper=new QueryWrapper<>();
        userRolesQueryWrapper.eq("LOGIN_ID",loginId);
        List<SysUserRoles> sysUserRolesList=userRoleMapper.selectList(userRolesQueryWrapper);
        Integer[] checkedRole=new Integer[sysUserRolesList.size()];
        for (int i = 0; i < sysUserRolesList.size(); i++) {
            checkedRole[i]=sysUserRolesList.get(i).getRoleId();
        }
        return checkedRole;
    }

    @Override
    public Integer roleCountByName(String roleName) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("ROLE_NAME",roleName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEty saveRole(Role role,Integer token) {
        if (role.getUseFlag()){//启用
            role.setStartDate(new Date());
            role.setEndDate(null);
        }else{//禁用
            role.setEndDate(new Date());
        }
        baseMapper.insert(role);
        if(role.getRoleId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        Integer roleId=role.getRoleId();
        Integer[] menuIds=role.getValue();
        if (menuIds!=null && menuIds.length>0){
            //插入新的关系数据
            SysRoleMenus sysRoleMenus=new SysRoleMenus();
            for (Integer menuId:menuIds) {
                sysRoleMenus.setFunctionId(menuId);
                sysRoleMenus.setRoleId(roleId);
                roleMenuMapper.insert(sysRoleMenus);
            }
        }
        systemLogService.saveMachineLog(token,"新增","新增了角色:"+role.getRoleName());
        return ResponseEty.success("操作成功");
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ResponseEty updateRole(Role role,Integer token) {
        if (role.getUseFlag()){//启用
            role.setStartDate(new Date());
            role.setEndDate(null);
        }else{//禁用
            role.setEndDate(new Date());
        }
        baseMapper.updateById(role);
        if(role.getRoleId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        Integer roleId=role.getRoleId();
        Integer[] menuIds=role.getValue();

        //先删除关系表 根据角色删除该角色所有的与权限关联的数据
        roleMenuMapper.deleleByRoleId(roleId);

        if (menuIds!=null && menuIds.length>0){

            //插入新的关系数据
            SysRoleMenus sysRoleMenus=new SysRoleMenus();
            for (Integer menuId:menuIds) {
                sysRoleMenus.setFunctionId(menuId);
                sysRoleMenus.setRoleId(roleId);
                roleMenuMapper.insert(sysRoleMenus);
            }
        }
        systemLogService.saveMachineLog(token,"更新","更新了角色:"+role.getRoleName());

        return ResponseEty.success("操作成功");
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
