package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.DisplayPlatformInfo;
import com.kexin.admin.service.DisplayPlatformInfoService;
import com.kexin.admin.service.DisplayPlatformService;
import com.kexin.admin.service.MachineService;
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

/**
 * 大屏详细配置管理controller层
 */
@Controller
@RequestMapping("displayPlatformInfo")
public class DisplayPlatformInfoController {

    @Autowired
    DisplayPlatformInfoService displayPlatformInfoService;//大屏详细service
    
     @Autowired
     DisplayPlatformService displayPlatformService;//大屏service
    
    @Autowired
    MachineService machineService;//设备service

    @Autowired
    SystemLogService systemLogService;//系统日志记录service

    @GetMapping("list")
    @ResponseBody
    @SysLog("大屏详细列表获取")
    public PageDataBase<DisplayPlatformInfo> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                                  @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                                  @RequestParam(value = "sort")String sort,
                                                  @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                                  @RequestParam(value = "displayPlatformId",defaultValue = "") Integer displayPlatformId,
                                                  @RequestParam(value = "machineId",defaultValue = "") Integer machineId,
                                                  @RequestHeader(value="token",required = false) Integer token,
                                                  ServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<DisplayPlatformInfo> displayPlatformInfoPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<DisplayPlatformInfo> displayPlatformInfoWrapper = new QueryWrapper<>();
        displayPlatformInfoWrapper.orderByDesc("START_DATE");
        if (sort.equals("+id")){
            displayPlatformInfoWrapper.orderByAsc("INFO_ID");
        }else{
            displayPlatformInfoWrapper.orderByDesc("INFO_ID");
        }

        if (StringUtils.isNotEmpty(useFlag)){
            displayPlatformInfoWrapper.eq("USE_FLAG",useFlag);
        }
        if (displayPlatformId!=null){
            displayPlatformInfoWrapper.eq("DISPLAY_PLATFORM_ID",displayPlatformId);
        }  
        if (machineId!=null){
            displayPlatformInfoWrapper.eq("MACHINE_ID",machineId);
        }

        IPage<DisplayPlatformInfo> displayPlatformInfoPage = displayPlatformInfoService.page(new Page<>(page,limit),displayPlatformInfoWrapper);
        displayPlatformInfoPage.getRecords().forEach(r->{
            r.setDisplayPlatform(displayPlatformService.getById(r.getDisplayPlatformId()));
            r.setMachine(machineService.getById(r.getMachineId()));
            });//外键实体添加
        data.setTotal(displayPlatformInfoPage.getTotal());
        data.setItems(displayPlatformInfoPage.getRecords());
        displayPlatformInfoPageData.setData(data);
        systemLogService.saveMachineLog(token,"查询","查询了大屏详细列表");

        return displayPlatformInfoPageData;
    }




    @PostMapping("create")
    @ResponseBody
    @SysLog("新增大屏详细数据")
    public ResponseEty create(@RequestBody DisplayPlatformInfo displayPlatformInfo, @RequestHeader(value="token",required = false) Integer token){
        if(displayPlatformInfo.getDisplayPlatformId()==null){
            return ResponseEty.failure("请选择大屏");
        }
        if(displayPlatformInfo.getMachineId()==null){
            return ResponseEty.failure("请选择设备");
        } if(displayPlatformInfo.getOrderNum()==null){
            return ResponseEty.failure("请填写显示顺序");
        }
        if (displayPlatformInfoService.countByMachineAndPlatform(displayPlatformInfo)>0){
            return ResponseEty.failure("此大屏下的此设备,已经有配置,不能重复添加");
        }
        displayPlatformInfoService.saveDisplayPlatformInfo(displayPlatformInfo);
        if(displayPlatformInfo.getInfoId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        displayPlatformInfo.setDisplayPlatform(displayPlatformService.getById(displayPlatformInfo.getDisplayPlatformId()));
        displayPlatformInfo.setMachine(machineService.getById(displayPlatformInfo.getMachineId()));
        systemLogService.saveMachineLog(token,"新增","新增了"+displayPlatformInfo.getDisplayPlatform().getDisplayPlatformName()+"大屏的"+displayPlatformInfo.getMachine().getMachineName()+"设备");
        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存大屏详细修改数据")
    public ResponseEty update(@RequestBody DisplayPlatformInfo displayPlatformInfo, @RequestHeader(value="token",required = false) Integer token){
        if(displayPlatformInfo.getDisplayPlatformId()==null){
            return ResponseEty.failure("请选择大屏");
        }
        if(displayPlatformInfo.getMachineId()==null){
            return ResponseEty.failure("请选择设备");
        } if(displayPlatformInfo.getOrderNum()==null){
            return ResponseEty.failure("请填写显示顺序");
        }
        DisplayPlatformInfo oldDisplayPlatformInfo = displayPlatformInfoService.getById(displayPlatformInfo.getInfoId());

        if(!displayPlatformInfo.getDisplayPlatformId().equals(oldDisplayPlatformInfo.getDisplayPlatformId()) && !displayPlatformInfo.getMachineId().equals(oldDisplayPlatformInfo.getMachineId())){
            if(displayPlatformInfoService.countByMachineAndPlatform(displayPlatformInfo)>0){
                return ResponseEty.failure("此大屏下的此设备,已经有配置,不能重复添加");
            }
        }
        displayPlatformInfoService.updateDisplayPlatformInfo(displayPlatformInfo);

        if(displayPlatformInfo.getInfoId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        displayPlatformInfo.setDisplayPlatform(displayPlatformService.getById(displayPlatformInfo.getDisplayPlatformId()));
        displayPlatformInfo.setMachine(machineService.getById(displayPlatformInfo.getMachineId()));
        systemLogService.saveMachineLog(token,"更新","更新了"+displayPlatformInfo.getDisplayPlatform().getDisplayPlatformName()+"大屏的"+displayPlatformInfo.getMachine().getMachineName()+"设备");

        return ResponseEty.success("操作成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除大屏详细数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id, @RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        DisplayPlatformInfo displayPlatformInfo=displayPlatformInfoService.getById(id);
        if(displayPlatformInfo == null){
            return ResponseEty.failure("大屏详细不存在");
        }
        displayPlatformInfo.setDisplayPlatform(displayPlatformService.getById(displayPlatformInfo.getDisplayPlatformId()));
        displayPlatformInfo.setMachine(machineService.getById(displayPlatformInfo.getMachineId()));
        displayPlatformInfoService.deleteDisplayPlatformInfo(displayPlatformInfo);
        systemLogService.saveMachineLog(token,"删除","删除了"+displayPlatformInfo.getDisplayPlatform().getDisplayPlatformName()+"大屏的"+displayPlatformInfo.getMachine().getMachineName()+"设备");

        return ResponseEty.success("删除成功");
    }


    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用大屏详细数据")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id, @RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        DisplayPlatformInfo displayPlatformInfo=displayPlatformInfoService.getById(id);
        if(displayPlatformInfo == null){
            return ResponseEty.failure("大屏详细不存在");
        }
        displayPlatformInfoService.lockDisplayPlatformInfo(displayPlatformInfo);
        displayPlatformInfo.setDisplayPlatform(displayPlatformService.getById(displayPlatformInfo.getDisplayPlatformId()));
        displayPlatformInfo.setMachine(machineService.getById(displayPlatformInfo.getMachineId()));
        systemLogService.saveMachineLog(token,"禁用","禁用了"+displayPlatformInfo.getDisplayPlatform().getDisplayPlatformName()+"大屏的"+displayPlatformInfo.getMachine().getMachineName()+"设备");
        return ResponseEty.success("操作成功");
    }
}
