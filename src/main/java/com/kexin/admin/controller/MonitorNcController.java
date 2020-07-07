package com.kexin.admin.controller;

import com.kexin.admin.component.MonitorBigScreenComponent;
import com.kexin.admin.service.*;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.util.ResponseEty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 南昌监控大屏controller
 */
@Controller
@RequestMapping("monitorNc")
public class MonitorNcController {



    @Autowired
    MonitorBigScreenComponent monitorBigScreenComponent; // 大屏数据获取组件

    @Autowired
    MachineWarningService machineWarningService;//大屏设备报警service

    @Autowired
    MachineService machineService;//设备service
    @Autowired
    OperatorService operatorService;//人员service


    @Autowired
    DisplayPlatformInfoService displayPlatformInfoService; // 监控配置详细信息service

    @Autowired
    DisplayPlatformService displayPlatformService;// 监控大屏配置service

    @GetMapping("monitorName")
    @ResponseBody
    @SysLog("获取大屏监控的名称")
    public ResponseEty getMonitorName(@RequestParam(name = "id") Integer id){
        return displayPlatformService.getDisplayPlatform(id);
    }

    @GetMapping("cardTop")
    @ResponseBody
    @SysLog("大屏监控卡片数据上面一排")
    public ResponseEty listCardTop(){
        return monitorBigScreenComponent.getCardTop();//监控的时候用到monitor数据
    }
    @GetMapping("cardBottom")
    @ResponseBody
    @SysLog("大屏监控卡片数据下面一排")
    public ResponseEty listCardBottom(){
        return monitorBigScreenComponent.getCardBottom();//监控的时候用到monitor数据
    }

    @GetMapping("warning")
    @ResponseBody
    @SysLog("大屏监控设备报警信息")
    public ResponseEty listWaring(){
        return machineWarningService.listNotDeal();//返回没有处理的监控数据
    }


}
