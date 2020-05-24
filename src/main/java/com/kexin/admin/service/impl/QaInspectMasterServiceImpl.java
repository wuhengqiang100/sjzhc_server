package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.OperationLog;
import com.kexin.admin.entity.tables.Operator;
import com.kexin.admin.entity.tables.QaInspectMaster;
import com.kexin.admin.entity.vo.QaInspectChange;
import com.kexin.admin.entity.vo.query.QueryDate;
import com.kexin.admin.entity.vo.query.SaveCheckData;
import com.kexin.admin.mapper.LoginUserMapper;
import com.kexin.admin.mapper.OperationLogMapper;
import com.kexin.admin.mapper.OperatorMapper;
import com.kexin.admin.mapper.QaInspectMasterMapper;
import com.kexin.admin.service.QaInspectMasterService;
import com.kexin.admin.service.SystemLogService;
import com.kexin.common.util.DateUtil.TodayUtil;
import com.kexin.common.util.ResponseEty;
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
    @Override
    public ResponseEty getCanAuditInspectMaster(QueryDate queryDate) {
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("canAuditTable",baseMapper.getCanAuditInspectMaster(queryDate.getStartDate(),queryDate.getEndDate()));
        return responseEty;
    }

    @Override
    public ResponseEty saveCanAuditInspectMaster(SaveCheckData saveCheckData,Integer token) {
        QueryWrapper<QaInspectMaster> qaInspectMasterQueryWrapper=new QueryWrapper<>();
        QaInspectMaster qaInspectMaster=new QaInspectMaster();
        qaInspectMaster.setAllowJudge(1);
        qaInspectMasterQueryWrapper.in("INSPECTM_ID",saveCheckData.getData());
        if (saveCheckData.getNote()!=null){
            qaInspectMaster.setNote(saveCheckData.getNote());
        }
        int flag=baseMapper.update(qaInspectMaster,qaInspectMasterQueryWrapper);
        List<QaInspectMaster> qaInspectMasterList=baseMapper.selectQaInspectMasterByInspectmIds(saveCheckData.getData());
        StringBuffer note = new StringBuffer();
        qaInspectMasterList.forEach(r->{
            note.append(" "+r.getWipJobs().getCartNumber());
        });
        systemLogService.saveMachineLog(token,"审核","审核了车号为:"+note+"的车次");
        if (flag>0){
            return ResponseEty.success("审核成功");
        }
        return ResponseEty.failure("审核失败");
    }

    @Override
    public ResponseEty getAlreadyAuditInspectMaster(QueryDate queryDate) {
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        List<QaInspectMaster> qaInspectMasterList=new ArrayList<>();
        if (queryDate.getStartDate()==null){
            qaInspectMasterList=baseMapper.getAlreadyAuditInspectMaster(TodayUtil.getStartTime(),TodayUtil.getEndTime());
        }else{
            qaInspectMasterList=baseMapper.getAlreadyAuditInspectMaster(queryDate.getStartDate(),queryDate.getEndDate());
        }
        qaInspectMasterList.forEach(r->{
            if (r.getAllowJudge()==1){//已审核未分活
                r.setDisabled(false);
            }else{//已分活
                r.setDisabled(true);
            }
        });
        responseEty.setAny("alreadyAuditTable",qaInspectMasterList);
        return responseEty;
    }

    @Override
    public ResponseEty saveAlreadyAuditInspectMaster(SaveCheckData saveCheckData,Integer token) {

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
            systemLogService.saveMachineLog(token,"审核","回退了车号为:"+note+"的车次");
            return ResponseEty.success("回退成功");
        }
        return ResponseEty.failure("回退失败");
    }

    @Override
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
    }

    @Override
    public ResponseEty saveNotAuditInspectMaster(SaveCheckData saveCheckData,Integer token) {
        QueryWrapper<QaInspectMaster> qaInspectMasterQueryWrapper=new QueryWrapper<>();
        QaInspectMaster qaInspectMaster=new QaInspectMaster();
        qaInspectMaster.setAllowJudge(-1);
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
            systemLogService.saveMachineLog(token,"审核","审核全检了车号为:"+note+"的车次");
            return ResponseEty.success("审核全检成功");
        }
        return ResponseEty.failure("审核全检失败");
    }

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
