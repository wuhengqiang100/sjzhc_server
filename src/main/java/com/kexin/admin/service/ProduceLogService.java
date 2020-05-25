package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.ProduceLog;
import com.kexin.admin.entity.tables.QaInspectMaster;
import org.apache.ibatis.annotations.Param;

public interface ProduceLogService extends IService<ProduceLog> {
    /**
     * 保存核查审核先相关日志
     * @param token
     * @param logType
     * @param note
     */
    void saveProduceLog(QaInspectMaster qaInspectMaster,Integer token, String logType , String note);
}
