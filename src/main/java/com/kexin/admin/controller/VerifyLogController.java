package com.kexin.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.*;
import com.kexin.admin.entity.vo.QaInspectSelect;
import com.kexin.admin.service.*;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.base.Data;
import com.kexin.common.base.PageDataBase;
import com.kexin.common.util.DateUtil.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @GetMapping("dataup")
    @ResponseBody
    @SysLog("上传日志查询list")
    public PageDataBase<DataupLog> listDataUpLog(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                        @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                        @RequestParam(value = "sort")String sort,
                                        @RequestParam(value = "title",defaultValue = "") String title){
        PageDataBase<DataupLog> dataupLogPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<DataupLog> dataupLogWrapper = new QueryWrapper<>();
        if (sort.equals("+id")){
            dataupLogWrapper.orderByAsc("DATAUP_SET_ID");
        }else{
            dataupLogWrapper.orderByDesc("DATAUP_SET_ID");
        }
        //增加根据用户查询的操作
        //增加根据时间查询的操作
/*        if (StringUtils.isNotEmpty(useFlag)){
            dataupLogWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            dataupLogWrapper.like("OPERATION_NAME",title);
        }*/
        IPage<DataupLog> dataupLogPage = dataupLogService.page(new Page<>(page,limit),dataupLogWrapper);
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
    @GetMapping("machine")
    @ResponseBody
    @SysLog("设备日志查询")
    public PageDataBase<MachineLog> listMachineLog(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                        @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                        @RequestParam(value = "sort")String sort,
                                        @RequestParam(value = "title",defaultValue = "") String title){
        PageDataBase<MachineLog> machineLogPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<MachineLog> machineLogWrapper = new QueryWrapper<>();
        if (sort.equals("+id")){
            machineLogWrapper.orderByAsc("LOG_MACHINE_ID");
        }else{
            machineLogWrapper.orderByDesc("LOG_MACHINE_ID");
        }
        //增加根据用户查询的操作
        //增加根据时间查询的操作
/*        if (StringUtils.isNotEmpty(useFlag)){
            machineLogWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            machineLogWrapper.like("OPERATION_NAME",title);
        }*/
        IPage<MachineLog> machineLogPage = machineLogService.page(new Page<>(page,limit),machineLogWrapper);
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
    @GetMapping("operation")
    @ResponseBody
    @SysLog("操作日志查询")
    public PageDataBase<OperationLog> listOperationLog(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                                   @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                                   @RequestParam(value = "sort")String sort,
                                                   @RequestParam(value = "title",defaultValue = "") String title){
        PageDataBase<OperationLog> operationLogPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<OperationLog> operationLogWrapper = new QueryWrapper<>();
        if (sort.equals("+id")){
            operationLogWrapper.orderByAsc("LOG_OPERATION_NOTE_ID");
        }else{
            operationLogWrapper.orderByDesc("LOG_OPERATION_NOTE_ID");
        }
        //增加根据用户查询的操作
        //增加根据时间查询的操作
/*        if (StringUtils.isNotEmpty(useFlag)){
            operationLogWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            operationLogWrapper.like("OPERATION_NAME",title);
        }*/
        IPage<OperationLog> operationLogPage = operationLogService.page(new Page<>(page,limit),operationLogWrapper);
        data.setTotal(operationLogPage.getTotal());
        data.setItems(operationLogPage.getRecords());
        operationLogPageData.setData(data);
        return operationLogPageData;
    }
    @GetMapping("produce")
    @ResponseBody
    @SysLog("生产日志查询")
    public PageDataBase<ViewProduceLog> listProduceLog(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                                   @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                                   @RequestParam(value = "sort")String sort,
                                                   @RequestParam(value = "title",defaultValue = "") String title){
        PageDataBase<ViewProduceLog> produceLogPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<ViewProduceLog> produceLogWrapper = new QueryWrapper<>();
        if (sort.equals("+id")){
            produceLogWrapper.orderByAsc("LOG_PROD_ID");
        }else{
            produceLogWrapper.orderByDesc("LOG_PROD_ID");
        }
        //增加根据用户查询的操作
        //增加根据时间查询的操作
/*        if (StringUtils.isNotEmpty(useFlag)){
            produceLogWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            produceLogWrapper.like("OPERATION_NAME",title);
        }*/
        IPage<ViewProduceLog> produceLogPage = viewProduceLogService.page(new Page<>(page,limit),produceLogWrapper);
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

/*        增加根据用户查询的操作
        增加根据时间查询的操作
        if (StringUtils.isNotEmpty(useFlag)){
            machineCheckQueryWrapper.eq("USE_FLAG",useFlag);
        }*/
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
}
