package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.QaInspectMaster;
import com.kexin.admin.entity.vo.QaInspectData;
import com.kexin.admin.entity.vo.QaInspectTransfer;
import com.kexin.common.util.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface QaInspectMasterService extends IService<QaInspectMaster> {
    //ALLOW_JUDGE
    //是否允许判废 0:不能分活  1:可以分活  2:已分活

    /**
     * 获取所有的分活信息
     * @return
     */
    QaInspectData getAllQaInspectMaster();

    /**
     * 根据 ALLOW_JUDGE 获取分活的QaInspectMaster
     * @return
     */
    List<Map<String,Object>> getQaInspectMasterHistory();

    /**
     * 保存当前已审核的信息
     * @param transferListransfer
     * @return
     */
    QaInspectData saveQaInspectAllow(List<QaInspectTransfer> transferListransfer);

    /**
     * 回退已审核的信息
     * @param transferListransfer
     * @return
     */
    ResponseEntity returnQaInspect(List<QaInspectTransfer> transferListransfer);

    /**
     * 快速审核信息
     * @return
     */
    ResponseEntity quickSaveInspect();
}
