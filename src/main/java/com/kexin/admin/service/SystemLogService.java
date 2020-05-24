package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.SystemLog;
import com.kexin.admin.entity.tables.WorkUnit;

public interface SystemLogService extends IService<SystemLog> {


    /**
     * 保存系统操作日志
     * @param token
     * @param logType
     * @param note
     */
    void saveMachineLog(Integer token,String logType ,String note);


}
