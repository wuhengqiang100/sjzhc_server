package com.kexin.admin.controller;


import com.kexin.admin.component.SelectOptionComponent;
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


}
