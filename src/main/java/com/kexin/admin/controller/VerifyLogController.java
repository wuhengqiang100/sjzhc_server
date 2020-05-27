package com.kexin.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.*;
import com.kexin.admin.entity.vo.query.QaInspectSelect;
import com.kexin.admin.entity.vo.query.QueryDateParent;
import com.kexin.admin.entity.vo.query.QueryProduceLog;
import com.kexin.admin.service.*;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.base.Data;
import com.kexin.common.base.PageDataBase;
import com.kexin.common.util.ResponseEty;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
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


//    @Autowired
//    ViewProduceLogService viewProduceLogService;//生产日志查询视图service

    @Autowired
    OperatorService operatorService;

    @Autowired
    SystemLogService systemLogService;//系统日志service

    @Autowired
    ProductsService productsService;//产品service

    @Autowired
    OperationService operationService;//工序service


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
    public PageDataBase<MachineLog> listMachineLog(@RequestBody QueryDateParent query,
                                                   @RequestHeader(value="token",required = false) Integer token){
        PageDataBase<MachineLog> machineLogPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<MachineLog> machineLogWrapper = new QueryWrapper<>();
        machineLogWrapper.orderByDesc("LOG_DATE");

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
        systemLogService.saveMachineLog(token,"查询","查询了设备日志列表");

        return machineLogPageData;
    }

    @PostMapping("system")
    @ResponseBody
    @SysLog("系统日志查询")
    public PageDataBase<SystemLog> listSystemLog(@RequestBody QueryDateParent query,@RequestHeader(value="token",required = false) Integer token){
        PageDataBase<SystemLog> systemLogPageDataBase = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<SystemLog> systemLogQueryWrapper = new QueryWrapper<>();
        systemLogQueryWrapper.orderByDesc("LOG_DATE");
        if (query.getSort().equals("+id")){
            systemLogQueryWrapper.orderByAsc("LOG_SYS_ID");
        }else{
            systemLogQueryWrapper.orderByDesc("LOG_SYS_ID");
        }
        if (StringUtils.isNotEmpty(query.getTitle())){
            systemLogQueryWrapper.like("OPERATOR_NAME",query.getTitle());
        }
        //增加根据用户查询的操作
        //增加根据时间查询的操作
        if (query.getStartDate()!=null && query.getEndDate()!=null ){//根据车台名称查询
            systemLogQueryWrapper.between("LOG_DATE",  query.getStartDate(), query.getEndDate());
        }
        IPage<SystemLog> machineLogPage = systemLogService.page(new Page<>(query.getPage(),query.getLimit()),systemLogQueryWrapper);
        data.setTotal(machineLogPage.getTotal());
//        machineLogPage.getRecords().forEach(r->r.setOperator(operatorService.getById(r.getOperatorId())));
        data.setItems(machineLogPage.getRecords());
        systemLogPageDataBase.setData(data);
        systemLogService.saveMachineLog(token,"查询","查询了系统日志列表");

        return systemLogPageDataBase;
    }

    @PostMapping("produce")
    @ResponseBody
    @SysLog("生产日志查询")
    public PageDataBase<ProduceLog> listProduceLog(@RequestBody QueryProduceLog query,@RequestHeader(value="token",required = false) Integer token){
        PageDataBase<ProduceLog> produceLogPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<ProduceLog> produceLogWrapper = new QueryWrapper<>();
        produceLogWrapper.orderByDesc("LOG_DATE");
        if (query.getSort().equals("+id")){
            produceLogWrapper.orderByAsc("LOG_PROD_ID");
        }else{
            produceLogWrapper.orderByDesc("LOG_PROD_ID");
        }
        if (query.getTitle()!=null){
            produceLogWrapper.like("CART_NUMBER",query.getTitle());
        }
        if (query.getOperationId()!=null){
            produceLogWrapper.eq("OPERATION_ID",query.getOperationId());
        }
        if (query.getProductId()!=null){
            produceLogWrapper.eq("PRODUCT_ID",query.getProductId());
        }
        if (query.getOperatorId()!=null){
            produceLogWrapper.eq("OPERATOR_ID",query.getOperatorId());
        }
        if (query.getStartDate()!=null && query.getEndDate()!=null ){//根据车台名称查询
            produceLogWrapper.between("LOG_DATE",  query.getStartDate(), query.getEndDate());
        }
        IPage<ProduceLog> produceLogPage = produceLogService.page(new Page<>(query.getPage(),query.getLimit()),produceLogWrapper);
        data.setTotal(produceLogPage.getTotal());
        produceLogPage.getRecords().forEach(r->{
            if(productsService.getById(r.getProductId())!=null)
                r.setProduct(productsService.getById(r.getProductId()));
            if (r.getOperationId()!=null)
                if (operationService.getById(r.getOperationId())!=null)
                    r.setOperation(operationService.getById(r.getOperationId()));
        });
        data.setItems(produceLogPage.getRecords());
        produceLogPageData.setData(data);
        systemLogService.saveMachineLog(token,"查询","查询了生产日志列表");

        return produceLogPageData;
    }


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
    public PageDataBase<DataupLog> listDataUpLog(@RequestBody QueryDateParent query,@RequestHeader(value="token",required = false) Integer token){
        PageDataBase<DataupLog> dataupLogPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<DataupLog> dataupLogWrapper = new QueryWrapper<>();
        dataupLogWrapper.orderByDesc("DATAUP_SET_DATE");
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
        systemLogService.saveMachineLog(token,"查询","查询了上传日志列表");

        return dataupLogPageData;
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
    public PageDataBase<OperationLog> listOperationLog(@RequestBody QueryDateParent query,@RequestHeader(value="token",required = false) Integer token){
        PageDataBase<OperationLog> operationLogPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<OperationLog> operationLogWrapper = new QueryWrapper<>();
        operationLogWrapper.orderByDesc("START_DATE");
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
        systemLogService.saveMachineLog(token,"查询","查询了操作日志列表");

        return operationLogPageData;
    }


    @PostMapping("CheckQuery")
    @ResponseBody
    @SysLog("核查审核查询")
    public PageDataBase<MachineCheckQuery> listCheckQuery(@RequestBody QaInspectSelect qaSelect,@RequestHeader(value="token",required = false) Integer token){
        PageDataBase<MachineCheckQuery> machineCheckQueryPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<MachineCheckQuery> machineCheckQueryWrapper = new QueryWrapper<>();
        machineCheckQueryWrapper.orderByDesc("START_DATE");
        if (qaSelect.getSort()!=null){
            if (qaSelect.getSort().equals("+id")){
                machineCheckQueryWrapper.orderByAsc("LOG_ID");
            }else{
                machineCheckQueryWrapper.orderByDesc("LOG_ID");
            }
        }
        if (qaSelect.getCartNumber()!=null){//根据车号查询
            machineCheckQueryWrapper.like("CART_NUMBER",qaSelect.getCartNumber());
        }if (StringUtils.isNotEmpty(qaSelect.getOperationId())){//根据工序名称查询
            machineCheckQueryWrapper.eq("OPERATION_ID",qaSelect.getOperationId());
        } if (StringUtils.isNotEmpty(qaSelect.getProductId())){//根据产品名称查询
            machineCheckQueryWrapper.eq("PRODUCT_ID",qaSelect.getProductId());
        }if (StringUtils.isNotEmpty(qaSelect.getMachineId())){//根据设备名称查询
            machineCheckQueryWrapper.eq("MACHINE_ID",qaSelect.getMachineId());
        }if (StringUtils.isNotEmpty(qaSelect.getWorkUnitId())){//根据车台名称查询
            machineCheckQueryWrapper.eq("WORK_UNIT_ID",qaSelect.getWorkUnitId());
        }if (qaSelect.getStartDate()!=null && qaSelect.getEndDate()!=null ){//根据车台名称查询
            machineCheckQueryWrapper.between("START_DATE",  qaSelect.getStartDate(), qaSelect.getEndDate());
        }
        IPage<MachineCheckQuery> machineCheckQueryPage = machineCheckQueryService.page(new Page<>(qaSelect.getPage(),qaSelect.getLimit()),machineCheckQueryWrapper);
        data.setTotal(machineCheckQueryPage.getTotal());
        data.setItems(machineCheckQueryPage.getRecords());
        machineCheckQueryPageData.setData(data);
        systemLogService.saveMachineLog(token,"查询","查询了核查审核列表");

        return machineCheckQueryPageData;
    }

    @PostMapping("listOption")
    @ResponseBody
    @SysLog("获取查询页面的select条件")
    public ResponseEty listOption(){
        return machineCheckQueryService.getQuerySelectOption();
    }
}
