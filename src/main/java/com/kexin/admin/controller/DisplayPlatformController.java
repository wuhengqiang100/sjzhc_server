package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.DisplayPlatform;
import com.kexin.admin.service.DisplayPlatformService;
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
 * 大屏管理controller层
 */
@Controller
@RequestMapping("displayPlatform")
public class DisplayPlatformController {

    @Autowired
    DisplayPlatformService displayPlatformService;

    @Autowired
    SystemLogService systemLogService;//系统日志记录service


    @GetMapping("list")
    @ResponseBody
    @SysLog("大屏列表获取")
    public PageDataBase<DisplayPlatform> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                              @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                              @RequestParam(value = "sort")String sort,
                                              @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                              @RequestParam(value = "title",defaultValue = "") String title,
                                              @RequestHeader(value="token",required = false) Integer token,
                                              ServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<DisplayPlatform> displayPlatformPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<DisplayPlatform> displayPlatformWrapper = new QueryWrapper<>();
        displayPlatformWrapper.orderByDesc("START_DATE");
        if (sort.equals("+id")){
            displayPlatformWrapper.orderByAsc("DISPLAY_PLATFORM_ID");
        }else{
            displayPlatformWrapper.orderByDesc("DISPLAY_PLATFORM_ID");
        }

        if (StringUtils.isNotEmpty(useFlag)){
            displayPlatformWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            displayPlatformWrapper.like("DISPLAY_PLATFORM_NAME",title);
        }

        IPage<DisplayPlatform> displayPlatformPage = displayPlatformService.page(new Page<>(page,limit),displayPlatformWrapper);
        data.setTotal(displayPlatformPage.getTotal());
        data.setItems(displayPlatformPage.getRecords());
        displayPlatformPageData.setData(data);
        systemLogService.saveMachineLog(token,"查询","查询了大屏列表");

        return displayPlatformPageData;
    }


    @PostMapping("create")
    @ResponseBody
    @SysLog("新增大屏数据")
    public ResponseEty create(@RequestBody DisplayPlatform displayPlatform,
                              @RequestHeader(value="token",required = false) Integer token){
        if(StringUtils.isBlank(displayPlatform.getDisplayPlatformCode())){
            return ResponseEty.failure("大屏编号不能为空");
        }
        if(StringUtils.isBlank(displayPlatform.getDisplayPlatformName())){
            return ResponseEty.failure("大屏名称不能为空");
        }
        if (displayPlatformService.displayPlatformCountByCode(displayPlatform.getDisplayPlatformCode())>0){
            return ResponseEty.failure("大屏编号已使用,请重新输入");
        }
        if (displayPlatformService.displayPlatformCountByName(displayPlatform.getDisplayPlatformName())>0){
            return ResponseEty.failure("大屏名称已使用,请重新输入");
        }
        displayPlatformService.saveDisplayPlatform(displayPlatform);
        if(displayPlatform.getDisplayPlatformId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        systemLogService.saveMachineLog(token,"新增","新增了大屏:"+displayPlatform.getDisplayPlatformName());

        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存大屏修改数据")
    public ResponseEty update(@RequestBody DisplayPlatform displayPlatform,
                              @RequestHeader(value="token",required = false) Integer token
    ){
        if(displayPlatform.getDisplayPlatformId()==null){
            return ResponseEty.failure("大屏ID不能为空");
        }
        if(StringUtils.isBlank(displayPlatform.getDisplayPlatformCode())){
            return ResponseEty.failure("大屏编号不能为空");
        }
        if(StringUtils.isBlank(displayPlatform.getDisplayPlatformName())){
            return ResponseEty.failure("大屏名称不能为空");
        }
        DisplayPlatform oldDisplayPlatform = displayPlatformService.getById(displayPlatform.getDisplayPlatformId());
        if(StringUtils.isNotBlank(displayPlatform.getDisplayPlatformCode())){
            if(!displayPlatform.getDisplayPlatformCode().equals(oldDisplayPlatform.getDisplayPlatformCode())){
                if(displayPlatformService.displayPlatformCountByCode(displayPlatform.getDisplayPlatformCode())>0){
                    return ResponseEty.failure("该大屏编码已经使用");
                }
            }
        }
        if(StringUtils.isNotBlank(displayPlatform.getDisplayPlatformName())){
            if(!displayPlatform.getDisplayPlatformName().equals(oldDisplayPlatform.getDisplayPlatformName())){
                if(displayPlatformService.displayPlatformCountByName(displayPlatform.getDisplayPlatformName())>0){
                    return ResponseEty.failure("该大屏名称已经使用");
                }
            }
        }
        displayPlatformService.updateDisplayPlatform(displayPlatform);

        if(displayPlatform.getDisplayPlatformId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        systemLogService.saveMachineLog(token,"更新","更新了大屏:"+displayPlatform.getDisplayPlatformName());

        return ResponseEty.success("操作成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除大屏数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id, @RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        DisplayPlatform displayPlatform=displayPlatformService.getById(id);
        if(displayPlatform == null){
            return ResponseEty.failure("大屏不存在");
        }
        displayPlatformService.deleteDisplayPlatform(displayPlatform);
        systemLogService.saveMachineLog(token,"删除","删除了大屏:"+displayPlatform.getDisplayPlatformName());

        return ResponseEty.success("删除成功");
    }


    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用大屏")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id, @RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        DisplayPlatform displayPlatform=displayPlatformService.getById(id);
        if(displayPlatform == null){
            return ResponseEty.failure("大屏不存在");
        }
        displayPlatformService.lockDisplayPlatform(displayPlatform);
        systemLogService.saveMachineLog(token,"禁用","禁用了大屏:"+displayPlatform.getDisplayPlatformName());

        return ResponseEty.success("操作成功");
    }
}
