package com.kexin.admin.controller;

import com.kexin.admin.component.MonitorBigScreenComponent;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.util.ResponseEty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("monitor")
public class MonitorController {



    @Autowired
    MonitorBigScreenComponent monitorBigScreenComponent; // 大屏数据获取组件

    @PostMapping("bigScreen")
    @ResponseBody
    @SysLog("大屏监控数据获取")
    public ResponseEty listOption(){
        return monitorBigScreenComponent.getData();//监控的时候用到monitor数据
    }
}
