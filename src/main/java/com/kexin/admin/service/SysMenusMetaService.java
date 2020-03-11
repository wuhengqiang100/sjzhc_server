package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.SysMenusMeta;
import org.apache.ibatis.annotations.Param;


/**
 * 菜单描述服务接口类
 */
public interface SysMenusMetaService extends IService<SysMenusMeta> {


    /**
     * 根据菜单描述编码计算数量,当前机器的code的数量
     * @param sysMenusMetaCode
     * @return
     */
    Integer sysMenusMetaCountByCode(@Param("sysMenusMetaCode") String sysMenusMetaCode);


    /**
     * 根据菜单描述名称计算数量
     * @param sysMenusMetaName
     * @return
     */
    Integer sysMenusMetaCountByName(@Param("sysMenusMetaName") String sysMenusMetaName);

    /**
     * 保存菜单描述
     * @param sysMenusMeta
     */
    void saveSysMenusMeta(@Param("sysMenusMeta") SysMenusMeta sysMenusMeta);


    /**
     * 修改更新菜单描述
     * @param sysMenusMeta
     */
    void updateSysMenusMeta(@Param("sysMenusMeta") SysMenusMeta sysMenusMeta);

    /**
     * 删除菜单描述(单个)
     * @param sysMenusMeta
     */
    void deleteSysMenusMeta(@Param("sysMenusMeta") SysMenusMeta sysMenusMeta);

    /**
     * 禁用或者启用菜单描述
     * @param sysMenusMeta
     */
    void lockSysMenusMeta(@Param("sysMenusMeta") SysMenusMeta sysMenusMeta);
}
