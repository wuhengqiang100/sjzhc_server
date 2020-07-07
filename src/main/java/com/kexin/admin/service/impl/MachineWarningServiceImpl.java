package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.MachineWarning;
import com.kexin.admin.mapper.MachineMapper;
import com.kexin.admin.mapper.MachineWarningMapper;
import com.kexin.admin.service.MachineWarningService;
import com.kexin.common.util.ResponseEty;
import com.kexin.common.util.constantEnum.ConstantEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 设备报警配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MachineWarningServiceImpl extends ServiceImpl<MachineWarningMapper, MachineWarning> implements MachineWarningService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)

    @Resource
    MachineMapper machineMapper;

    @Override
    public ResponseEty listNotDeal() {
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        QueryWrapper<MachineWarning> machineWarningQueryWrapper=new QueryWrapper<>();
        machineWarningQueryWrapper
                .eq("LOG_STATE", ConstantEnum.NOT_DEAL_STATE)
                .orderByDesc("LOG_DATE");
        List<MachineWarning> machineWarningList=baseMapper.selectList(machineWarningQueryWrapper);
        machineWarningList.forEach(r-> r.setMachine(machineMapper.selectById(r.getMachineId())));
        String[][] warnings=new String[machineWarningList.size()][];

        for (int i = 0; i <machineWarningList.size() ; i++) {
            String[] detail=new String[2];
            detail[0]=machineWarningList.get(i).getMachine().getMachineName();
            detail[1]=machineWarningList.get(i).getNote();
            warnings[i]=detail;
        }
        responseEty.setAny("warnings",warnings);
        responseEty.setAny("machineWarningList",machineWarningList);
        return responseEty;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMachineWarning(MachineWarning machineWarning) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
//        if (machineWarning.getUseFlag()){//启用
//            machineWarning.setStartDate(new Date());
//            machineWarning.setEndDate(null);
//        }else{//禁用
//            machineWarning.setEndDate(new Date());
//        }
        baseMapper.insert(machineWarning);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMachineWarning(MachineWarning machineWarning) {
//        dropUserRolesByUserId(user.getLoginId());
//        if (machineWarning.getUseFlag()){//启用
//            machineWarning.setStartDate(new Date());
//            machineWarning.setEndDate(null);
//        }else{//禁用
//            machineWarning.setEndDate(new Date());
//        }
        machineWarning.setLogState(1);//设置处理完成
        baseMapper.updateById(machineWarning);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMachineWarning(MachineWarning machineWarning) {
        baseMapper.deleteById(machineWarning.getLogId());
    }

    /**
     * 处理报警信息,设置状态为已处理
     * @param machineWarning
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dealMachineWarning(MachineWarning machineWarning) {
        machineWarning.setLogState(ConstantEnum.DEAL_STATE);//设置处理完成
        machineWarning.setDealDate(new Date());
        baseMapper.updateById(machineWarning);
    }
}
