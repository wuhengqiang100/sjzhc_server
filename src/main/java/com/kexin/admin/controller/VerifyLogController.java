package com.kexin.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.*;
import com.kexin.admin.entity.vo.query.QaInspectSelect;
import com.kexin.admin.entity.vo.query.QueryDateParent;
import com.kexin.admin.service.*;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.base.Data;
import com.kexin.common.base.PageDataBase;
import com.kexin.common.util.ResponseEty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:核查日志查询controller
 * @Author: 巫恒强
 * @Date: 2020/1/3 9:24
 */
@Controller
@RequestMapping("log")
public class VerifyLogController {

    @Autowired
    DataupLogService dataupLogService;


    @Autowired
    MachineLogService machineLogService;

    @Autowired
    OperationLogService operationLogService;

    @Autowired
    ProduceLogService produceLogService;

    @Autowired
    MachineCheckQueryService machineCheckQueryService;//核查综合查询service


    @Autowired
    ViewProduceLogService viewProduceLogService;//生产日志查询视图service

    @Autowired
    OperatorService operatorService;
    /**
     * @Title:
     * @Description: TODO(上传日志查询)
     * @param @param
     * @return @return
     * @author 13015
     * @throws
     * @date 2020/3/10 10:04
     */
    @PostMapping("dataup")
    @ResponseBody
    @SysLog("上传日志查询list")
    public PageDataBase<DataupLog> listDataUpLog(@RequestBody QueryDateParent query){
        PageDataBase<DataupLog> dataupLogPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<DataupLog> dataupLogWrapper = new QueryWrapper<>();
        if (query.getSort().equals("+id")){
            dataupLogWrapper.orderByAsc("DATAUP_SET_ID");
        }else{
            dataupLogWrapper.orderByDesc("DATAUP_SET_ID");
        }
        //增加根据用户查询的操作
        //增加根据时间查询的操作
        if (query.getStartDate()!=null && query.getEndDate()!=null ){//根据车台名称查询
            dataupLogWrapper.between("DATAUP_SET_DATE",  query.getStartDate(), query.getEndDate());
        }
        IPage<DataupLog> dataupLogPage = dataupLogService.page(new Page<>(query.getPage(),query.getLimit()),dataupLogWrapper);
        data.setTotal(dataupLogPage.getTotal());
        data.setItems(dataupLogPage.getRecords());
        dataupLogPageData.setData(data);
        return dataupLogPageData;
    }

    /**
     * @Title:
     * @Description: TODO(设备日志查询)
     * @param @param
     * @return @return
     * @author 13015
     * @throws
     * @date 2020/3/10 14:38
     */
    @PostMapping("machine")
    @ResponseBody
    @SysLog("设备日志查询")
    public PageDataBase<MachineLog> listMachineLog(@RequestBody QueryDateParent query){
        PageDataBase<MachineLog> machineLogPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<MachineLog> machineLogWrapper = new QueryWrapper<>();
        if (query.getSort().equals("+id")){
            machineLogWrapper.orderByAsc("LOG_MACHINE_ID");
        }else{
            machineLogWrapper.orderByDesc("LOG_MACHINE_ID");
        }
        //增加根据用户查询的操作
        //增加根据时间查询的操作
        if (query.getStartDate()!=null && query.getEndDate()!=null ){//根据车台名称查询
            machineLogWrapper.between("LOG_DATE",  query.getStartDate(), query.getEndDate());
        }
        IPage<MachineLog> machineLogPage = machineLogService.page(new Page<>(query.getPage(),query.getLimit()),machineLogWrapper);
        data.setTotal(machineLogPage.getTotal());
        machineLogPage.getRecords().forEach(r->r.setOperator(operatorService.getById(r.getOperatorId())));
        data.setItems(machineLogPage.getRecords());
        machineLogPageData.setData(data);
        return machineLogPageData;
    }

