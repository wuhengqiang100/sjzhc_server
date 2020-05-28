package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.Operator;
import com.kexin.admin.service.OperatorService;
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
 * 人员配置管理controller层
 */
@Controller
@RequestMapping("operator")
public class OperatorController {


    @Autowired
    OperatorService operatorService;

    @Autowired
    SystemLogService systemLogService;//系统日志记录service
    @GetMapping("list")
    @ResponseBody
    @SysLog("人员列表获取")
    public PageDataBase<Operator> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                        @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                        @RequestParam(value = "sort")String sort,
                                       @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                       @RequestParam(value = "title",defaultValue = "") String title,
                                        ServletRequest request,
                                       @RequestHeader(value="token",required = false) Integer token){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<Operator> operatorPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<Operator> operatorWrapper = new QueryWrapper<>();
        operatorWrapper.orderByDesc("START_DATE");

        if (sort.equals("+id")){
            operatorWrapper.orderByAsc("OPERATOR_ID");
        }else{
            operatorWrapper.orderByDesc("OPERATOR_ID");
        }
        if (StringUtils.isNotEmpty(useFlag)){
            operatorWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            operatorWrapper.like("OPERATOR_NAME",title);
        }
        IPage<Operator> operatorPage = operatorService.page(new Page<>(page,limit),operatorWrapper);
        data.setTotal(operatorPage.getTotal());
        data.setItems(operatorPage.getRecords());
        operatorPageData.setData(data);
        systemLogService.saveMachineLog(token,"查询","查询了人员列表");

        return operatorPageData;
    }


    @PostMapping("create")
    @ResponseBody
    @SysLog("新增人员数据")
    public ResponseEty create(@RequestBody  Operator operator,@RequestHeader(value="token",required = false) Integer token){
        if(StringUtils.isBlank(operator.getOperatorCode())){
            return ResponseEty.failure("人员编号不能为空");
        }
        if(StringUtils.isBlank(operator.getOperatorName())){
            return ResponseEty.failure("人员名称不能为空");
        }
        if (operatorService.operatorCountByCode(operator.getOperatorCode())>0){
            return ResponseEty.failure("人员编号已使用,请重新输入");
        }
        if (operatorService.operatorCountByName(operator.getOperatorName())>0){
            return ResponseEty.failure("人员名称已使用,请重新输入");
        }
        operatorService.saveOperator(operator);
        if(operator.getOperatorId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        systemLogService.saveMachineLog(token,"新增","新增了人员:"+operator.getOperatorName());

        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存人员修改数据")
    public ResponseEty update(@RequestBody  Operator operator,@RequestHeader(value="token",required = false) Integer token){
        if(operator.getOperatorId()==null){
            return ResponseEty.failure("人员ID不能为空");
        }
        if(StringUtils.isBlank(operator.getOperatorCode())){
            return ResponseEty.failure("人员编号不能为空");
        }
        if(StringUtils.isBlank(operator.getOperatorName())){
            return ResponseEty.failure("人员名称不能为空");
        }
        Operator oldOperator = operatorService.getById(operator.getOperatorId());
        if(StringUtils.isNotBlank(operator.getOperatorCode())){
            if(!operator.getOperatorCode().equals(oldOperator.getOperatorCode())){
                if(operatorService.operatorCountByCode(operator.getOperatorCode())>0){
                    return ResponseEty.failure("该人员编码已经使用");
                }
            }
        }
        if(StringUtils.isNotBlank(operator.getOperatorName())){
            if(!operator.getOperatorName().equals(oldOperator.getOperatorName())){
                if(operatorService.operatorCountByName(operator.getOperatorName())>0){
                    return ResponseEty.failure("该人员名称已经使用");
                }
            }
        }
        operatorService.updateOperator(operator);

        if(operator.getOperatorId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        systemLogService.saveMachineLog(token,"更新","更新了人员:"+operator.getOperatorName());

        return ResponseEty.success("操作成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除人员数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id,@RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        Operator operator=operatorService.getById(id);
        if(operator == null){
            return ResponseEty.failure("人员不存在");
        }
        operatorService.deleteOperator(operator);
        systemLogService.saveMachineLog(token,"删除","删除了人员:"+operator.getOperatorName());

        return ResponseEty.success("删除成功");
    }
    //



    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用人员")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id,@RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        Operator operator=operatorService.getById(id);
        if(operator == null){
            return ResponseEty.failure("人员不存在");
        }
        operatorService.lockOperator(operator);
        systemLogService.saveMachineLog(token,"禁用","禁用了人员:"+operator.getOperatorName());

        return ResponseEty.success("操作成功");
    }
}
