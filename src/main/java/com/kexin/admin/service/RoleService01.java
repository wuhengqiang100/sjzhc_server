package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.Role01;

import java.util.List;

public interface RoleService01 extends IService<Role01> {

    long getRoleNameCount(String name);

    Role01 saveRole(Role01 role01);

    Role01 getRoleById(String id);

    void updateRole(Role01 role01);

    void deleteRole(Role01 role01);

    List<Role01> selectAll();
}
