package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.QaInspectMaster;
import com.kexin.admin.entity.vo.QaInspectChange;
import com.kexin.admin.entity.vo.query.QueryDate;
import com.kexin.admin.entity.vo.query.SaveCheckData;
import com.kexin.admin.entity.vo.query.SaveNoteData;
import com.kexin.common.util.ResponseEntity;
import com.kexin.common.util.ResponseEty;

import java.util.List;
import java.util.Map;

public interface QaInspectMasterService extends IService<QaInspectMaster> {
    //ALLOW_JUDGE
    //是否允许判废 0:不能分活  1:可以分活  2:已分活


    ResponseEty saveQaInspectMaster(QaInspectChange inspectChange);
    /**
     * 获取没有审核的车次,以及仅限于今天已经审核的和已分活的车次
     * @return
     */
    List<QaInspectMaster> getAllQaInspectMaster();

    /**
     * 获取没有审核的车次
     * @return
     */
    ResponseEty getCanAuditInspectMaster(QueryDate queryDate);

    /**
     * 保存审核的车次西门西
     * @param saveCheckData
     * @return
     */
    ResponseEty saveCanAuditInspectMaster(SaveCheckData saveCheckData,Integer token);

    /**
     * 获取已经审核的车次
     * @return
     */
    ResponseEty getAlreadyAuditInspectMaster(QueryDate queryDate);


    /**
     * 保存回退审核的车次西
     * @param saveCheckData
     * @return
     */
    ResponseEty saveAlreadyAuditInspectMaster(SaveCheckData saveCheckData,Integer token);

     /**
     * 获取不走审核的车次
     * @return
     */
//     ResponseEty getNotAuditInspectMaster(QueryDate queryDate);


    /**
     * 保存走全检的车次
     * @param saveCheckData
     * @return
     */
    ResponseEty saveNotAuditInspectMaster(SaveCheckData saveCheckData,Integer token);
    /**
     * 保存修改的备注
     * @param saveNoteData
     * @return
     */
    ResponseEty saveNoteInspectMaster(SaveNoteData saveNoteData, Integer token);

    /**
     * 回退走全检的车次
     * @param saveCheckData
     * @return
     */
//    ResponseEty returnNotAuditInspectMaster(SaveCheckData saveCheckData,Integer token);



}
