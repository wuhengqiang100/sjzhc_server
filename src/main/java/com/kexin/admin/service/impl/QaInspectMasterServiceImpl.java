package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.OperationLog;
import com.kexin.admin.entity.tables.Operator;
import com.kexin.admin.entity.tables.QaInspectMaster;
import com.kexin.admin.entity.vo.QaInspectChange;
import com.kexin.admin.entity.vo.query.QueryDate;
import com.kexin.admin.entity.vo.query.SaveCheckData;
import com.kexin.admin.entity.vo.query.SaveNoteData;
import com.kexin.admin.mapper.LoginUserMapper;
import com.kexin.admin.mapper.OperationLogMapper;
import com.kexin.admin.mapper.OperatorMapper;
import com.kexin.admin.mapper.QaInspectMasterMapper;
import com.kexin.admin.service.ProduceLogService;
import com.kexin.admin.service.QaInspectMasterService;
import com.kexin.admin.service.SystemLogService;
import com.kexin.common.util.DateUtil.TodayUtil;
import com.kexin.common.util.ResponseEty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Autowired
    SystemLogService systemLogService;//系统日志记录service

    @Autowired
    ProduceLogService produceLogService;//生产日志service
    @Override
    public ResponseEty getCanAuditInspectMaster(QueryDate queryDate) {
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        if (StringUtils.isNotEmpty(queryDate.getCartNumber())){
            queryDate.setCartNumber(queryDate.getCartNumber().toUpperCase());
        }
        responseEty.setAny("canAuditTable",baseMapper.getCanAuditInspectMaster(queryDate.getStartDate(),queryDate.getEndDate(),queryDate.getCartNumber()));
        return responseEty;
    }
    //新增和编辑加上,事务回滚时用到
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEty saveCanAuditInspectMaster(SaveCheckData saveCheckData,Integer token) {
        QueryWrapper<QaInspectMaster> qaInspectMasterQueryWrapper=new QueryWrapper<>();

        qaInspectMasterQueryWrapper.in("INSPECTM_ID",saveCheckData.getData());
        List<QaInspectMaster> qaInspectMasterList=baseMapper.selectList(qaInspectMasterQueryWrapper);
        qaInspectMasterList.forEach(r->{
            r.setAllowJudge(1);
            r.setAutoCheckFlag(2);
            r.setCheckDate(new Date());
            if (StringUtils.isNotEmpty(saveCheckData.getNote())){
                r.setNote(saveCheckData.getNote());
            }
            baseMapper.updateById(r);
        });

        StringBuffer note = new StringBuffer();
        List<QaInspectMaster> qaInspectMasterList1=baseMapper.selectQaInspectMasterByInspectmIds(saveCheckData.getData());
        qaInspectMasterList1.forEach(r->{
            note.append(" "+r.getWipJobs().getCartNumber());
            produceLogService.saveProduceLog(r,token,"核查审核","审核了车号为:"+r.getWipJobs().getCartNumber()+"的车次");
        });


            return ResponseEty.success("审核成功");

    }

    @Override
    public ResponseEty getAlreadyAuditInspectMaster(QueryDate queryDate) {
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        List<QaInspectMaster> qaInspectMasterList=new ArrayList<>();
        if (StringUtils.isNotEmpty(queryDate.getCartNumber())){
            queryDate.setCartNumber(queryDate.getCartNumber().toUpperCase());
        }
        if (queryDate.getAllowJudge()==null){
            if (queryDate.getStartDate()==null){
                qaInspectMasterList=baseMapper.getAlreadyAuditInspectMaster(TodayUtil.get3StartTime(),TodayUtil.getEndTime(),queryDate.getCartNumber());
            }else{
                qaInspectMasterList=baseMapper.getAlreadyAuditInspectMaster(queryDate.getStartDate(),queryDate.getEndDate(),queryDate.getCartNumber());
            }
        }else{
            qaInspectMasterList=baseMapper.getAlreadyAuditInspectMasterByAllowJudge(queryDate.getStartDate(),queryDate.getEndDate(),queryDate.getCartNumber(),queryDate.getAllowJudge());
        }

        qaInspectMasterList.forEach(r->{

            if(r.getAllowJudge()==2){
                r.setDisabled(true);
            }else{
                r.setDisabled(false);
            }
        });
        responseEty.setAny("alreadyAuditTable",qaInspectMasterList);
        return responseEty;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEty saveAlreadyAuditInspectMaster(SaveCheckData saveCheckData,Integer token) {

        QueryWrapper<QaInspectMaster> qaInspectMasterQueryWrapper=new QueryWrapper<>();

        qaInspectMasterQueryWrapper.in("INSPECTM_ID",saveCheckData.getData());
        List<QaInspectMaster> qaInspectMasterList=baseMapper.selectList(qaInspectMasterQueryWrapper);
        qaInspectMasterList.forEach(r->{
            r.setAllowJudge(0);
            r.setCheckDate(new Date());
            if (StringUtils.isNotEmpty(saveCheckData.getNote())){
                r.setNote(saveCheckData.getNote());
            }
            r.setAutoCheckFlag(0);
            baseMapper.updateById(r);
        });

        StringBuffer note = new StringBuffer();
        List<QaInspectMaster> qaInspectMasterList1=baseMapper.selectQaInspectMasterByInspectmIds(saveCheckData.getData());
        qaInspectMasterList1.forEach(r->{
            note.append(" "+r.getWipJobs().getCartNumber());
            produceLogService.saveProduceLog(r,token,"核查审核","回退了车号为:"+r.getWipJobs().getCartNumber()+"的车次");
        });
        return ResponseEty.success("回退成功");

    }

 /*   @Override
    public ResponseEty getNotAuditInspectMaster(QueryDate queryDate) {
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        List<QaInspectMaster> qaInspectMasterList=new ArrayList<>();
        if (queryDate.getStartDate()==null){
            qaInspectMasterList=baseMapper.getNotAuditInspectMaster(TodayUtil.getStartTime(),TodayUtil.getEndTime());
        }else{
            qaInspectMasterList=baseMapper.getNotAuditInspectMaster(queryDate.getStartDate(),queryDate.getEndDate());
        }
        responseEty.setAny("notAuditTable",qaInspectMasterList);
        return responseEty;
    }*/
 @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEty saveNotAuditInspectMaster(SaveCheckData saveCheckData,Integer token) {
        QueryWrapper<QaInspectMaster> qaInspectMasterQueryWrapper=new QueryWrapper<>();
        QaInspectMaster qaInspectMaster=new QaInspectMaster();
        qaInspectMaster.setAllowJudge(-1);


        qaInspectMaster.setCheckDate(new Date());
        qaInspectMasterQueryWrapper.in("INSPECTM_ID",saveCheckData.getData());
        if (saveCheckData.getNote()!=null){
            qaInspectMaster.setNote(saveCheckData.getNote());
        }
        qaInspectMaster.setAutoCheckFlag(2);
        int flag=baseMapper.update(qaInspectMaster,qaInspectMasterQueryWrapper);
        if (flag>0){
            List<QaInspectMaster> qaInspectMasterList=baseMapper.selectQaInspectMasterByInspectmIds(saveCheckData.getData());
            StringBuffer note = new StringBuffer();
            qaInspectMasterList.forEach(r->{
                note.append(" "+r.getWipJobs().getCartNumber());
                produceLogService.saveProduceLog(r,token,"核查审核","审核全检了车号为:"+r.getWipJobs().getCartNumber()+"的车次");
            });
            return ResponseEty.success("审核全检成功");
        }
        return ResponseEty.failure("审核全检失败");
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEty saveNoteInspectMaster(SaveNoteData saveNoteData, Integer token) {

        QueryWrapper<QaInspectMaster> qaInspectMasterQueryWrapper=new QueryWrapper<>();
        QaInspectMaster qaInspectMaster=new QaInspectMaster();
        qaInspectMaster.setInspectmId(saveNoteData.getData());
        qaInspectMaster.setNote(saveNoteData.getNote());
//        qaInspectMasterQueryWrapper.eq("INSPECTM_ID",saveNoteData.getData());
//        int flag=baseMapper.update(qaInspectMaster,qaInspectMasterQueryWrapper);
        int flag=baseMapper.updateById(qaInspectMaster);
        if (flag>0){
//            QaInspectMaster qaInspectMaster1=baseMapper.selectQaInspectMasterByInspectmId(saveNoteData.getData());
//            produceLogService.saveProduceLog(qaInspectMaster1,token,"核查审核","修改了车号为:"+qaInspectMaster1.getWipJobs().getCartNumber()+"的车次的备注:"+qaInspectMaster1.getNote());
            return ResponseEty.success("备注成功");
        }else{
            return ResponseEty.success("备注失败");
        }
    }

/*
    @Override
    public ResponseEty returnNotAuditInspectMaster(SaveCheckData saveCheckData,Integer token) {
        QueryWrapper<QaInspectMaster> qaInspectMasterQueryWrapper=new QueryWrapper<>();
        QaInspectMaster qaInspectMaster=new QaInspectMaster();
        qaInspectMaster.setAllowJudge(0);
        qaInspectMasterQueryWrapper.in("INSPECTM_ID",saveCheckData.getData());
        if (saveCheckData.getNote()!=null){
            qaInspectMaster.setNote(saveCheckData.getNote());
        }
        int flag=baseMapper.update(qaInspectMaster,qaInspectMasterQueryWrapper);
        if (flag>0){
            List<QaInspectMaster> qaInspectMasterList=baseMapper.selectQaInspectMasterByInspectmIds(saveCheckData.getData());
            StringBuffer note = new StringBuffer();
            qaInspectMasterList.forEach(r->{
                note.append(" "+r.getWipJobs().getCartNumber());
            });
            systemLogService.saveMachineLog(token,"审核","审核全检回退了车号为:"+note+"的车次");
            return ResponseEty.success("全检回退成功");
        }
        return ResponseEty.failure("全检回退失败");
    }
*/


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
//        LoginUser loginUser=loginUserMapper.selectById(inspectChange.getTokenId());
        Operator operator=operatorMapper.selectById(inspectChange.getTokenId());
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


}
