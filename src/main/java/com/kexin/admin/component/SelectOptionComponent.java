package com.kexin.admin.component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kexin.admin.entity.tables.*;
import com.kexin.admin.entity.vo.AuditParameter.ParameterByIds;
import com.kexin.admin.entity.vo.webQuery.SelectOption;
import com.kexin.admin.entity.vo.webQuery.SelectOptionValue;
import com.kexin.admin.mapper.*;
import com.kexin.admin.service.MachineService;
import com.kexin.common.util.ResponseEty;
import com.kexin.common.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询组件,各种查询条件
 */
@Component
public class SelectOptionComponent {

    @Resource
    ProductsMapper productsMapper;//产品mapper

    @Resource
    OperationMapper operationMapper;//工序mapper

    @Resource
    MachineMapper machineMapper;//设备mapper

    @Resource
    DicWorkUnitsMapper dicWorkUnitsMapper;//机台mapper

    @Resource
    OperatorMapper operatorMapper;//人员mapper

    @Resource
    CartNumFirstMapper cartNumFirstMapper;//车号首字母mapper

    @Resource
    RoleMapper roleMapper;//角色mapper

    @Resource
    AuditParameterTypeMapper auditParameterTypeMapper;//审核参数类型mapper

    @Resource
    AuditParameterMapper auditParameterMapper;//审核参数mapper


    /**
     * 获取产品的selectOption
     * @return
     */
    public List<SelectOption> getProductSelectOption(){
        QueryWrapper<Products> productsQueryWrapper = new QueryWrapper<>();
        productsQueryWrapper.eq("USE_FLAG",1);//启用状态的产品
        List<Products> productsList= productsMapper.selectList(productsQueryWrapper);
        List<SelectOption> selectOptionList=new ArrayList<>(productsList.size());
        SelectOption selectOption;
        for (Products r : productsList) {
            selectOption=new SelectOption();
            selectOption.setValue(r.getProductId());
            selectOption.setLabel(r.getProductName());
            if (r.getUseFlag()) {
                selectOption.setDisabled(true);
            } else {
                selectOption.setDisabled(false);
            }
            selectOptionList.add(selectOption);
        }
        return selectOptionList;
    }

    /**
     * 获取工序的selectOption，生产工序
     * @return
     */
    public List<SelectOption> getOperationSelectOption(){
        QueryWrapper<Operation> operationQueryWrapper = new QueryWrapper<>();
        operationQueryWrapper.eq("USE_FLAG",1)//启用状态
        .eq("OPERATION_TYPE_ID",1)//生产工序
        ;
        List<Operation> operationList= operationMapper.selectList(operationQueryWrapper);
        List<SelectOption> selectOptionList=new ArrayList<>(operationList.size());
        SelectOption selectOption;
        for (Operation r : operationList) {
            selectOption=new SelectOption();
            selectOption.setValue(r.getOperationId());
            selectOption.setLabel(r.getOperationName());
            if (r.getUseFlag()) {
                selectOption.setDisabled(true);
            } else {
                selectOption.setDisabled(false);
            }
            selectOptionList.add(selectOption);
        }
        return selectOptionList;
    }

    /**
     * 获取设备的selectOption
     * @return
     */
    public List<SelectOption> getMachineSelectOption(){
        QueryWrapper<Machine> machineQueryWrapper = new QueryWrapper<>();
        machineQueryWrapper.eq("USE_FLAG",1);//启用状态
        List<Machine> machineList= machineMapper.selectList(machineQueryWrapper);
        List<SelectOption> selectOptionList=new ArrayList<>(machineList.size());
        SelectOption selectOption;
        for (Machine r : machineList) {
            selectOption=new SelectOption();
            selectOption.setValue(r.getMachineId());
            selectOption.setLabel(r.getMachineName());
            if (r.getUseFlag()) {
                selectOption.setDisabled(true);
            } else {
                selectOption.setDisabled(false);
            }
            selectOptionList.add(selectOption);
        }
        return selectOptionList;
    }

    /**
     * 获取机台的selectoption
     * @return
     */
    public List<SelectOption> getWorkUnitSelectOption(){
        QueryWrapper<DicWorkUnits> dicWorkUnitsQueryWrapper = new QueryWrapper<>();
        dicWorkUnitsQueryWrapper.eq("USE_FLAG",1);//启用状态
        List<DicWorkUnits> dicWorkUnitsList= dicWorkUnitsMapper.selectList(dicWorkUnitsQueryWrapper);
        List<SelectOption> selectOptionList=new ArrayList<>(dicWorkUnitsList.size());
        SelectOption selectOption;
        for (DicWorkUnits r : dicWorkUnitsList) {
            selectOption=new SelectOption();
            selectOption.setValue(r.getWorkUnitId());
            selectOption.setLabel(r.getWorkUnitName());
            selectOptionList.add(selectOption);
        }
        return selectOptionList;
    }
    /**
     * 获取车号首字母的selectoption
     * @return
     */
    public List<SelectOption> getCartNumFirstSelectOption(){
        QueryWrapper<CartNumFirst> cartNumFirstQueryWrapper = new QueryWrapper<>();
        List<CartNumFirst> cartNumFirstList= cartNumFirstMapper.selectList(cartNumFirstQueryWrapper);
        List<SelectOption> selectOptionList=new ArrayList<>(cartNumFirstList.size());
        SelectOption selectOption;
        for (CartNumFirst r : cartNumFirstList) {
            selectOption=new SelectOption();
            selectOption.setValue(r.getNumId());
            selectOption.setLabel(r.getNumCode());
            selectOptionList.add(selectOption);
        }
        return selectOptionList;
    }

