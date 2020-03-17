package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.QaInspectMaster;

import com.kexin.admin.entity.vo.QaInspectData;
import com.kexin.admin.entity.vo.QaInspectTransfer;
import com.kexin.admin.mapper.QaInspectMasterMapper;
import com.kexin.admin.service.QaInspectMasterService;
import com.kexin.common.util.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor = Exception.class)
public class QaInspectMasterServiceImpl extends ServiceImpl<QaInspectMasterMapper,QaInspectMaster> implements QaInspectMasterService {
    @Override
    public QaInspectData getAllQaInspectMaster() {
        return getInspectMasterData();
    }


    /**
     * 获取当前未核查,或者已核查的机检数据
     * @return
     */
    private QaInspectData getInspectMasterData(){
        QaInspectData qaInspectData=new QaInspectData();
        List<QaInspectMaster> qaInspectMasterList=baseMapper.getAllQaInspectMaster();
        List<QaInspectTransfer> qaInspectTransferList=new ArrayList<>();
        String[] value=new String[qaInspectMasterList.size()];
        int i=0;
        QaInspectTransfer qa=null;
        for(QaInspectMaster q:qaInspectMasterList){
            qa=new QaInspectTransfer();
            qa.setValue(String.valueOf(q.getWipProdLogs().getLogId()));
            //AllowJudge 0 可以审核
            qa.setChecked(false);
            //AllowJudge 1 循序分活
            if(q.getAllowJudge()==0){
                qa.setTitle(q.getWipJobs().getCartNumber()+"<span>&nbsp;&nbsp;</span> "+q.getProduct().getProductName()+"<span>&nbsp;&nbsp;</span>"+q.getOperation().getOperationName()+"<span>&nbsp;&nbsp;</span>10000"+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>50"+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>20");

            }
            if (q.getAllowJudge()==1){
//                qa.setChecked(true);
                value[i]= qa.getValue();
                i++;
                qa.setTitle(q.getWipJobs().getCartNumber()+"<span>&nbsp;&nbsp;</span> "+q.getProduct().getProductName()+"<span>&nbsp;&nbsp;</span>"+q.getOperation().getOperationName()+"<span>&nbsp;&nbsp;</span>10000"+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>50"+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>20"+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>未分活");

            }
            //AllowJudge 2 已经分完活,不能回退
            qa.setDisabled(false);
            if (q.getAllowJudge()==2){
                qa.setDisabled(true);
                value[i]= qa.getValue();
                i++;
                qa.setTitle(q.getWipJobs().getCartNumber()+"<span>&nbsp;&nbsp;</span> "+q.getProduct().getProductName()+"<span>&nbsp;&nbsp;</span>"+q.getOperation().getOperationName()+"<span>&nbsp;&nbsp;</span>10000"+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>50"+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>20"+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>已分活");

            }
            qaInspectTransferList.add(qa);

        }
        qaInspectData.setQaInspectTransfer(qaInspectTransferList);
        qaInspectData.setValue(value);
        return qaInspectData;
    }

    @Override
    public List<Map<String,Object>> getQaInspectMasterHistory() {
        //获取已分活的审核信息 allowJudge==2
        List<QaInspectMaster> qaInspectMasterList=baseMapper.selectQaInspectMaster(2);
        List<Map<String,Object>> historyInspectList=new ArrayList<>();
        Map<String,Object> map=null;
        for(QaInspectMaster q:qaInspectMasterList){
            map=new HashMap<>();
            map.put("title",q.getWipJobs().getCartNumber()+"  "+q.getProduct().getProductName()+"  "+q.getOperation().getOperationName());
            historyInspectList.add(map);
        }
        return historyInspectList;
    }

    /**
     * 只处理审核
     * 未审核的数据的更新为审核的数据
     * allowJudge==1
     * @param transferListransfer
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public QaInspectData saveQaInspectAllow(List<QaInspectTransfer> transferListransfer) {

        for(QaInspectTransfer q:transferListransfer){
            QueryWrapper<QaInspectMaster> wrapper = new QueryWrapper<>();
            wrapper.eq("LOG_ID",q.getValue());
            QaInspectMaster qa=baseMapper.selectOne(wrapper);
            qa.setAllowJudge(1);
            baseMapper.updateById(qa);
        }
        return getInspectMasterData();
    }

    /**
     * 回退  审核的数据
     * @param transferListransfer
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEntity returnQaInspect(List<QaInspectTransfer> transferListransfer) {
        ResponseEntity responseEntity=new ResponseEntity();
        String message=null;//错误信息
        try{
            for (QaInspectTransfer q:transferListransfer) {
                //根据logId获取InspectMaster信息
                QaInspectMaster qa=baseMapper.selectOneInspeceMasterByLogId(Integer.valueOf(q.getValue()));
                if (qa.getAllowJudge()==1){ //回退操作
                    qa.setAllowJudge(0);
                    baseMapper.updateById(qa);
                }else if(qa.getAllowJudge()==2){
                    message+=qa.getWipJobs().getCartNumber()+"  "+qa.getProduct().getProductName()+"  "+qa.getOperation().getOperationName()+"1000   20    3"+"\n";
                }
            }
            responseEntity.setSuccess(true);
            if (message!=null){
                responseEntity.setMessage(message);
            }
        }catch(Exception e){
            responseEntity.setSuccess(false);
            responseEntity.setMessage("回退错误!");
        }
        responseEntity.setAny("qaInspectData", getInspectMasterData());
        return responseEntity;
    }

    /**
     * 快速审核信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEntity quickSaveInspect() {
        ResponseEntity responseEntity=new ResponseEntity();

        QueryWrapper<QaInspectMaster> wrapper = new QueryWrapper<>();
        wrapper.eq("ALLOW_JUDGE",0);
        List<QaInspectMaster> qaInspectMasterList=baseMapper.selectList(wrapper);
        if (qaInspectMasterList.size()==0){
            responseEntity.setSuccess(false);
            return ResponseEntity.failure("当前没有需要审核的信息");
        }
        for (QaInspectMaster q:qaInspectMasterList){
            q.setAllowJudge(1);
            baseMapper.updateById(q);
        }
        responseEntity.setSuccess(true);
        responseEntity.setAny("qaInspectData", getInspectMasterData());
         return responseEntity;
    }


    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)
/*

    @Override
    public Integer qaInspectMasterCountByCode(String qaInspectMasterCode) {
        QueryWrapper<QaInspectMaster> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_CODE",qaInspectMasterCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer qaInspectMasterCountByName(String qaInspectMasterName) {
        QueryWrapper<QaInspectMaster> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_NAME",qaInspectMasterName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveQaInspectMaster(QaInspectMaster qaInspectMaster) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        baseMapper.insert(qaInspectMaster);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateQaInspectMaster(QaInspectMaster qaInspectMaster) {
//        dropUserRolesByUserId(user.getLoginId());
        baseMapper.updateById(qaInspectMaster);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteQaInspectMaster(QaInspectMaster qaInspectMaster) {
        baseMapper.deleteById(qaInspectMaster.getQaInspectMasterId());
    }

    @Override
    public void lockQaInspectMaster(QaInspectMaster qaInspectMaster) {
        qaInspectMaster.setUseFlag(qaInspectMaster.getUseFlag()==1?0:1);
        baseMapper.updateById(qaInspectMaster);
    }
*/


}
