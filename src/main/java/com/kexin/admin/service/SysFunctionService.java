package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.SysFunctions;
import com.kexin.admin.entity.vo.MenuTree;
import com.kexin.common.util.ResponseEty;
import org.apache.ibatis.annotations.Param;


/**
 * 菜单服务接口类
 */
public interface SysFunctionService extends IService<SysFunctions> {



    /**
     * @Title:
     * @Description: TODO(获取所有的菜单)
     * @param @param
     * @return @return
     * @author 13015
     * @throws
     * @date 2020/3/11 13:10
     */
    ResponseEty getSysFunctions(Integer token);

    /**
     * 角色分配权限时,获取所有的菜单
     * @return
     */
    MenuTree[]  getSysFunctionOptionB();

    /**
     * 角色分配权限时,获取该角色已获得c端权限
     * @param roleId
     * @return
     */
    Integer[] getSysFunctionOwnB(@Param("roleId") Integer roleId);
    /**
     * 角色分配权限时,获取所有的菜单 string数组
     * @return
     */
    String[] getSysFunctionOptionC();

    /**
     * 角色分配权限时,获取该角色已获得c端权限 string数组
     * @param roleId
     * @return
     */
    String[] getSysFunctionOwnC(@Param("roleId") Integer roleId);

    /**
     * 根据菜单编码计算数量,当前机器的code的数量
     * @param SysFunctionsCode
     * @return
     */
    Integer SysFunctionsCountByCode(@Param("SysFunctionsCode") String SysFunctionsCode);


    /**
     * 根据菜单名称计算数量
     * @param SysFunctionsName
     * @return
     */
    Integer SysFunctionsCountByName(@Param("SysFunctionsName") String SysFunctionsName);

    /**
     * 保存菜单
     * @param SysFunctions
     */
    void saveSysFunctions(@Param("SysFunctions") SysFunctions SysFunctions);


    /**
     * 修改更新菜单
     * @param SysFunctions
     */
    void updateSysFunctions(@Param("SysFunctions") SysFunctions SysFunctions);

    /**
     * 删除菜单(单个)
     * @param SysFunctions
     */
    void deleteSysFunctions(@Param("SysFunctions") SysFunctions SysFunctions);

    /**
     * 禁用或者启用菜单
     * @param SysFunctions
     */
    void lockSysFunctions(@Param("SysFunctions") SysFunctions SysFunctions);
}