    /**
     * 获取用户的selectoption
     * @return
     */

    public List<SelectOption> getOperatorSelectOption(){
        QueryWrapper<Operator> operatorQueryWrapper = new QueryWrapper<>();
        operatorQueryWrapper.eq("USE_FLAG",1);//启用状态
        List<Operator> operatorList= operatorMapper.selectList(operatorQueryWrapper);
        List<SelectOption> selectOptionList=new ArrayList<>(operatorList.size());
        SelectOption selectOption;
        for (Operator r : operatorList) {
            selectOption=new SelectOption();
            selectOption.setValue(r.getOperatorId());
            selectOption.setLabel(r.getOperatorName());
            selectOptionList.add(selectOption);
        }
        return selectOptionList;
    }

    /**
     * 获取角色的selectOption
     * @return
     */
    public List<SelectOption> getRoleSelectOption(){
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("USE_FLAG",1);//启用状态
        List<Role> roleList= roleMapper.selectList(roleQueryWrapper);
        List<SelectOption> selectOptionList=new ArrayList<>(roleList.size());
        SelectOption selectOption;
        for (Role r : roleList) {
            selectOption=new SelectOption();
            selectOption.setValue(r.getRoleId());
            selectOption.setLabel(r.getRoleName());
            selectOptionList.add(selectOption);
        }
        return selectOptionList;
    }

    /**
     * 获取审核参数类型的selectOption
     * @return
     */
    public List<SelectOption> getAuditParameterTypeSelectOption(){
        QueryWrapper<AuditParameterType> auditParameterTypeQueryWrapper = new QueryWrapper<>();
        auditParameterTypeQueryWrapper.eq("USE_FLAG",1);//启用状态
        List<AuditParameterType> auditParameterTypeList= auditParameterTypeMapper.selectList(auditParameterTypeQueryWrapper);
        List<SelectOption> selectOptionList=new ArrayList<>(auditParameterTypeList.size());
        SelectOption selectOption;
        for (AuditParameterType r : auditParameterTypeList) {
            selectOption=new SelectOption();
            selectOption.setValue(r.getJudgeCheckTypeId());
            selectOption.setLabel(r.getJudgeCheckTypeName());
            selectOptionList.add(selectOption);
        }
        return selectOptionList;
    }


    /**
     * 获取审核参数类型的selectOption,根据工序id,产品id,设备id
     * @return
     */
    public List<SelectOptionValue> getAuditParameterTypeSelectOptionByIds(ParameterByIds parameterByIds){
        QueryWrapper<AuditParameterType> auditParameterTypeQueryWrapper = new QueryWrapper<>();
        auditParameterTypeQueryWrapper.eq("USE_FLAG",1);//启用状态
        List<AuditParameterType> auditParameterTypeList= auditParameterTypeMapper.selectList(auditParameterTypeQueryWrapper);



        List<SelectOptionValue> selectOptionList=new ArrayList<>(auditParameterTypeList.size());
        SelectOptionValue selectOption;
        for (AuditParameterType r : auditParameterTypeList) {
            selectOption=new SelectOptionValue();
            selectOption.setValue(r.getJudgeCheckTypeId());
            selectOption.setLabel(r.getJudgeCheckTypeName());
            QueryWrapper<AuditParameter> auditParameterQueryWrapper = new QueryWrapper<>();
            auditParameterQueryWrapper.eq("OPERATION_ID",parameterByIds.getOperationId());
            auditParameterQueryWrapper.eq("PRODUCT_ID",parameterByIds.getProductId());
            auditParameterQueryWrapper.eq("MACHINE_ID",parameterByIds.getMachineId());
            auditParameterQueryWrapper.eq("JUDGE_CHECK_TYPE_ID",r.getJudgeCheckTypeId());
            AuditParameter auditParameter= auditParameterMapper.selectOne(auditParameterQueryWrapper);
            if (auditParameter!=null){
                selectOption.setValueData(auditParameter.getValue());
            }else{
                selectOption.setValueData(0);
            }
            selectOptionList.add(selectOption);
        }
        return selectOptionList;
    }

    /**
     * 获取审核参数的selectOption
     * @return
     */
/*    public List<SelectOption> getAuditParameterSelectOption(){
        QueryWrapper<AuditParameter> auditParameterQueryWrapper = new QueryWrapper<>();
        auditParameterQueryWrapper.eq("USE_FLAG",1);//启用状态
        List<AuditParameter> auditParameterTypeList= auditParameterMapper.selectList(auditParameterQueryWrapper);
        List<SelectOption> selectOptionList=new ArrayList<>(auditParameterTypeList.size());
        SelectOption selectOption;
        for (AuditParameter r : auditParameterTypeList) {
            selectOption=new SelectOption();
            selectOption.setValue(r.getJudgeCheckId());
            selectOption.setLabel(r.get());
            selectOptionList.add(selectOption);
        }
        return selectOptionList;
    }*/


}
