package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.Machine;
import com.kexin.admin.entity.tables.MachineWarning;
import com.kexin.admin.service.MachineService;
import com.kexin.admin.service.MachineWarningService;
import com.kexin.admin.service.OperatorService;
import com.kexin.admin.service.SystemLogService;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.base.Data;
import com.kexin.common.base.PageDataBase;
import com.kexin.common.util.ResponseEty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

/**
 * 设备报警配置管理controller层
 */
@Controller
@RequestMapping("machineWarning")
public class MachineWarningController {

    @Autowired
    MachineWarningService machineWarningService;

    @Autowired
    MachineService machineService;
    @Autowired
    OperatorService operatorService;

    @Autowired
    SystemLogService systemLogService;//系统日志记录service

    @GetMapping("list")
    @ResponseBody
    @SysLog("设备报警列表获取")
    public PageDataBase<MachineWarning> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                             @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                             @RequestParam(value = "sort")String sort,
                                             @RequestParam(value = "logState",defaultValue = "")Integer logState, //处理状态
                                             @RequestParam(value = "machineId",defaultValue = "") Integer machineId,
                                             @RequestParam(value = "operatorId",defaultValue = "") Integer operatorId,
                                             @RequestHeader(value="token",required = false) Integer token,
                                             ServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<MachineWarning> machineWarningPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<MachineWarning> machineWarningWrapper = new QueryWrapper<>();
        machineWarningWrapper.orderByDesc("LOG_DATE");
        if (sort.equals("+id")){
            machineWarningWrapper.orderByAsc("LOG_ID");
        }else{
            machineWarningWrapper.orderByDesc("LOG_ID");
        }

        if (logState!=null){
            machineWarningWrapper.eq("LOG_STATE",logState);
        }
        if (machineId!=null){
            machineWarningWrapper.eq("MACHINE_ID",machineId);
        }if (operatorId!=null){
            machineWarningWrapper.eq("OPERATOR_ID",operatorId);
        }
        IPage<MachineWarning> machineWarningPage = machineWarningService.page(new Page<>(page,limit),machineWarningWrapper);
        machineWarningPage.getRecords().forEach(r->{
                    r.setMachine(machineService.getById(r.getMachineId()));
                    if (r.getOperatorId()!=null){
                        r.setOperator(operatorService.getById(r.getOperatorId()));
                    }
                });//外键实体添加
        data.setTotal(machineWarningPage.getTotal());
        data.setItems(machineWarningPage.getRecords());
        machineWarningPageData.setData(data);
        systemLogService.saveMachineLog(token,"查询","查询了设备报警信息列表");

        return machineWarningPageData;
    }


    @PostMapping("deal")
    @ResponseBody
    @SysLog("禁用或者启用工序")
    public ResponseEty dealMachineWarning(@RequestParam(value = "id",required = false)Integer id, @RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        MachineWarning machineWarning=machineWarningService.getById(id);
        if(machineWarning == null){
            return ResponseEty.failure("报警信息不存在");
        }
        machineWarning.setOperatorId(token);
        machineWarningService.dealMachineWarning(machineWarning);
        Machine machine=machineService.getById(machineWarning.getMachineId());
        systemLogService.saveMachineLog(token,"处理","处理了"+machine.getMachineName()+"的异常");
        return ResponseEty.success("操作成功");
    }



}
