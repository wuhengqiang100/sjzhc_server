package com.kexin.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.*;
import com.kexin.admin.entity.vo.query.*;
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
 * 核查综合查询的report相关查询
 */
@Controller
@RequestMapping("query")
public class QueryReportController {

    @Autowired
    DataupLogService dataupLogService;


    @Autowired
    MachineLogService machineLogService;

    @Autowired
    OperationLogService operationLogService;

    @Autowired
    ProduceLogService produceLogService;





    @Autowired
    OperatorService operatorService;

    @Autowired
    SystemLogService systemLogService;//系统日志service

    @Autowired
    ProductsService productsService;//产品service

    @Autowired
    OperationService operationService;//工序service


    @Autowired
    QueryReportMainService queryReportMainService;//报表主视图service

    @Autowired
    QueryReportNckService queryReportNckService;//报表未检视图service
    @Autowired
    QueryReportQaService queryReportQaService;//报表缺陷视图service



    @PostMapping("reportMain")
    @ResponseBody
    @SysLog("报表查询主视图")
    public PageDataBase<QueryReportMain> listReportMain(@RequestBody QueryReportMainSelect qaSelect,
                                                        @RequestHeader(value="token",required = false) Integer token){
        PageDataBase<QueryReportMain> queryReportMainPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<QueryReportMain> queryReportMainWrapper = new QueryWrapper<>();
        if (qaSelect.getSort()!=null){
            if (qaSelect.getSort().equals("+id")){
                queryReportMainWrapper.orderByAsc("JOB_ID");
            }else{
                queryReportMainWrapper.orderByDesc("JOB_ID");
            }
        }
        if (qaSelect.getCartNumber()!=null){//根据车号查询
            queryReportMainWrapper.like("CART_NUMBER",qaSelect.getCartNumber());
        }if (qaSelect.getProductId()!=null){//根据产品名称查询
            queryReportMainWrapper.eq("PRODUCT_ID",qaSelect.getProductId());
        }if (qaSelect.getFinishedFlag()!=null){//根据完成标志查询
            queryReportMainWrapper.eq("finished_flag",qaSelect.getFinishedFlag());
        }if (qaSelect.getStartDate()!=null && qaSelect.getEndDate()!=null ){//根据车台名称查询
            queryReportMainWrapper.between("START_DATE",  qaSelect.getStartDate(), qaSelect.getEndDate());
        }
        IPage<QueryReportMain> queryReportMainPage = queryReportMainService.page(new Page<>(qaSelect.getPage(),qaSelect.getLimit()),queryReportMainWrapper);
//        data.setTotal(queryReportMainPage.getTotal());
        data.setTotal((long) 100000);
        data.setItems(queryReportMainPage.getRecords());
        queryReportMainPageData.setData(data);
        systemLogService.saveMachineLog(token,"查询","查询了报表主信息");
        return queryReportMainPageData;
    }

    @PostMapping("reportNck")
    @ResponseBody
    @SysLog("报表查询未检视图")
    public PageDataBase<QueryReportNck> listReportNck(@RequestBody QueryReportNckSelect qaSelect,
                                                @RequestHeader(value="token",required = false) Integer token){
        PageDataBase<QueryReportNck> queryReportNckPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<QueryReportNck> queryReportNckWrapper = new QueryWrapper<>();
        if (qaSelect.getSort()!=null){
            if (qaSelect.getSort().equals("+id")){
                queryReportNckWrapper.orderByAsc("JOB_ID");
            }else{
                queryReportNckWrapper.orderByDesc("JOB_ID");
            }
        }
        if (qaSelect.getSheetNum()!=null){//根据大张号查询
            queryReportNckWrapper.like("sheet_num",qaSelect.getSheetNum());
        }if (qaSelect.getCodeNum()!=null){//根据印码查询
            queryReportNckWrapper.like("code_num",qaSelect.getCodeNum());
        }if (qaSelect.getProductId()!=null){//根据产品查询
            queryReportNckWrapper.eq("PRODUCT_ID",qaSelect.getProductId());
        }if (qaSelect.getProductId()!=null){//根据工序查询
            queryReportNckWrapper.eq("operation_id",qaSelect.getOperationId());
        }
        IPage<QueryReportNck> queryReportNckPage = queryReportNckService.page(new Page<>(qaSelect.getPage(),qaSelect.getLimit()),queryReportNckWrapper);
        data.setTotal(queryReportNckPage.getTotal());
        data.setItems(queryReportNckPage.getRecords());
        queryReportNckPageData.setData(data);
        systemLogService.saveMachineLog(token,"查询","查询了报表未检信息");
        return queryReportNckPageData;
    }

    @PostMapping("reportQa")
    @ResponseBody
    @SysLog("报表查询缺陷视图")
    public PageDataBase<QueryReportQa> listReportQa(@RequestBody QueryReportQaSelect qaSelect,
                                                    @RequestHeader(value="token",required = false) Integer token){
        PageDataBase<QueryReportQa> queryReportQaPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<QueryReportQa> queryReportQaWrapper = new QueryWrapper<>();
        if (qaSelect.getSort()!=null){
            if (qaSelect.getSort().equals("+id")){
                queryReportQaWrapper.orderByAsc("JOB_ID");
            }else{
                queryReportQaWrapper.orderByDesc("JOB_ID");
            }
        }
        if (qaSelect.getSheetNum()!=null){//根据大张号查询
            queryReportQaWrapper.like("sheet_num",qaSelect.getSheetNum());
        }if (qaSelect.getCodeNum()!=null){//根据印码查询
            queryReportQaWrapper.like("code_num",qaSelect.getCodeNum());
        }if (qaSelect.getProductId()!=null){//根据产品查询
            queryReportQaWrapper.eq("PRODUCT_ID",qaSelect.getProductId());
        }if (qaSelect.getProductId()!=null){//根据工序查询
            queryReportQaWrapper.eq("operation_id",qaSelect.getOperationId());
        }
        IPage<QueryReportQa> queryReportQaPage = queryReportQaService.page(new Page<>(qaSelect.getPage(),qaSelect.getLimit()),queryReportQaWrapper);
        data.setTotal(queryReportQaPage.getTotal());
        data.setItems(queryReportQaPage.getRecords());
        queryReportQaPageData.setData(data);
        systemLogService.saveMachineLog(token,"查询","查询了报表缺陷信息");
        return queryReportQaPageData;
    }

}
