package com.kexin.admin.service;

import com.kexin.admin.entity.pojo.InitData;

/**
 * 初始化服务数据
 */
public interface InitService {

    /**
     * 系统初始化
     * @return
     */
    InitData systemInit(int userId);
}
