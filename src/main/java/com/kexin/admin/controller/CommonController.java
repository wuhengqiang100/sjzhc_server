package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.Machine;
import com.kexin.admin.entity.tables.SysMenus;
import com.kexin.admin.entity.tables.SysMenusMeta;
import com.kexin.admin.service.LoginUserService;
import com.kexin.admin.service.MachineService;
import com.kexin.admin.service.SysMenusMetaService;
import com.kexin.admin.service.SysMenusService;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.base.Data;
import com.kexin.common.base.PageDataBase;
import com.kexin.common.util.ResponseEty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 公用controller
 */
@Controller
@RequestMapping("common")
public class CommonController {

    @Autowired
    SysMenusService sysMenusService;

    @Autowired
    SysMenusMetaService sysMenusMetaService;

    @Autowired
    LoginUserService loginUserService;

    /**
     * @Title:
     * @Description: TODO(数据库动态获取router)
     * @param @param
     * @return @return
     * @author 13015
     * @throws
     * @date 2020/3/12 14:25
     */
    @PostMapping("menu")
    @ResponseBody
    @SysLog("获取动态路由菜单")
    public ResponseEty create(){
        ResponseEty responseEty=new ResponseEty();
        QueryWrapper<SysMenus> sysMenusQueryWrapper = new QueryWrapper<>();
        sysMenusQueryWrapper.isNull("PARENT_ID");
        List<SysMenus> sysMenusList=sysMenusService.list(sysMenusQueryWrapper);
        for (SysMenus menu:sysMenusList) {
            //把一级主菜单的描述信息放进去
            SysMenusMeta sysMenusMeta=sysMenusMetaService.getById(menu.getMetaId());
            if (sysMenusMeta!=null){
                menu.setMeta(sysMenusMeta);
            }
            if (StringUtils.isNotEmpty(menu.getChildrenIds())){
                String[] childrenIds=StringUtils.split(menu.getChildrenIds(),',');
                QueryWrapper<SysMenus> sysMenusChildQueryWrapper = new QueryWrapper<>();
                sysMenusChildQueryWrapper.in("ID",childrenIds);
                List<SysMenus> sysMenusChildList=sysMenusService.list(sysMenusChildQueryWrapper);
                for (SysMenus menuChild:sysMenusChildList){
                    //把二级主菜单的描述信息放进去
                    SysMenusMeta sysMenusChildMeta=sysMenusMetaService.getById(menuChild.getMetaId());
                    if (sysMenusMeta!=null){
                        menuChild.setMeta(sysMenusChildMeta);
                    }
                }
                menu.setChildren(sysMenusChildList);
            }
        }
        responseEty.setSuccess(20000);
        responseEty.setAny("asyncRoutes",sysMenusList);
        return responseEty;
    }


    /**
     * @Title:用户登陆
     * @param @param
     * @return @return
     * @author 巫恒强
     * @throws
     * @date 2020/3/12 14:26
     */
    @PostMapping("login")
    @ResponseBody
    public ResponseEty login(@RequestBody Map map){

        return loginUserService.login(map);
    }

    /**
     * @Title:获取用户的基本信息
     * @param @param
     * @return @return
     * @author 巫恒强
     * @throws
     * @date 2020/3/12 14:27
     */
    @GetMapping("info")
    @ResponseBody
    public ResponseEty info(@RequestParam String token){
        System.out.println(token);
        return loginUserService.userInfo(token);
    }

}
