package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.SysFunctions;
import com.kexin.admin.entity.tables.SysMenus;
import com.kexin.admin.entity.tables.SysMenusMeta;
import com.kexin.admin.entity.vo.Menu;
import com.kexin.admin.entity.vo.MenuMetas;
import com.kexin.admin.mapper.SysFunctionMapper;
import com.kexin.admin.mapper.SysMenusMapper;
import com.kexin.admin.service.SysFunctionService;
import com.kexin.admin.service.SysMenusService;
import com.kexin.common.util.ResponseEty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Override
    public ResponseEty getSysFunctions() {
        ResponseEty responseEty=new ResponseEty();
        QueryWrapper<SysFunctions> sysFunctionsQueryWrapper = new QueryWrapper<>();
        sysFunctionsQueryWrapper.eq("FUNCTON_TYPE_ID",0);//0位菜单类型
        sysFunctionsQueryWrapper.isNull("FUNCTON_PARENT_ID");//第一级菜单
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
            sysMenusChildQueryWrapper.in("FUNCTION_ID",childrenIds);
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
        wrapper.eq("FUNCTION_CODE",SysFunctionsCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer SysFunctionsCountByName(String SysFunctionsName) {
        QueryWrapper<SysFunctions> wrapper = new QueryWrapper<>();
        wrapper.eq("FUNCTION_NAME",SysFunctionsName);
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
        baseMapper.deleteById(SysFunctions.getFunctionId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockSysFunctions(SysFunctions SysFunctions) {
/*        if (SysFunctions.getUseFlag()){
            SysFunctions.setUseFlag(false);
            SysFunctions.setEndDate(new Date());
        }else{
            SysFunctions.setUseFlag(true);
            SysFunctions.setEndDate(null);
        }*/
        baseMapper.updateById(SysFunctions);
    }
}
