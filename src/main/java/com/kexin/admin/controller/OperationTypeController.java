package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.OperationType;
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
 * 工序种类配置管理controller层
 */
@Controller
@RequestMapping("operationType")
public class OperationTypeController {

    @Autowired
    OperationTypeService operationTypeService;

    @Autowired
    SystemLogService systemLogService;//系统日志记录service


    @GetMapping("list")
    @ResponseBody
    @SysLog("工序种类列表获取")
    public PageDataBase<OperationType> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                       @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                       @RequestParam(value = "sort")String sort,
                                       @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                       @RequestParam(value = "title",defaultValue = "") String title,
                                            @RequestHeader(value="token",required = false) Integer token,
                                            ServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<OperationType> operationTypePageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<OperationType> operationTypeWrapper = new QueryWrapper<>();
        operationTypeWrapper.orderByDesc("START_DATE");
        if (sort.equals("+id")){
            operationTypeWrapper.orderByAsc("OPERATION_TYPE_ID");
        }else{
            operationTypeWrapper.orderByDesc("OPERATION_TYPE_ID");
        }

        if (StringUtils.isNotEmpty(useFlag)){
            operationTypeWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            operationTypeWrapper.like("OPERATION_TYPE_NAME",title);
        }

        IPage<OperationType> operationTypePage = operationTypeService.page(new Page<>(page,limit),operationTypeWrapper);
        data.setTotal(operationTypePage.getTotal());
        data.setItems(operationTypePage.getRecords());
        operationTypePageData.setData(data);
        systemLogService.saveMachineLog(token,"查询","查询了工序种类列表");

        return operationTypePageData;
    }

    @GetMapping("listOption")
    @ResponseBody
    @SysLog("工序类别options列表获取")
    public ResponseEty listopetion(){
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("operationTypeOptions",operationTypeService.getOperationTypeSelectOption());
        return responseEty;
    }


    @PostMapping("create")
    @ResponseBody
    @SysLog("新增工序种类数据")
    public ResponseEty create(@RequestBody  OperationType operationType,
                              @RequestHeader(value="token",required = false) Integer token){
        if(StringUtils.isBlank(operationType.getOperationTypeCode())){
            return ResponseEty.failure("工序种类编号不能为空");
        }
        if(StringUtils.isBlank(operationType.getOperationTypeName())){
            return ResponseEty.failure("工序种类名称不能为空");
        }
        if (operationTypeService.operationTypeCountByCode(operationType.getOperationTypeCode())>0){
            return ResponseEty.failure("工序种类编号已使用,请重新输入");
        }
        if (operationTypeService.operationTypeCountByName(operationType.getOperationTypeName())>0){
            return ResponseEty.failure("工序种类名称已使用,请重新输入");
        }
        operationTypeService.saveOperationType(operationType);
        if(operationType.getOperationTypeId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        systemLogService.saveMachineLog(token,"新增","新增了工序种类:"+operationType.getOperationTypeName());

        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存工序种类修改数据")
    public ResponseEty update(@RequestBody  OperationType operationType,
                              @RequestHeader(value="token",required = false) Integer token
    ){
        if(operationType.getOperationTypeId()==null){
            return ResponseEty.failure("工序种类ID不能为空");
        }
        if(StringUtils.isBlank(operationType.getOperationTypeCode())){
            return ResponseEty.failure("工序种类编号不能为空");
        }
        if(StringUtils.isBlank(operationType.getOperationTypeName())){
            return ResponseEty.failure("工序种类名称不能为空");
        }
        OperationType oldOperationType = operationTypeService.getById(operationType.getOperationTypeId());
        if(StringUtils.isNotBlank(operationType.getOperationTypeCode())){
            if(!operationType.getOperationTypeCode().equals(oldOperationType.getOperationTypeCode())){
                if(operationTypeService.operationTypeCountByCode(operationType.getOperationTypeCode())>0){
                    return ResponseEty.failure("该工序种类编码已经使用");
                }
            }
        }
        if(StringUtils.isNotBlank(operationType.getOperationTypeName())){
            if(!operationType.getOperationTypeName().equals(oldOperationType.getOperationTypeName())){
                if(operationTypeService.operationTypeCountByName(operationType.getOperationTypeName())>0){
                    return ResponseEty.failure("该工序种类名称已经使用");
                }
            }
        }
        operationTypeService.updateOperationType(operationType);

        if(operationType.getOperationTypeId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        systemLogService.saveMachineLog(token,"更新","更新了工序种类:"+operationType.getOperationTypeName());

        return ResponseEty.success("操作成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除工序种类数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id,@RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        OperationType operationType=operationTypeService.getById(id);
        if(operationType == null){
            return ResponseEty.failure("工序种类不存在");
        }
        operationTypeService.deleteOperationType(operationType);
        systemLogService.saveMachineLog(token,"删除","删除了工序种类:"+operationType.getOperationTypeName());

        return ResponseEty.success("删除成功");
    }


    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用工序种类")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id,@RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        OperationType operationType=operationTypeService.getById(id);
        if(operationType == null){
            return ResponseEty.failure("工序种类不存在");
        }
        operationTypeService.lockOperationType(operationType);
        systemLogService.saveMachineLog(token,"禁用","禁用了工序种类:"+operationType.getOperationTypeName());

        return ResponseEty.success("操作成功");
    }
}
