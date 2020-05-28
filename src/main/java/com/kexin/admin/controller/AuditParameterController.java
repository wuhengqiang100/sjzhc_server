package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.*;
import com.kexin.admin.entity.vo.AuditParameter.AuditParameterDelete;
import com.kexin.admin.entity.vo.AuditParameter.AuditParameterDetail;
import com.kexin.admin.entity.vo.AuditParameter.AuditParameterSelect;
import com.kexin.admin.mapper.AuditParameterMapper;
import com.kexin.admin.service.*;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.base.Data;
import com.kexin.common.base.PageDataBase;
import com.kexin.common.util.ResponseEty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * 审核参数配置管理controller层
 */
@Controller
@RequestMapping("auditParameter")
public class AuditParameterController {

    @Autowired
    AuditParameterService auditParameterService;//审核参数service

    @Autowired
    AuditParameterTypeService auditParameterTypeService;//审核参数类型service
    
    @Autowired
    OperationService operationService;//审核参数service
    
    @Autowired
    ProductsService productsService;//产品servcie

    @Autowired
    MachineService machineService;//设备service

    @Autowired
    SystemLogService systemLogService;//系统日志记录service
    @GetMapping("list")
    @ResponseBody
    @SysLog("审核参数列表获取")
    public PageDataBase<AuditParameterSelect> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                       @RequestParam(value = "limit",defaultValue = "1000")Integer limit,
                                       @RequestParam(value = "sort")String sort,
                                       @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                       @RequestParam(value = "title",defaultValue = "") String title,
                                       @RequestParam(value = "judgeCheckTypeId",defaultValue = "") Integer judgeCheckTypeId,
                                       @RequestParam(value = "operationId",defaultValue = "") Integer operationId,
                                       @RequestParam(value = "productId",defaultValue = "") Integer productId,
                                       @RequestParam(value = "machineId",defaultValue = "") Integer machineId,
                                       @RequestHeader(value="token",required = false) Integer token
                                       ){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<AuditParameterSelect> auditParameterPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<AuditParameter> auditParameterWrapper = new QueryWrapper<>();
        auditParameterWrapper.orderByDesc("START_DATE");
        if (sort.equals("+id")){
            auditParameterWrapper.orderByAsc("JUDGE_CHECK_ID");
        }else{
            auditParameterWrapper.orderByDesc("JUDGE_CHECK_ID");
        }

        if (StringUtils.isNotEmpty(useFlag)){
            auditParameterWrapper.eq("USE_FLAG",useFlag);
        }
        if (judgeCheckTypeId!=null){
            auditParameterWrapper.eq("JUDGE_CHECK_TYPE_ID",judgeCheckTypeId);
        }
        if (operationId!=null){
            auditParameterWrapper.eq("OPERATION_ID",operationId);
        } if (productId!=null){
            auditParameterWrapper.eq("PRODUCT_ID",productId);
        }if (machineId!=null){
            auditParameterWrapper.eq("MACHINE_ID",machineId);
        }

//        List<Map<String,Object>> mapList= auditParameterService.getAuditParameterSecond();
        IPage<AuditParameter> auditParameterPage = auditParameterService.page(new Page<>(page,limit),auditParameterWrapper);
        auditParameterPage.getRecords().forEach(r->{
                    r.setJudgeCheckType(auditParameterTypeService.getById(r.getJudgeCheckTypeId()));
                    r.setOperation(operationService.getById(r.getOperationId()));
                    r.setProducts(productsService.getById(r.getProductId()));
                    r.setMachine(machineService.getById(r.getMachineId()));
                });//外键实体添加
        List<AuditParameterSelect> auditParameterSelectList = this.handleRecords(auditParameterPage.getRecords());
        data.setTotal(Long.valueOf(auditParameterSelectList.size()));
        data.setItems(auditParameterSelectList);
        auditParameterPageData.setData(data);
        systemLogService.saveMachineLog(token,"查询","查询了审核参数");
        return auditParameterPageData;
    }


    private List<AuditParameterSelect>  handleRecords(List<AuditParameter> auditParameterList){
        List<AuditParameterSelect> auditParameterSelectList = new ArrayList<>();
        if (this.analyze(auditParameterList)!=null){
            auditParameterSelectList.addAll(this.analyze(auditParameterList));
        }

        auditParameterSelectList= auditParameterSelectList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getOperationName() + ";" + o.getProductName()+ ";" + o.getMachineName()))), ArrayList::new));

        for (AuditParameterSelect pSelect:auditParameterSelectList) {
            StringBuffer values=new StringBuffer();
            pSelect.getDetails().forEach(r->{
                if (r.getValue()==null){
                    r.setValue(0);
                }

                values.append("                   "+r.getName()+":"+r.getValue()+"              ");

            });
            pSelect.setValues(String.valueOf(values));

        }
        return auditParameterSelectList;
    };


    private List<AuditParameterSelect> analyze(List<AuditParameter> auditParameterList){


        List<AuditParameterSelect> auditParameterSelectList=new ArrayList<>();
        AuditParameterSelect auditParameterSelect = null;
        for(int i=0;i<auditParameterList.size();i++) {

            boolean secondLoop = true;

            AuditParameter audit1=auditParameterList.get(i);

            for(int j=0;j<auditParameterList.size();j++) {
                if (!secondLoop){
                    continue;
                }
                AuditParameter audit2=auditParameterList.get(j);
                //如果工序+产品+设备都一样
                Boolean insertFlag=false;
                if (audit1.getOperationId().equals(audit2.getOperationId()) && audit1.getProductId().equals(audit2.getProductId()) && audit1.getMachineId().equals(audit2.getMachineId()) ) {
                    auditParameterSelect=new AuditParameterSelect();
                    auditParameterSelect.setOperationName(audit2.getOperation().getOperationName());
                    auditParameterSelect.setProductName(audit2.getProducts().getProductName());
                    auditParameterSelect.setMachineName(audit2.getMachine().getMachineName());
                    auditParameterSelect.setUseFlag(audit2.getUseFlag());
                    auditParameterSelect.setStartDate(audit2.getStartDate());
                    auditParameterSelect.setEndDate(audit2.getEndDate());
                    auditParameterSelect.setNote(audit2.getNote());
                    auditParameterSelect.setOperationId(audit2.getOperationId());
                    auditParameterSelect.setProductId(audit2.getProductId());
                    auditParameterSelect.setMachineId(audit2.getMachineId());
                    insertFlag=true;
                    auditParameterSelect.setDetails(auditParameterService.getAuditParameterDetail(audit2));//放入参数的list
                    secondLoop = false;
                }

            }
            auditParameterSelectList.add(auditParameterSelect);
        }
        return auditParameterSelectList;
    }
    @PostMapping("create")
    @ResponseBody
    @SysLog("新增审核参数数据")
    public ResponseEty create(@RequestBody  AuditParameter auditParameter,@RequestHeader(value="token",required = false) Integer token){
        if (auditParameter.getValues()==null){
            return ResponseEty.failure("请填写参数值");
        } if (auditParameter.getOperationId()==null){
            return ResponseEty.failure("请选择工序");
        } if (auditParameter.getProductId()==null){
            return ResponseEty.failure("请选择产品");
        }if (auditParameter.getMachineId()==null){
            return ResponseEty.failure("请选择设备");
        }

        if (auditParameterService.countParameterByTypeOperationProduct(auditParameter)>0){
            return ResponseEty.failure("一个工序->产品->设备的审核参数,只能添加一组数据");
        }
        updateParameterWithMachine(auditParameter);
        Operation operation=operationService.getById(auditParameter.getOperationId());
        Products product=productsService.getById(auditParameter.getProductId());
        Machine machine=machineService.getById(auditParameter.getMachineId());
        systemLogService.saveMachineLog(token,"新增","新增了"+operation.getOperationName()+","+product.getProductName()+","+machine.getMachineName()+"的审核参数");

        return ResponseEty.success("添加成功!");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存审核参数修改数据")
    public ResponseEty update(@RequestBody  AuditParameter auditParameter,@RequestHeader(value="token",required = false) Integer token){

        if (auditParameter.getValues()==null){
            return ResponseEty.failure("请填写参数值");
        }
         if (auditParameter.getOperationId()==null){
            return ResponseEty.failure("请选择工序");
        } if (auditParameter.getProductId()==null){
            return ResponseEty.failure("请选择产品");
        }
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
            List<AuditParameter> auditParameterList=auditParameterService.list(auditParameterQueryWrapper);
                auditParameterList.forEach(r->{
                    r.setValues(auditParameter.getValues());
                    r.setUseFlag(auditParameter.getUseFlag());
                    r.setNote(auditParameter.getNote());
                    updateParameterWithNotMachine(r);
                });
            Operation operation=operationService.getById(auditParameter.getOperationId());
            Products product=productsService.getById(auditParameter.getProductId());
            systemLogService.saveMachineLog(token,"修改","修改了"+operation.getOperationName()+","+product.getProductName()+"的审核参数");
            return ResponseEty.success("批量修改成功!");
        }else{
            Operation operation=operationService.getById(auditParameter.getOperationId());
            Products product=productsService.getById(auditParameter.getProductId());
            Machine machine=machineService.getById(auditParameter.getMachineId());
            systemLogService.saveMachineLog(token,"修改","修改了"+operation.getOperationName()+","+product.getProductName()+","+machine.getMachineName()+"的审核参数");

            updateParameterWithMachine(auditParameter);
            return ResponseEty.success("修改成功!");

        }

    }

    private void updateParameterWithNotMachine(AuditParameter auditParameter){
        QueryWrapper<AuditParameterType> auditParameterTypeQueryWrapper=new QueryWrapper<>();
        List<AuditParameterType> auditParameterTypeList=auditParameterTypeService.list(auditParameterTypeQueryWrapper);
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
            auditParameterService.saveOrUpdate(auditParameter1,auditParameterQueryWrapper1);
        }
    }

    private void updateParameterWithMachine(AuditParameter auditParameter){
        QueryWrapper<AuditParameterType> auditParameterTypeQueryWrapper=new QueryWrapper<>();
        List<AuditParameterType> auditParameterTypeList=auditParameterTypeService.list(auditParameterTypeQueryWrapper);
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
            auditParameterService.saveOrUpdate(auditParameter1,auditParameterQueryWrapper1);
        }
    }
    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除审核参数数据(单个)")
    public ResponseEty delete(@RequestBody AuditParameter auditParameter,@RequestHeader(value="token",required = false) Integer token){
        Integer deleteFlag=auditParameterService.deleteAuditParameter(auditParameter);


        if (deleteFlag>0){
            Operation operation=operationService.getById(auditParameter.getOperationId());
            Products product=productsService.getById(auditParameter.getProductId());
            Machine machine=machineService.getById(auditParameter.getMachineId());
            systemLogService.saveMachineLog(token,"删除","删除了"+operation.getOperationName()+","+product.getProductName()+","+machine.getMachineName()+"的审核参数");
            return ResponseEty.success("删除成功");
        }else{
            return ResponseEty.failure("删除失败");

        }
    }



    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用审核参数")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id,@RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        AuditParameter auditParameter=auditParameterService.getById(id);
        if(auditParameter == null){
            return ResponseEty.failure("审核参数不存在");
        }
        auditParameterService.lockAuditParameter(auditParameter);
        Operation operation=operationService.getById(auditParameter.getOperationId());
        Products product=productsService.getById(auditParameter.getProductId());
        Machine machine=machineService.getById(auditParameter.getMachineId());
        systemLogService.saveMachineLog(token,"禁用","禁用了"+operation.getOperationName()+","+product.getProductName()+","+machine.getMachineName()+"的审核参数");
        return ResponseEty.success("操作成功");
    }
}
