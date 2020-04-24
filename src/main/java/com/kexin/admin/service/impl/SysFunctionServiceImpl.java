package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.*;
import com.kexin.admin.entity.vo.Menu;
import com.kexin.admin.entity.vo.MenuMetas;
import com.kexin.admin.entity.vo.MenuTree;
import com.kexin.admin.mapper.LoginUserMapper;
import com.kexin.admin.mapper.RoleMenuMapper;
import com.kexin.admin.mapper.SysFunctionMapper;
import com.kexin.admin.mapper.UserRoleMapper;
import com.kexin.admin.service.SysFunctionService;
import com.kexin.common.util.ResponseEty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysFunctionServiceImpl extends ServiceImpl<SysFunctionMapper, SysFunctions> implements SysFunctionService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)


    @Resource
    RoleMenuMapper roleMenuMapper;//角色菜单关系service

    @Resource
    UserRoleMapper userRoleMapper;//账户和角色关系表

    @Resource
    LoginUserMapper loginUserMapper;//账户mapper

    /**
     * 角色分配权限时,获取所有的b端权限option MenuTree[]
     * @return
     */
    @Override
    public MenuTree[] getSysFunctionOptionB() {

        QueryWrapper<SysFunctions> sysMenusQueryWrapper = new QueryWrapper<>();//b端的菜单权限
        sysMenusQueryWrapper.isNull("FUNCTON_PARENT_ID")
        .eq("FUNCTON_TYPE_ID",1)
        .eq("IS_SHOW",1)
        .orderByAsc("FUNCTON_SORT");//不显示最高权限的相关功能

//        List<SysFunctions> sysMenusList=sysMenusService.list(sysMenusQueryWrapper);
        List<SysFunctions> sysMenusList=baseMapper.selectList(sysMenusQueryWrapper);
        MenuTree[] menuTree=new MenuTree[sysMenusList.size()];//总返回的菜单树
        MenuTree menuTree1;
        for (int i=0;i<sysMenusList.size();i++) {//SysFunctions menu:sysMenusList
            menuTree1=new MenuTree();
            menuTree1.setId(sysMenusList.get(i).getFunctonId());
            //把一级主菜单的描述信息放进去
            menuTree1.setLabel(sysMenusList.get(i).getTitle());

            menuTree[i]=menuTree1;
            if (StringUtils.isNotEmpty(sysMenusList.get(i).getChildrenIds())){
                String[] childrenIds=StringUtils.split(sysMenusList.get(i).getChildrenIds(),',');
                QueryWrapper<SysFunctions> sysMenusChildQueryWrapper = new QueryWrapper<>();
                sysMenusChildQueryWrapper.in("FUNCTON_ID",childrenIds);
                List<SysFunctions> sysMenusChildList=baseMapper.selectList(sysMenusChildQueryWrapper);
                MenuTree[] menuTreeChild=new MenuTree[sysMenusChildList.size()];//子菜单树
                MenuTree menuTree2;
                for (int j=0;j<sysMenusChildList.size();j++){// menuChild:sysMenusChildList
                    //把二级主菜单的描述信息放进去
                    menuTree2=new MenuTree();
                    menuTree2.setId(sysMenusChildList.get(j).getFunctonId());
                    menuTree2.setLabel(sysMenusChildList.get(j).getTitle());
                    menuTreeChild[j]=menuTree2;
                }
                menuTree1.setChildren(menuTreeChild);

            }

        }
        return  menuTree;
    }

    /**
     * 角色分配权限是获取b端已分配的权限
     * @param roleId
     * @return
     */
    @Override
    public Integer[] getSysFunctionOwnB(Integer roleId) {
        QueryWrapper<SysRoleMenus> roleMenusQueryWrapper = new QueryWrapper<>();
        roleMenusQueryWrapper.eq("ROLE_ID",roleId);
        List<SysRoleMenus> sysRoleMenusList=roleMenuMapper.selectList(roleMenusQueryWrapper);
        Integer[] menuIds=new Integer[sysRoleMenusList.size()];
        if (sysRoleMenusList.size()!=0){

            for (int i = 0; i <sysRoleMenusList.size() ; i++) {
                menuIds[i]=sysRoleMenusList.get(i).getFunctionId();
            }
            return menuIds;
        }
        menuIds=null;
        return menuIds;

    }

    /**
     * 角色分配权限时,获取所有的c端权限
     * @return
     */
    @Override
    public String[] getSysFunctionOptionC() {
        QueryWrapper<SysFunctions> sysMenusQueryWrapper = new QueryWrapper<>();
        sysMenusQueryWrapper.eq("FUNCTON_TYPE_ID",2);//c端的菜单权限

        List<SysFunctions> sysMenusList=baseMapper.selectList(sysMenusQueryWrapper);
        String[] cPermiss=new String[sysMenusList.size()];
        for (int i=0;i<sysMenusList.size();i++){
            cPermiss[i]=sysMenusList.get(i).getTitle();//把c端权限的名称放到数组里面返回
        }
        return cPermiss;
    }

    /**
     * 角色分配权限时,获取已分配的c端权限
     * @param roleId
     * @return
     */
    @Override
    public String[] getSysFunctionOwnC(Integer roleId) {
        QueryWrapper<SysRoleMenus> roleMenusQueryWrapper = new QueryWrapper<>();
        roleMenusQueryWrapper.eq("ROLE_ID",roleId);//
        List<SysRoleMenus> sysRoleMenusList=roleMenuMapper.selectList(roleMenusQueryWrapper);
        String[] cPermissOwn=new String[sysRoleMenusList.size()];
        if (sysRoleMenusList.size()!=0){

            for (int i = 0; i <sysRoleMenusList.size() ; i++) {
                cPermissOwn[i]=baseMapper.selectById(sysRoleMenusList.get(i).getFunctionId()).getTitle();
            }
            return cPermissOwn;
        }
        cPermissOwn=null;
        return cPermissOwn;
    }

    /**
     * 菜单初始化获取
     * @return
     */
    @Override
    public ResponseEty getSysFunctions(Integer token) {
        ResponseEty responseEty=new ResponseEty();
//        QueryWrapper<LoginUser> loginUserQueryWrapper=new QueryWrapper();
//        loginUserQueryWrapper.eq("LOGIN_ID",token);
//        LoginUser loginUser=loginUserMapper.selectOne(loginUserQueryWrapper);

        //根据operatorId获取账户角色关系
        QueryWrapper<SysUserRoles> sysUserRolesQueryWrapper=new QueryWrapper<>();
        sysUserRolesQueryWrapper.eq("USER_ID",token);
        List<SysUserRoles> sysUserRolesList=userRoleMapper.selectList(sysUserRolesQueryWrapper);
        Integer[] roles=new Integer[sysUserRolesList.size()];
        for (int i = 0; i <sysUserRolesList.size() ; i++) {
            roles[i]=sysUserRolesList.get(i).getRoleId();
        }
        //根据角色id数据获能展示的菜单
        QueryWrapper<SysRoleMenus> sysRoleMenusQueryWrapper=new QueryWrapper<>();
        sysRoleMenusQueryWrapper.in("ROLE_ID",roles);
        List<SysRoleMenus> sysRoleMenusList=roleMenuMapper.selectList(sysRoleMenusQueryWrapper);

        Integer[] functonIds=new Integer[sysRoleMenusList.size()];
        for (int j = 0; j <sysRoleMenusList.size() ; j++) {
            functonIds[j]=sysRoleMenusList.get(j).getFunctionId();
        }
        QueryWrapper<SysFunctions> sysFunctionsQueryWrapper = new QueryWrapper<>();
        sysFunctionsQueryWrapper.eq("FUNCTON_TYPE_ID",1)//1是b端菜单类型
        .isNull("FUNCTON_PARENT_ID")//第一级菜单
        .in("FUNCTON_ID",functonIds)
                .orderByAsc("FUNCTON_SORT");
        List<SysFunctions> sysFunctionsList=baseMapper.selectList(sysFunctionsQueryWrapper);
        List<Menu> sysMenusList=new ArrayList<>();
        for (SysFunctions function:sysFunctionsList) {
            sysMenusList.add(getMenus(function));
        }
        responseEty.setSuccess(20000);
        responseEty.setAny("asyncRoutes",sysMenusList);
        return responseEty;
    }



    //解析处理menus
    private Menu getMenus(SysFunctions function){
        Menu menu=getMenuMeta(function);
        if (StringUtils.isNotEmpty(function.getChildrenIds())){
            String[] childrenIds=StringUtils.split(function.getChildrenIds(),',');
            QueryWrapper<SysFunctions> sysMenusChildQueryWrapper = new QueryWrapper<>();
            sysMenusChildQueryWrapper.in("FUNCTON_ID",childrenIds);
            List<SysFunctions> sysMenusChildList=baseMapper.selectList(sysMenusChildQueryWrapper);
            List<Menu> menuList=new ArrayList<>();
            for (SysFunctions menuChild:sysMenusChildList){
                menuList.add(getMenuMeta(menuChild));
            }
            menu.setChildren(menuList);
        }
        return menu;
    }
    // 基于对象的转换
    private Menu getMenuMeta(SysFunctions function){
        Menu menu=new Menu();
        MenuMetas meta=new MenuMetas();
        menu.setPath(function.getPath());//请求路径
        menu.setName(function.getName());//路由名
        menu.setAlwaysShow(function.getAlwaysShow());
        menu.setHidden(function.getHidden());
        menu.setComponent(function.getComponent());//组件地址
        menu.setRedirect(function.getRedirect());//重定向标志

        meta.setBreadcrumb(function.getBreadcrumb());
        meta.setIcon(function.getIcon());
        meta.setNoCache(function.getNoCache());
        meta.setTitle(function.getTitle());//菜单名称
        meta.setRoles(StringUtils.split(function.getRoles()));//存储角色的时候会用逗号,隔开,输出时转换为string数组
        menu.setMeta(meta);
        return menu;
    }

    @Override
    public Integer SysFunctionsCountByCode(String SysFunctionsCode) {
        QueryWrapper<SysFunctions> wrapper = new QueryWrapper<>();
        wrapper.eq("FUNCTON_CODE",SysFunctionsCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer SysFunctionsCountByName(String SysFunctionsName) {
        QueryWrapper<SysFunctions> wrapper = new QueryWrapper<>();
        wrapper.eq("FUNCTON_NAME",SysFunctionsName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer SysFunctionsCountByTitle(String SysFunctionsTitle) {
        QueryWrapper<SysFunctions> wrapper = new QueryWrapper<>();
        wrapper.eq("TITLE",SysFunctionsTitle);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysFunctions(SysFunctions SysFunctions) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        baseMapper.insert(SysFunctions);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSysFunctions(SysFunctions SysFunctions) {
//        dropUserRolesByUserId(user.getLoginId());
        baseMapper.updateById(SysFunctions);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSysFunctions(SysFunctions SysFunctions) {
        baseMapper.deleteById(SysFunctions.getFunctonId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockSysFunctions(SysFunctions SysFunctions) {
        if (SysFunctions.getHidden()){
            SysFunctions.setHidden(false);
        }else{
            SysFunctions.setHidden(true);
        }
        baseMapper.updateById(SysFunctions);
    }
}