    /**
     * @Title:
     * @Description: TODO(操作日志查询)
     * @param @param
     * @return @return
     * @author 13015
     * @throws
     * @date 2020/3/10 14:39
     */
    @PostMapping("operation")
    @ResponseBody
    @SysLog("操作日志查询")
    public PageDataBase<OperationLog> listOperationLog(@RequestBody QueryDateParent query){
        PageDataBase<OperationLog> operationLogPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<OperationLog> operationLogWrapper = new QueryWrapper<>();
        if (query.getSort().equals("+id")){
            operationLogWrapper.orderByAsc("LOG_OPERATION_NOTE_ID");
        }else{
            operationLogWrapper.orderByDesc("LOG_OPERATION_NOTE_ID");
        }
        if (StringUtils.isNotEmpty(query.getTitle())){
            operationLogWrapper.like("CART_NUMBER",query.getTitle());
        }
        //增加根据用户查询的操作
        //增加根据时间查询的操作
        if (query.getStartDate()!=null && query.getEndDate()!=null ){//根据车台名称查询
            operationLogWrapper.between("START_DATE",  query.getStartDate(), query.getEndDate());
        }
        IPage<OperationLog> operationLogPage = operationLogService.page(new Page<>(query.getPage(),query.getLimit()),operationLogWrapper);
        data.setTotal(operationLogPage.getTotal());
        data.setItems(operationLogPage.getRecords());
        operationLogPageData.setData(data);
        return operationLogPageData;
    }
    @PostMapping("produce")
    @ResponseBody
    @SysLog("生产日志查询")
    public PageDataBase<ViewProduceLog> listProduceLog(@RequestBody QaInspectSelect qaSelect){
        PageDataBase<ViewProduceLog> produceLogPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<ViewProduceLog> produceLogWrapper = new QueryWrapper<>();
        if (qaSelect.getSort().equals("+id")){
            produceLogWrapper.orderByAsc("LOG_PROD_ID");
        }else{
            produceLogWrapper.orderByDesc("LOG_PROD_ID");
        }
        if (qaSelect.getTitle()!=null){
            produceLogWrapper.like("CART_NUMBER",qaSelect.getTitle());
        }if (StringUtils.isNotEmpty(qaSelect.getProductName())){//根据产品名称查询
            produceLogWrapper.like("PRODUCT_NAME",qaSelect.getProductName());
        }if (StringUtils.isNotEmpty(qaSelect.getOperationName())){//根据工序名称查询
            produceLogWrapper.like("OPERATION_NAME",qaSelect.getOperationName());
        }
        if (qaSelect.getStartDate()!=null && qaSelect.getEndDate()!=null ){//根据车台名称查询
            produceLogWrapper.between("LOG_DATE",  qaSelect.getStartDate(), qaSelect.getEndDate());
        }
        IPage<ViewProduceLog> produceLogPage = viewProduceLogService.page(new Page<>(qaSelect.getPage(),qaSelect.getLimit()),produceLogWrapper);
        data.setTotal(produceLogPage.getTotal());
        data.setItems(produceLogPage.getRecords());
        produceLogPageData.setData(data);
        return produceLogPageData;
    }

    @PostMapping("CheckQuery")
    @ResponseBody
    @SysLog("生产综合查询")
    public PageDataBase<MachineCheckQuery> listCheckQuery(@RequestBody QaInspectSelect qaSelect){
        PageDataBase<MachineCheckQuery> machineCheckQueryPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<MachineCheckQuery> machineCheckQueryWrapper = new QueryWrapper<>();
        if (qaSelect.getSort()!=null){
            if (qaSelect.getSort().equals("+id")){
                machineCheckQueryWrapper.orderByAsc("LOG_ID");
            }else{
                machineCheckQueryWrapper.orderByDesc("LOG_ID");
            }
        }
        if (qaSelect.getCartNumber()!=null){//根据车号查询
            machineCheckQueryWrapper.like("CART_NUMBER",qaSelect.getCartNumber());
        } if (StringUtils.isNotEmpty(qaSelect.getProductName())){//根据产品名称查询
            machineCheckQueryWrapper.like("PRODUCT_NAME",qaSelect.getProductName());
        }if (StringUtils.isNotEmpty(qaSelect.getOperationName())){//根据工序名称查询
            machineCheckQueryWrapper.like("OPERATION_NAME",qaSelect.getOperationName());
        }if (StringUtils.isNotEmpty(qaSelect.getMachineName())){//根据设备名称查询
            machineCheckQueryWrapper.like("MACHINE_NAME",qaSelect.getMachineName());
        }if (StringUtils.isNotEmpty(qaSelect.getWorkUnitName())){//根据车台名称查询
            machineCheckQueryWrapper.like("WORK_UNIT_NAME",qaSelect.getWorkUnitName());
        }if (qaSelect.getStartDate()!=null && qaSelect.getEndDate()!=null ){//根据车台名称查询
            machineCheckQueryWrapper.between("START_DATE",  qaSelect.getStartDate(), qaSelect.getEndDate());
        }
        IPage<MachineCheckQuery> machineCheckQueryPage = machineCheckQueryService.page(new Page<>(qaSelect.getPage(),qaSelect.getLimit()),machineCheckQueryWrapper);
        data.setTotal(machineCheckQueryPage.getTotal());
        data.setItems(machineCheckQueryPage.getRecords());
        machineCheckQueryPageData.setData(data);
        return machineCheckQueryPageData;
    }

    @PostMapping("listOption")
    @ResponseBody
    @SysLog("获取查询页面的select条件")
    public ResponseEty listOption(){
        return machineCheckQueryService.getQuerySelectOption();
    }
}
