package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.*;
import com.kexin.admin.entity.vo.AuditParameter.AuditParameterDelete;
import com.kexin.admin.entity.vo.AuditParameter.AuditParameterDetail;
import com.kexin.admin.mapper.*;
import com.kexin.admin.service.AuditParameterService;
import com.kexin.common.util.ResponseEty;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工序配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AuditParameterServiceImpl extends ServiceImpl<AuditParameterMapper, AuditParameter> implements AuditParameterService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)

    @Resource
    AuditParameterTypeMapper auditParameterTypeMapper;//参数种类mapper

    @Resource
    OperationMapper operationMapper;//工序mapper

    @Resource
    ProductsMapper productsMapper;//产品mapper
    @Resource
    MachineMapper machineMapper;//设备mapper



    @Override
    public List<Map<String, Object>> getAuditParameterSecond() {
        String sqlQuery= baseMapper.getAuditParameterFirst();
        List<Map<String, Object>> mapList=baseMapper.getAuditParameterSecond(sqlQuery);
        return mapList;
    }

    @Override
    public List<AuditParameterDetail> getAuditParameterDetail(AuditParameter auditParameter) {
        //获取所有的参数
        QueryWrapper<AuditParameter> auditParameterQueryWrapper=new QueryWrapper<>();
        auditParameterQueryWrapper.eq("OPERATION_ID",auditParameter.getOperationId());
        auditParameterQueryWrapper.eq("PRODUCT_ID",auditParameter.getProductId());
        auditParameterQueryWrapper.eq("MACHINE_ID",auditParameter.getMachineId());
        List<AuditParameter> auditParameterList=baseMapper.selectList(auditParameterQueryWrapper);




        List<AuditParameterDetail> auditParameterDetailList=new ArrayList<>();
        QueryWrapper<AuditParameterType> auditParameterTypeQueryWrapper=new QueryWrapper<>();
        List<AuditParameterType> auditParameterTypeList=auditParameterTypeMapper.selectList(auditParameterTypeQueryWrapper);

        for (AuditParameter parameter:auditParameterList) {
            //获取的参数类型数据
            AuditParameterDetail parameterDetail=new AuditParameterDetail();
            AuditParameterType auditParameterType = auditParameterTypeMapper.selectById(parameter.getJudgeCheckTypeId());

            auditParameterTypeList.forEach(r->{
                if (r.getJudgeCheckTypeName().equals(auditParameterType.getJudgeCheckTypeName())){
                    r.setValue(parameter.getValue());
                }
            });
        }
        for (int i = 0; i <auditParameterTypeList.size() ; i++) {
            AuditParameterDetail auditParameterDetail=new AuditParameterDetail();
            auditParameterDetail.setName(auditParameterTypeList.get(i).getJudgeCheckTypeName());
            auditParameterDetail.setValue(auditParameterTypeList.get(i).getValue());
            auditParameterDetailList.add(auditParameterDetail);
        }


        return auditParameterDetailList;
    }

    @Override
    public Integer countParameterByTypeOperationProduct(AuditParameter auditParameter) {
        QueryWrapper<AuditParameter> auditParameterQueryWrapper=new QueryWrapper<>();
        auditParameterQueryWrapper.eq("OPERATION_ID",auditParameter.getOperationId())
                .eq("PRODUCT_ID",auditParameter.getProductId())
                .eq("MACHINE_ID",auditParameter.getMachineId())
        ;
        return baseMapper.selectCount(auditParameterQueryWrapper);
    }

    @Override
    public Integer auditParameterCountByCode(String auditParameterCode) {
        QueryWrapper<AuditParameter> wrapper = new QueryWrapper<>();
        wrapper.eq("JUDGE_CHECK_CODE",auditParameterCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer auditParameterCountByName(String auditParameterName) {
        QueryWrapper<AuditParameter> wrapper = new QueryWrapper<>();
        wrapper.eq("JUDGE_CHECK_NAME",auditParameterName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAuditParameter(AuditParameter auditParameter) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        if (auditParameter.getUseFlag()){//启用
            auditParameter.setStartDate(new Date());
            auditParameter.setEndDate(null);
        }else{//禁用
            auditParameter.setEndDate(new Date());
        }
        QueryWrapper<AuditParameterType> auditParameterTypeQueryWrapper=new QueryWrapper<>();
        List<AuditParameterType> auditParameterTypeList=auditParameterTypeMapper.selectList(auditParameterTypeQueryWrapper);
        AuditParameter auditParameter1=null;
        for (int i = 0; i < auditParameterTypeList.size(); i++) {
            auditParameter1=new AuditParameter();
            auditParameter1.setOperationId(auditParameter.getOperationId());
            auditParameter1.setProductId(auditParameter.getProductId());
            auditParameter1.setMachineId(auditParameter.getMachineId());
            auditParameter1.setJudgeCheckTypeId(auditParameterTypeList.get(i).getJudgeCheckTypeId());
            auditParameter1.setValue(auditParameter.getValues()[i]);
            auditParameter1.setUseFlag(auditParameter.getUseFlag());
            auditParameter1.setStartDate(auditParameter.getStartDate());
            auditParameter1.setEndDate(auditParameter.getEndDate());
            baseMapper.insert(auditParameter1);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEty updateAuditParameter(AuditParameter auditParameter) {
//        dropUserRolesByUserId(user.getLoginId());
        if (auditParameter.getUseFlag()){//启用
            auditParameter.setStartDate(new Date());
            auditParameter.setEndDate(null);
        }else{//禁用
            auditParameter.setEndDate(new Date());
        }
        if (auditParameter.getMachineId()==null){
            QueryWrapper<AuditParameter> auditParameterQueryWrapper=new QueryWrapper<>();
            auditParameterQueryWrapper.eq("OPERATION_ID",auditParameter.getOperationId());
            auditParameterQueryWrapper.eq("PRODUCT_ID",auditParameter.getProductId());
            List<AuditParameter> auditParameterList=baseMapper.selectList(auditParameterQueryWrapper);
            auditParameterList.forEach(r->this.updateParameter(r));
            return ResponseEty.success("批量修改成功!");
        }else{
            this.updateParameter(auditParameter);
            return ResponseEty.success("修改成功!");

        }

    }

    private void updateParameter(AuditParameter auditParameter){
        QueryWrapper<AuditParameterType> auditParameterTypeQueryWrapper=new QueryWrapper<>();
        List<AuditParameterType> auditParameterTypeList=auditParameterTypeMapper.selectList(auditParameterTypeQueryWrapper);
        AuditParameter auditParameter1=null;
        for (int i = 0; i < auditParameterTypeList.size(); i++) {

            QueryWrapper<AuditParameter> auditParameterQueryWrapper=new QueryWrapper<>();

            auditParameter1=new AuditParameter();
            auditParameter1.setOperationId(auditParameter.getOperationId());
            auditParameter1.setProductId(auditParameter.getProductId());
            auditParameter1.setMachineId(auditParameter.getMachineId());
            auditParameter1.setJudgeCheckTypeId(auditParameterTypeList.get(i).getJudgeCheckTypeId());
            auditParameter1.setValue(auditParameter.getValues()[i]);
            auditParameter1.setUseFlag(auditParameter.getUseFlag());
            auditParameter1.setStartDate(auditParameter.getStartDate());
            auditParameter1.setEndDate(auditParameter.getEndDate());
            QueryWrapper<AuditParameter> auditParameterQueryWrapper1=new QueryWrapper<>();
            auditParameterQueryWrapper1.eq("OPERATION_ID",auditParameter1.getOperationId());
            auditParameterQueryWrapper1.eq("PRODUCT_ID",auditParameter1.getProductId());
            auditParameterQueryWrapper1.eq("MACHINE_ID",auditParameter1.getMachineId());
            auditParameterQueryWrapper1.eq("JUDGE_CHECK_TYPE_ID",auditParameter1.getJudgeCheckTypeId());
            baseMapper.update(auditParameter1,auditParameterQueryWrapper1);
        }
    }


    @Override
    public Integer deleteAuditParameter(AuditParameter auditParameter) {

        QueryWrapper<AuditParameter> auditParameterQueryWrapper=new QueryWrapper<>();
        auditParameterQueryWrapper.eq("OPERATION_ID",auditParameter.getOperationId())
        .eq("PRODUCT_ID",auditParameter.getProductId())
        .eq("MACHINE_ID",auditParameter.getMachineId());
        return  baseMapper.delete(auditParameterQueryWrapper);
    }

    /*@Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAuditParameter(AuditParameter auditParameter) {
        baseMapper.deleteById(auditParameter.getJudgeCheckId());
    }*/

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockAuditParameter(AuditParameter auditParameter) {
        if (auditParameter.getUseFlag()){
            auditParameter.setUseFlag(false);
            auditParameter.setEndDate(new Date());
        }else{
            auditParameter.setUseFlag(true);
            auditParameter.setEndDate(null);
        }
        baseMapper.updateById(auditParameter);
    }
}
