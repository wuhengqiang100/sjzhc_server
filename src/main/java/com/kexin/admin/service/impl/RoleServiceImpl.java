package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.Role;
import com.kexin.admin.entity.tables.SysUserRoles;
import com.kexin.admin.mapper.RoleMapper;
import com.kexin.admin.mapper.UserRoleMapper;
import com.kexin.admin.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色服务实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


    @Resource
    UserRoleMapper userRoleMapper;
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
    public String[] getRoleOptionOwn(Integer userId) {
        QueryWrapper<SysUserRoles> userRolesQueryWrapper=new QueryWrapper<>();
        userRolesQueryWrapper.eq("USER_ID",userId);
        List<SysUserRoles> sysUserRolesList=userRoleMapper.selectList(userRolesQueryWrapper);
        String[] checkedRole=new String[sysUserRolesList.size()];
        for (int i = 0; i < sysUserRolesList.size(); i++) {
            checkedRole[i]=baseMapper.selectById(sysUserRolesList.get(i).getRoleId()).getRoleName();
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
