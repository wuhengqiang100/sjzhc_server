package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.SysMenus;
import com.kexin.common.util.ResponseEty;
import org.apache.ibatis.annotations.Param;


/**
 * 菜单服务接口类
 */
public interface SysMenusService extends IService<SysMenus> {



    /**
     * @Title:
     * @Description: TODO(获取所有的菜单)
     * @param @param
     * @return @return
     * @author 13015
     * @throws
     * @date 2020/3/11 13:10
     */
    ResponseEty getSysMenus();

    /**
     * 根据菜单编码计算数量,当前机器的code的数量
     * @param sysMenusCode
     * @return
     */
    Integer sysMenusCountByCode(@Param("sysMenusCode") String sysMenusCode);


    /**
     * 根据菜单名称计算数量
     * @param sysMenusName
     * @return
     */
    Integer sysMenusCountByName(@Param("sysMenusName") String sysMenusName);

    /**
     * 保存菜单
     * @param sysMenus
     */
    void saveSysMenus(@Param("sysMenus") SysMenus sysMenus);


    /**
     * 修改更新菜单
     * @param sysMenus
     */
    void updateSysMenus(@Param("sysMenus") SysMenus sysMenus);

    /**
     * 删除菜单(单个)
     * @param sysMenus
     */
    void deleteSysMenus(@Param("sysMenus") SysMenus sysMenus);

    /**
     * 禁用或者启用菜单
     * @param sysMenus
     */
    void lockSysMenus(@Param("sysMenus") SysMenus sysMenus);
}
