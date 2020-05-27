package com.kexin.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kexin.admin.entity.tables.QaInspectMaster;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


public interface QaInspectMasterMapper extends BaseMapper<QaInspectMaster> {

    /**
     * 获取没有审核的车次
     * @return
     */
    List<QaInspectMaster> getCanAuditInspectMaster(@Param("startTime") Date startTime,@Param("endTime") Date endTime,@Param("cartNumber") String cartNumber);

    /**
     * 获取已经审核的车次
     * @return
     */
    List<QaInspectMaster> getAlreadyAuditInspectMaster(@Param("startTime") Date startTime,@Param("endTime") Date endTime,@Param("cartNumber") String cartNumber);
    List<QaInspectMaster> getAlreadyAuditInspectMasterByAllowJudge(@Param("startTime") Date startTime,@Param("endTime") Date endTime,@Param("cartNumber") String cartNumber,@Param("allowJudge") Integer allowJudge);

    /**
     * 获取不走审核的车次
     * @return
     */
    List<QaInspectMaster> getNotAuditInspectMaster(@Param("startTime") Date startTime,@Param("endTime") Date endTime);

















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
     * @param inspectmIds
     * @return
     */
    List<QaInspectMaster> selectQaInspectMasterByInspectmIds(@Param("inspectmIds") Integer[] inspectmIds);

    /**
     * 根据logId生产日志id获取InspectMaster信息
     * @param inspectmId
     * @return
     */
    QaInspectMaster selectQaInspectMasterByInspectmId(@Param("inspectmId") Integer inspectmId);

    List<QaInspectMaster> selectQaInspectMasterByIds(@Param("inspectmIds") Integer[] inspectmIds);

}