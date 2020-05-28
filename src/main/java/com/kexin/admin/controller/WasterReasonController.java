package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.WasterReason;
import com.kexin.admin.service.OperationService;
import com.kexin.admin.service.SystemLogService;
import com.kexin.admin.service.WasterReasonService;
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
 * 错误类型配置管理controller层
 */
@Controller
@RequestMapping("wasterReason")
public class WasterReasonController {

    @Autowired
    WasterReasonService wasterReasonService;

    @Autowired
    OperationService operationService;

    @Autowired
    SystemLogService systemLogService;//系统日志记录service

    @GetMapping("list")
    @ResponseBody
    @SysLog("错误类型列表获取")
    public PageDataBase<WasterReason> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                       @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                       @RequestParam(value = "sort")String sort,
                                       @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                       @RequestParam(value = "title",defaultValue = "") String title,
                                       @RequestParam(value = "operationId",defaultValue = "") Integer operationId,
                                           @RequestHeader(value="token",required = false) Integer token,
                                       ServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<WasterReason> wasterReasonPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<WasterReason> wasterReasonWrapper = new QueryWrapper<>();
        wasterReasonWrapper.orderByDesc("START_DATE");

        if (sort.equals("+id")){
            wasterReasonWrapper.orderByAsc("WASTER_REASONS_ID");
        }else{
            wasterReasonWrapper.orderByDesc("WASTER_REASONS_ID");
        }

        if (StringUtils.isNotEmpty(useFlag)){
            wasterReasonWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            wasterReasonWrapper.like("WASTER_REASONS_NAME",title);
        }
        if (operationId!=null){
            wasterReasonWrapper.eq("OPERATION_ID",operationId);
        }

        IPage<WasterReason> wasterReasonPage = wasterReasonService.page(new Page<>(page,limit),wasterReasonWrapper);
        wasterReasonPage.getRecords().forEach(r->{
            if (r.getOperationId()!=null){
                r.setOperation(operationService.getById(r.getOperationId()));
            }
        });//外键实体添加
        data.setTotal(wasterReasonPage.getTotal());
        data.setItems(wasterReasonPage.getRecords());
        wasterReasonPageData.setData(data);
        systemLogService.saveMachineLog(token,"查询","查询了错误类型列表");

        return wasterReasonPageData;
    }




    @PostMapping("create")
    @ResponseBody
    @SysLog("新增错误类型数据")
    public ResponseEty create(@RequestBody  WasterReason wasterReason,
                              @RequestHeader(value="token",required = false) Integer token
                              ){
        if(StringUtils.isBlank(wasterReason.getWasterReasonsCode())){
            return ResponseEty.failure("错误类型编号不能为空");
        }
        if(StringUtils.isBlank(wasterReason.getWasterReasonsName())){
            return ResponseEty.failure("错误类型名称不能为空");
        }if(StringUtils.isBlank(wasterReason.getWasterReasonsIndex())){
            return ResponseEty.failure("错误类型序号不能为空");
        }
        if (wasterReason.getOperationId()==null){
            return ResponseEty.failure("请选择工序");
        }
        if (wasterReasonService.wasterReasonCountByCode(wasterReason.getWasterReasonsCode())>0){
            return ResponseEty.failure("错误类型编号已使用,请重新输入");
        }
        if (wasterReasonService.wasterReasonCountByName(wasterReason.getWasterReasonsName())>0){
            return ResponseEty.failure("错误类型名称已使用,请重新输入");
        }
        wasterReasonService.saveWasterReason(wasterReason);
        if(wasterReason.getWasterReasonsId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        systemLogService.saveMachineLog(token,"新增","新增了错误类型:"+wasterReason.getWasterReasonsName());

        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存错误类型修改数据")
    public ResponseEty update(@RequestBody  WasterReason wasterReason,@RequestHeader(value="token",required = false) Integer token){
        if(wasterReason.getWasterReasonsId()==null){
            return ResponseEty.failure("错误类型ID不能为空");
        }
        if(StringUtils.isBlank(wasterReason.getWasterReasonsCode())){
            return ResponseEty.failure("错误类型编号不能为空");
        }
        if(StringUtils.isBlank(wasterReason.getWasterReasonsName())){
            return ResponseEty.failure("错误类型名称不能为空");
        }
        if(StringUtils.isBlank(wasterReason.getWasterReasonsIndex())){
            return ResponseEty.failure("错误类型序号不能为空");
        }
        if (wasterReason.getOperationId()==null){
            return ResponseEty.failure("请选择工序");
        }
        WasterReason oldWasterReason = wasterReasonService.getById(wasterReason.getWasterReasonsId());
        if(StringUtils.isNotBlank(wasterReason.getWasterReasonsCode())){
            if(!wasterReason.getWasterReasonsCode().equals(oldWasterReason.getWasterReasonsCode())){
                if(wasterReasonService.wasterReasonCountByCode(wasterReason.getWasterReasonsCode())>0){
                    return ResponseEty.failure("该错误类型编码已经使用");
                }
            }
        }
        if(StringUtils.isNotBlank(wasterReason.getWasterReasonsName())){
            if(!wasterReason.getWasterReasonsName().equals(oldWasterReason.getWasterReasonsName())){
                if(wasterReasonService.wasterReasonCountByName(wasterReason.getWasterReasonsName())>0){
                    return ResponseEty.failure("该错误类型名称已经使用");
                }
            }
        }
        wasterReasonService.updateWasterReason(wasterReason);

        if(wasterReason.getWasterReasonsId()==null){

            return ResponseEty.failure("保存信息出错");
        }
        systemLogService.saveMachineLog(token,"更新","更新了错误类型:"+wasterReason.getWasterReasonsName());

        return ResponseEty.success("操作成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除错误类型数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id,@RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        WasterReason wasterReason=wasterReasonService.getById(id);
        if(wasterReason == null){
            return ResponseEty.failure("错误类型不存在");
        }
        wasterReasonService.deleteWasterReason(wasterReason);
        systemLogService.saveMachineLog(token,"删除","删除了错误类型:"+wasterReason.getWasterReasonsName());

        return ResponseEty.success("删除成功");
    }
    //


    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用错误类型")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id,@RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        WasterReason wasterReason=wasterReasonService.getById(id);
        if(wasterReason == null){
            return ResponseEty.failure("错误类型不存在");
        }
        wasterReasonService.lockWasterReason(wasterReason);
        systemLogService.saveMachineLog(token,"禁用","禁用了错误类型:"+wasterReason.getWasterReasonsName());

        return ResponseEty.success("操作成功");
    }
}
