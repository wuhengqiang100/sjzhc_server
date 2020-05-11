package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.Role;
import com.kexin.admin.entity.tables.SysFunctions;
import com.kexin.admin.entity.tables.SysRoleMenus;
import com.kexin.admin.entity.tables.SysUserRoles;
import com.kexin.admin.mapper.RoleMapper;
import com.kexin.admin.mapper.RoleMenuMapper;
import com.kexin.admin.mapper.SysFunctionMapper;
import com.kexin.admin.mapper.UserRoleMapper;
import com.kexin.admin.service.RoleService;
import com.kexin.common.util.ResponseEty;
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

    @Resource
    RoleMenuMapper roleMenuMapper;

    @Resource
    SysFunctionMapper sysFunctionMapper;
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
        userRolesQueryWrapper.eq("LOGIN_ID",userId);
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
//    @Transactional(rollbackFor = Exception.class)
    public ResponseEty updateRole(Role role) {
//        baseMapper.updateById(role);
        if(role.getRoleId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        Integer roleId=role.getRoleId();
        //先删除原来的关系数据
        roleMenuMapper.deleleByRoleId(roleId);
        //再添加新的数据
        //b端权限
        if (role.getMenuIds()!=null){

            Integer [] menuIds=role.getMenuIds();
            SysRoleMenus sysRoleMenu=null;
            for (Integer menuId:menuIds) {
                sysRoleMenu=new SysRoleMenus();
                sysRoleMenu.setRoleId(roleId);
                sysRoleMenu.setFunctionId(menuId);
                roleMenuMapper.insert(sysRoleMenu);

            }

        }
        //保存c端权限
        if (role.getCheckedPermiss()!=null){
            String [] checkedPermiss=role.getCheckedPermiss();//根据function 表里面的title字段查找
            SysRoleMenus sysRoleMenu=null;
            for (String permiss:checkedPermiss){
                QueryWrapper<SysFunctions> sysFunctionsQueryWrapper=new QueryWrapper<>();
                sysFunctionsQueryWrapper.eq("TITLE",permiss);
                SysFunctions sysFunction=sysFunctionMapper.selectOne(sysFunctionsQueryWrapper);
                sysRoleMenu=new SysRoleMenus();
                sysRoleMenu.setRoleId(roleId);
                sysRoleMenu.setFunctionId(sysFunction.getFunctionId());
                roleMenuMapper.insert(sysRoleMenu);

            }
        }
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
