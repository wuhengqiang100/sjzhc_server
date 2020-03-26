package com.kexin.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kexin.admin.entity.tables.QaInspectMaster;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


public interface QaInspectMasterMapper extends BaseMapper<QaInspectMaster> {

    /**
     * 获取所有的分活信息
     * @return
     */
    List<QaInspectMaster> getAllQaInspectMaster(@Param("startTime") Date startTime,@Param("endTime") Date endTime);
    /**
     * 根据 ALLOW_JUDGE 获取分活的QaInspectMaster
     * @param allowJudge
     * @return
     */
    List<QaInspectMaster> selectQaInspectMaster(@Param("allowJudge") Integer allowJudge);


    /**
     * 根据logId生产日志id获取InspectMaster信息
     * @param logId
     * @return
     */
    QaInspectMaster selectOneInspeceMasterByLogId(@Param("logId") Integer logId);
}