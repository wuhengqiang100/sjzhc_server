package com.kexin.admin.controller;


import com.kexin.admin.component.SelectOptionComponent;
import com.kexin.admin.entity.vo.AuditParameter.ParameterByIds;
import com.kexin.admin.service.LoginUserService;
import com.kexin.admin.service.MachineCheckQueryService;
import com.kexin.admin.service.SysFunctionService;
import com.kexin.admin.service.SysMenusService;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.component.TestComponent;
import com.kexin.common.util.ResponseEty;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 公用controller
 */

@Controller
@RequestMapping("option")
public class SelectOptionController {


    @Autowired
    SelectOptionComponent selectOptionComponent;//查询条件的组件

    @Autowired
    MachineCheckQueryService machineCheckQueryService;

    @PostMapping("all")
    @ResponseBody
    @SysLog("获取所有查询页面的select条件")
    public ResponseEty listOptionAll(){
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("productOption",selectOptionComponent.getProductSelectOption());//产品下拉option
        responseEty.setAny("operationOption",selectOptionComponent.getOperationSelectOption());//工序下拉option
        responseEty.setAny("machineOption",selectOptionComponent.getMachineSelectOption());//设备下拉option
        responseEty.setAny("dicWorkUnitOption",selectOptionComponent.getWorkUnitSelectOption());//机台下拉option
        responseEty.setAny("cartNumfirstOption",selectOptionComponent.getWorkUnitSelectOption());//车号首字母下拉option
        return responseEty;
    }

    @PostMapping("machineModel")
    @ResponseBody
    @SysLog("获取机检模板页面的select条件")
    public ResponseEty listOptionMachineModel(){
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("machineOption",selectOptionComponent.getMachineSelectOption());//设备下拉option
        responseEty.setAny("operationOption",selectOptionComponent.getOperationSelectOption());//工序下拉option
        responseEty.setAny("productOption",selectOptionComponent.getProductSelectOption());//产品下拉option
        return responseEty;
    }

    @PostMapping("auditParameter")
    @ResponseBody
    @SysLog("获取机检模板页面的select条件")
    public ResponseEty listOptionAuditParameter(){
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("operationOption",selectOptionComponent.getOperationSelectOption());//工序下拉option
        responseEty.setAny("productOption",selectOptionComponent.getProductSelectOption());//产品下拉option
        responseEty.setAny("machineOption",selectOptionComponent.getMachineSelectOption());//设备下拉option
        responseEty.setAny("judgeCheckTypeOption",selectOptionComponent.getAuditParameterTypeSelectOption());//审核参数类型下拉option
        return responseEty;
    }

    @PostMapping("auditParameterByIds")
    @ResponseBody
    @SysLog("获取机检模板页面的select条件")
    public ResponseEty listOptionAuditParameterByIds(@RequestBody ParameterByIds parameterByIds){
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("operationOption",selectOptionComponent.getOperationSelectOption());//工序下拉option
        responseEty.setAny("productOption",selectOptionComponent.getProductSelectOption());//产品下拉option
        responseEty.setAny("machineOption",selectOptionComponent.getMachineSelectOption());//设备下拉option
        responseEty.setAny("judgeCheckTypeOption",selectOptionComponent.getAuditParameterTypeSelectOptionByIds(parameterByIds));//审核参数类型下拉option
        return responseEty;
    }

    @PostMapping("operator")
    @ResponseBody
    @SysLog("获取登陆账户页面的select条件")
    public ResponseEty listOptionOperator(){
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("operatorOption",selectOptionComponent.getOperatorSelectOption());//工序下拉option
        return responseEty;
    }

    @PostMapping("wasterReason")
    @ResponseBody
    @SysLog("获取错误类型页面的select条件")
    public ResponseEty listOptionErrorType(){
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("operationOption",selectOptionComponent.getOperationSelectOption());//工序下拉option
        return responseEty;
    }
    @PostMapping("product")
    @ResponseBody
    @SysLog("获取产品页面的select条件")
    public ResponseEty listOptionProduct(){
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
//        responseEty.setAny("cartNumFirstOption",selectOptionComponent.getCartNumFirstSelectOption());//前缀字母option
        return responseEty;
    }

    @PostMapping("workUnit")
    @ResponseBody
    @SysLog("获取几天配置页面的select条件")
    public ResponseEty listOptionWorkUnit(){
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("operatorOption",selectOptionComponent.getOperatorSelectOption());//工序下拉option
        return responseEty;
    }
    @PostMapping("produceLog")
    @ResponseBody
    @SysLog("获取几天配置页面的select条件")
    public ResponseEty listOptionProduceLog(){
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("operationOption",selectOptionComponent.getOperationSelectOption());//工序下拉option
        responseEty.setAny("productOption",selectOptionComponent.getProductSelectOption());//产品下拉option
        responseEty.setAny("operatorOption",selectOptionComponent.getOperatorSelectOption());//工序下拉option
        return responseEty;
    }

    @PostMapping("machineQuery")
    @ResponseBody
    @SysLog("获取核查查询的条件")
    public ResponseEty listOptionMachineQuery(){
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("operationOption",selectOptionComponent.getOperationSelectOption());//工序下拉option
        responseEty.setAny("productOption",selectOptionComponent.getProductSelectOption());//产品下拉option
        responseEty.setAny("machineOption",selectOptionComponent.getMachineSelectOption());//设备下拉option
        responseEty.setAny("dicWorkUnitOption",selectOptionComponent.getWorkUnitSelectOption());//机台下拉option
        return responseEty;
    }


    @PostMapping("reportMain")
    @ResponseBody
    @SysLog("获取报表主查询的查询条件")
    public ResponseEty listOptionReportMain(){
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("productOption",selectOptionComponent.getProductSelectOption());//产品下拉option
        responseEty.setAny("operationOption",selectOptionComponent.getOperationSelectOption());//工序下拉option
        return responseEty;
    }


    @PostMapping("machineWarningDeal")
    @ResponseBody
    @SysLog("获取报警处理页面的查询条件")
    public ResponseEty listOptionMachineWarningDeal(){
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("machineOption",selectOptionComponent.getMachineSelectOption());//设备下拉option
        responseEty.setAny("operatorOption",selectOptionComponent.getOperatorSelectOption());//工序下拉option
        return responseEty;
    }


    @PostMapping("displayPlatformInfo")
    @ResponseBody
    @SysLog("获取报警处理页面的查询条件")
    public ResponseEty listOptionDisplayPlatformInfo(){
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("machineOption",selectOptionComponent.getMachineSelectOption());//设备下拉option

        responseEty.setAny("displayPlatformOption",selectOptionComponent.getDisplayPlatformSelectOption());//工序下拉option
        return responseEty;
    }


}
