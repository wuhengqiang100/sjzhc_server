package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    public ResponseEty getCanAuditInspectMaster() {
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("canAuditTable",baseMapper.getCanAuditInspectMaster());
        return responseEty;
    }

    @Override
    public ResponseEty saveCanAuditInspectMaster(Integer[] inspectmIds) {
        QueryWrapper<QaInspectMaster> qaInspectMasterQueryWrapper=new QueryWrapper<>();
        QaInspectMaster qaInspectMaster=new QaInspectMaster();
        qaInspectMaster.setAllowJudge(1);
        qaInspectMasterQueryWrapper.in("INSPECTM_ID",inspectmIds);
        int flag=baseMapper.update(qaInspectMaster,qaInspectMasterQueryWrapper);
        if (flag>0){
            return ResponseEty.success("审核成功");
        }
        return ResponseEty.failure("审核失败");
    }

    @Override
    public ResponseEty getAlreadyAuditInspectMaster() {
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("alreadyAuditTable",baseMapper.getAlreadyAuditInspectMaster(TodayUtil.getStartTime(),TodayUtil.getEndTime()));
        return responseEty;
    }

    @Override
    public ResponseEty saveAlreadyAuditInspectMaster(Integer[] inspectmIds) {
        QueryWrapper<QaInspectMaster> qaInspectMasterQueryWrapper=new QueryWrapper<>();
        QaInspectMaster qaInspectMaster=new QaInspectMaster();
        qaInspectMaster.setAllowJudge(0);
        qaInspectMasterQueryWrapper.in("INSPECTM_ID",inspectmIds);
        int flag=baseMapper.update(qaInspectMaster,qaInspectMasterQueryWrapper);
        if (flag>0){
            return ResponseEty.success("回退成功");
        }
        return ResponseEty.failure("回退失败");
    }

    @Override
    public ResponseEty getNotAuditInspectMaster() {
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("notAuditTable",baseMapper.getNotAuditInspectMaster(TodayUtil.getStartTime(),TodayUtil.getEndTime()));
        return responseEty;
    }

    @Override
    public ResponseEty saveNotAuditInspectMaster(Integer[] inspectmIds) {
        QueryWrapper<QaInspectMaster> qaInspectMasterQueryWrapper=new QueryWrapper<>();
        QaInspectMaster qaInspectMaster=new QaInspectMaster();
        qaInspectMaster.setAllowJudge(-1);
        qaInspectMasterQueryWrapper.in("INSPECTM_ID",inspectmIds);
        int flag=baseMapper.update(qaInspectMaster,qaInspectMasterQueryWrapper);
        if (flag>0){
            return ResponseEty.success("废弃成功");
        }
        return ResponseEty.failure("废弃失败");
    }

    @Override
    public ResponseEty returnNotAuditInspectMaster(Integer[] inspectmIds) {
        QueryWrapper<QaInspectMaster> qaInspectMasterQueryWrapper=new QueryWrapper<>();
        QaInspectMaster qaInspectMaster=new QaInspectMaster();
        qaInspectMaster.setAllowJudge(0);
        qaInspectMasterQueryWrapper.in("INSPECTM_ID",inspectmIds);
        int flag=baseMapper.update(qaInspectMaster,qaInspectMasterQueryWrapper);
        if (flag>0){
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
