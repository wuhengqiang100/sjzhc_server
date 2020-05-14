package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.*;
import com.kexin.admin.entity.vo.webQuery.SelectOption;
import com.kexin.admin.mapper.*;
import com.kexin.admin.service.MachineCheckQueryService;
import com.kexin.common.util.ResponseEty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class MachineCheckQueryServiceImpl extends ServiceImpl<MachineCheckQueryMapper, MachineCheckQuery> implements MachineCheckQueryService {



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


    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)

    @Override
    public ResponseEty getQuerySelectOption() {
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("productOption",getProductSelectOption());
        responseEty.setAny("operationOption",getOperationSelectOption());
        responseEty.setAny("machineOption",getMachineSelectOption());
        responseEty.setAny("dicWorkUnitOption",getWorkUnitSelectOption());
        responseEty.setAny("cartNumfirstOption",getWorkUnitSelectOption());

        return responseEty;
    }


    /**
     * 获取产品的selectOption
     * @return
     */
    private List<SelectOption> getProductSelectOption(){
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
     * 获取工序的selectOption
     * @return
     */
    private List<SelectOption> getOperationSelectOption(){
        QueryWrapper<Operation> operationQueryWrapper = new QueryWrapper<>();
        operationQueryWrapper.eq("USE_FLAG",1);//启用状态
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
    private List<SelectOption> getMachineSelectOption(){
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
    private List<SelectOption> getWorkUnitSelectOption(){
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
    private List<SelectOption> getCartNumFirstSelectOption(){
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
    @Override
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



}
