package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.Operation;
import com.kexin.admin.service.OperationService;
import com.kexin.admin.service.OperationTypeService;
import com.kexin.admin.service.SystemLogService;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.base.Data;
import com.kexin.common.base.PageDataBase;
import com.kexin.common.util.ResponseEty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;

/**
 * 工序配置管理controller层
 */
@Controller
@RequestMapping("operation")
public class OperationController {

    @Autowired
    OperationService operationService;

    @Autowired
    OperationTypeService operationTypeService;

    @Autowired
    SystemLogService systemLogService;//系统日志记录service

    @GetMapping("list")
    @ResponseBody
    @SysLog("工序列表获取")
    public PageDataBase<Operation> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                       @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                       @RequestParam(value = "sort")String sort,
                                       @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                       @RequestParam(value = "title",defaultValue = "") String title,
                                        @RequestHeader(value="token",required = false) Integer token,
                                       ServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<Operation> operationPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<Operation> operationWrapper = new QueryWrapper<>();
        operationWrapper.orderByDesc("START_DATE");
        if (sort.equals("+id")){
            operationWrapper.orderByAsc("OPERATION_ID");
        }else{
            operationWrapper.orderByDesc("OPERATION_ID");
        }

        if (StringUtils.isNotEmpty(useFlag)){
            operationWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            operationWrapper.like("OPERATION_NAME",title);
        }

        IPage<Operation> operationPage = operationService.page(new Page<>(page,limit),operationWrapper);
        operationPage.getRecords().forEach(r->r.setOperationType(operationTypeService.getById(r.getOperationTypeId())));//外键实体添加
        data.setTotal(operationPage.getTotal());
        data.setItems(operationPage.getRecords());
        operationPageData.setData(data);
        systemLogService.saveMachineLog(token,"查询","查询了工序列表");

        return operationPageData;
    }




    @PostMapping("create")
    @ResponseBody
    @SysLog("新增工序数据")
    public ResponseEty create(@RequestBody  Operation operation, @RequestHeader(value="token",required = false) Integer token){
        if(StringUtils.isBlank(operation.getOperationCode())){
            return ResponseEty.failure("工序编号不能为空");
        }
        if(StringUtils.isBlank(operation.getOperationName())){
            return ResponseEty.failure("工序名称不能为空");
        }
        if (operationService.operationCountByCode(operation.getOperationCode())>0){
            return ResponseEty.failure("工序编号已使用,请重新输入");
        }
        if (operationService.operationCountByName(operation.getOperationName())>0){
            return ResponseEty.failure("工序名称已使用,请重新输入");
        }
        operationService.saveOperation(operation);
        if(operation.getOperationId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        systemLogService.saveMachineLog(token,"新增","新增了"+operation.getOperationName()+"工序");
        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存工序修改数据")
    public ResponseEty update(@RequestBody  Operation operation, @RequestHeader(value="token",required = false) Integer token){
        if(operation.getOperationId()==null){
            return ResponseEty.failure("工序ID不能为空");
        }
        if(StringUtils.isBlank(operation.getOperationCode())){
            return ResponseEty.failure("工序编号不能为空");
        }
        if(StringUtils.isBlank(operation.getOperationName())){
            return ResponseEty.failure("工序名称不能为空");
        }
        Operation oldOperation = operationService.getById(operation.getOperationId());
        if(StringUtils.isNotBlank(operation.getOperationCode())){
            if(!operation.getOperationCode().equals(oldOperation.getOperationCode())){
                if(operationService.operationCountByCode(operation.getOperationCode())>0){
                    return ResponseEty.failure("该工序编码已经使用");
                }
            }
        }
        if(StringUtils.isNotBlank(operation.getOperationName())){
            if(!operation.getOperationName().equals(oldOperation.getOperationName())){
                if(operationService.operationCountByName(operation.getOperationName())>0){
                    return ResponseEty.failure("该工序名称已经使用");
                }
            }
        }
        operationService.updateOperation(operation);

        if(operation.getOperationId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        systemLogService.saveMachineLog(token,"更新","更新了"+operation.getOperationName()+"工序");

        return ResponseEty.success("操作成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除工序数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id, @RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        Operation operation=operationService.getById(id);
        if(operation == null){
            return ResponseEty.failure("工序不存在");
        }
        operationService.deleteOperation(operation);
        systemLogService.saveMachineLog(token,"删除","删除了"+operation.getOperationName()+"工序");

        return ResponseEty.success("删除成功");
    }


    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用工序")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id, @RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        Operation operation=operationService.getById(id);
        if(operation == null){
            return ResponseEty.failure("工序不存在");
        }
        operationService.lockOperation(operation);
        systemLogService.saveMachineLog(token,"禁用","禁用了"+operation.getOperationName()+"工序");
        return ResponseEty.success("操作成功");
    }
}
