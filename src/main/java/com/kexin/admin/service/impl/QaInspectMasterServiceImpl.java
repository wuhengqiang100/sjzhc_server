package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.LoginUser;
import com.kexin.admin.entity.tables.OperationLog;
import com.kexin.admin.entity.tables.Operator;
import com.kexin.admin.entity.tables.QaInspectMaster;
import com.kexin.admin.entity.vo.QaInspectChange;
import com.kexin.admin.mapper.LoginUserMapper;
import com.kexin.admin.mapper.OperationLogMapper;
import com.kexin.admin.mapper.OperatorMapper;
import com.kexin.admin.mapper.QaInspectMasterMapper;
import com.kexin.admin.service.QaInspectMasterService;
import com.kexin.common.util.DateUtil.TodayUtil;
import com.kexin.common.util.ResponseEty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class QaInspectMasterServiceImpl extends ServiceImpl<QaInspectMasterMapper,QaInspectMaster> implements QaInspectMasterService {


    @Resource
    OperationLogMapper operationLogMapper;//操作日志mapper

    @Resource
    LoginUserMapper loginUserMapper;//登录用户mapper

    @Resource
    OperatorMapper operatorMapper;//人员mapper

    @Override
    @Transactional(rollbackFor = Exception.class)//有修改操作一定要加上事务
    public ResponseEty saveQaInspectMaster(QaInspectChange inspectChange) {
        QaInspectMaster qaInspectMaster;
        QueryWrapper<QaInspectMaster> qaInspectMasterQueryWrapper=new QueryWrapper<>();
        if (inspectChange.getDirection().equals("right")){//审核操作
            qaInspectMaster=new QaInspectMaster();
            qaInspectMaster.setAllowJudge(1);
            qaInspectMasterQueryWrapper.in("INSPECTM_ID",inspectChange.getMovedKeys());
            baseMapper.update(qaInspectMaster,qaInspectMasterQueryWrapper);
            saveOperationLog(inspectChange,1);//保存核查审核日志
            return ResponseEty.success("审核成功");

        }else{//回退操作
            qaInspectMaster=new QaInspectMaster();
            qaInspectMaster.setAllowJudge(0);
            qaInspectMasterQueryWrapper.in("INSPECTM_ID",inspectChange.getMovedKeys());
            baseMapper.update(qaInspectMaster,qaInspectMasterQueryWrapper);
            saveOperationLog(inspectChange,2);//保存核查回退日志
            return ResponseEty.success("回退成功");

        }
    }

    /**
     * 获取没有审核的车次,以及仅限于今天已经审核的和已分活的车次
     * @return
     */
    @Override
    public List<QaInspectMaster> getAllQaInspectMaster() {
//        Calendar
        return baseMapper.getAllQaInspectMaster(TodayUtil.getStartTime(),TodayUtil.getEndTime());
    }

    /**
     * 保存日志操作
     * @param inspectChange
     * @param flag== 1 审核操作,flag==2回退操作
     */
    private void saveOperationLog(QaInspectChange inspectChange,Integer flag){
        List<QaInspectMaster> qaInspectMasterList=baseMapper.selectQaInspectMasterByInspectmIds(inspectChange.getMovedKeys());
        LoginUser loginUser=loginUserMapper.selectById(inspectChange.getTokenId());
        Operator operator=operatorMapper.selectById(loginUser.getOperatorId());
        if (flag==1){//审核操作
            OperationLog operationLog;
            for (QaInspectMaster qa:qaInspectMasterList) {
                operationLog=new OperationLog();
                operationLog.setJobId(qa.getWipJobs().getJobId());
                    operationLog.setOperatorId(operator.getOperatorId());
                    operationLog.setOperatorName(operator.getOperatorName());
                    operationLog.setNote(operator.getOperatorName()+"核查审核了"+qa.getWipJobs().getCartNumber()+"车");


                operationLog.setStartDate(new Date());
                operationLog.setItemFlag(qa.getItemFlag());
                operationLog.setOperationNoteType("核查审核");
                operationLogMapper.insert(operationLog);
            }
        }
        else{
            OperationLog operationLog;
            for (QaInspectMaster qa:qaInspectMasterList) {
                operationLog=new OperationLog();
                operationLog.setJobId(qa.getWipJobs().getJobId());
                    operationLog.setOperatorId(operator.getOperatorId());
                    operationLog.setOperatorName(operator.getOperatorName());
                    operationLog.setNote(operator.getOperatorName()+"核查回退"+qa.getWipJobs().getCartNumber()+"车");

                operationLog.setStartDate(new Date());
                operationLog.setItemFlag(qa.getItemFlag());

                operationLog.setOperationNoteType("核查回退");
                operationLogMapper.insert(operationLog);
            }
        }
    }



  /*  @Override
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
    }*/

    /**
     * 只处理审核
     * 未审核的数据的更新为审核的数据
     * allowJudge==1
     * @param transferListransfer
     * @return
     */
/*    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<QaInspectMaster> saveQaInspectAllow(List<QaInspectTransfer> transferListransfer) {

        for(QaInspectTransfer q:transferListransfer){
            QueryWrapper<QaInspectMaster> wrapper = new QueryWrapper<>();
            wrapper.eq("LOG_ID",q.getValue());
            QaInspectMaster qa=baseMapper.selectOne(wrapper);
            qa.setAllowJudge(1);
            baseMapper.updateById(qa);
        }
        return getInspectMasterData();
    }*/

    /**
     * 回退  审核的数据
     * @param transferListransfer
     * @return
     */
 /*   @Transactional(rollbackFor = Exception.class)
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
    }*/

    /**
     * 快速审核信息
     * @return
     */
/*
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
*/


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
