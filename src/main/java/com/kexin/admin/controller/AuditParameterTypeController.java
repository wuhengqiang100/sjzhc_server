package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.AuditParameterType;
import com.kexin.admin.service.AuditParameterTypeService;
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
 * 审核参数种类配置管理controller层
 */
@Controller
@RequestMapping("auditParameterType")
public class AuditParameterTypeController {

    @Autowired
    AuditParameterTypeService auditParameterTypeService;

    @Autowired
    SystemLogService systemLogService;//系统日志记录service


    @GetMapping("list")
    @ResponseBody
    @SysLog("审核参数种类列表获取")
    public PageDataBase<AuditParameterType> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                       @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                       @RequestParam(value = "sort")String sort,
                                       @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                       @RequestParam(value = "title",defaultValue = "") String title,
                                       @RequestHeader(value="token",required = false) Integer token
                                       ){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<AuditParameterType> auditParameterTypePageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<AuditParameterType> auditParameterTypeWrapper = new QueryWrapper<>();
        auditParameterTypeWrapper.orderByDesc("START_DATE");
        if (sort.equals("+id")){
            auditParameterTypeWrapper.orderByAsc("JUDGE_CHECK_TYPE_ID");
        }else{
            auditParameterTypeWrapper.orderByDesc("JUDGE_CHECK_TYPE_ID");
        }

        if (StringUtils.isNotEmpty(useFlag)){
            auditParameterTypeWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            auditParameterTypeWrapper.like("JUDGE_CHECK_TYPE_NAME",title);
        }

        IPage<AuditParameterType> auditParameterTypePage = auditParameterTypeService.page(new Page<>(page,limit),auditParameterTypeWrapper);
        data.setTotal(auditParameterTypePage.getTotal());
        data.setItems(auditParameterTypePage.getRecords());
        auditParameterTypePageData.setData(data);
        systemLogService.saveMachineLog(token,"查询","查询了审核参数类型");
        return auditParameterTypePageData;
    }

    @GetMapping("listOption")
    @ResponseBody
    @SysLog("审核参数类别options列表获取")
    public ResponseEty listopetion(){
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("auditParameterTypeOptions",auditParameterTypeService.getAuditParameterTypeSelectOption());
        return responseEty;
    }


    @PostMapping("create")
    @ResponseBody
    @SysLog("新增审核参数种类数据")
    public ResponseEty create(@RequestBody  AuditParameterType auditParameterType,@RequestHeader(value="token",required = false) Integer token){
        if(StringUtils.isBlank(auditParameterType.getJudgeCheckTypeCode())){
            return ResponseEty.failure("审核参数种类编号不能为空");
        }
        if(StringUtils.isBlank(auditParameterType.getJudgeCheckTypeName())){
            return ResponseEty.failure("审核参数种类名称不能为空");
        }
        if (auditParameterTypeService.auditParameterTypeCountByCode(auditParameterType.getJudgeCheckTypeCode())>0){
            return ResponseEty.failure("审核参数种类编号已使用,请重新输入");
        }
        if (auditParameterTypeService.auditParameterTypeCountByName(auditParameterType.getJudgeCheckTypeName())>0){
            return ResponseEty.failure("审核参数种类名称已使用,请重新输入");
        }
        auditParameterTypeService.saveAuditParameterType(auditParameterType);
        if(auditParameterType.getJudgeCheckTypeId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        systemLogService.saveMachineLog(token,"新增","新增了审核参数类型:"+auditParameterType.getJudgeCheckTypeName());
        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存审核参数种类修改数据")
    public ResponseEty update(@RequestBody  AuditParameterType auditParameterType,@RequestHeader(value="token",required = false) Integer token){
        if(auditParameterType.getJudgeCheckTypeId()==null){
            return ResponseEty.failure("审核参数种类ID不能为空");
        }
        if(StringUtils.isBlank(auditParameterType.getJudgeCheckTypeCode())){
            return ResponseEty.failure("审核参数种类编号不能为空");
        }
        if(StringUtils.isBlank(auditParameterType.getJudgeCheckTypeName())){
            return ResponseEty.failure("审核参数种类名称不能为空");
        }
        AuditParameterType oldAuditParameterType = auditParameterTypeService.getById(auditParameterType.getJudgeCheckTypeId());
        if(StringUtils.isNotBlank(auditParameterType.getJudgeCheckTypeCode())){
            if(!auditParameterType.getJudgeCheckTypeCode().equals(oldAuditParameterType.getJudgeCheckTypeCode())){
                if(auditParameterTypeService.auditParameterTypeCountByCode(auditParameterType.getJudgeCheckTypeCode())>0){
                    return ResponseEty.failure("该审核参数种类编码已经使用");
                }
            }
        }
        if(StringUtils.isNotBlank(auditParameterType.getJudgeCheckTypeName())){
            if(!auditParameterType.getJudgeCheckTypeName().equals(oldAuditParameterType.getJudgeCheckTypeName())){
                if(auditParameterTypeService.auditParameterTypeCountByName(auditParameterType.getJudgeCheckTypeName())>0){
                    return ResponseEty.failure("该审核参数种类名称已经使用");
                }
            }
        }
        auditParameterTypeService.updateAuditParameterType(auditParameterType);

        if(auditParameterType.getJudgeCheckTypeId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        systemLogService.saveMachineLog(token,"更新","更新了审核参数类型:"+auditParameterType.getJudgeCheckTypeName());
        return ResponseEty.success("操作成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除审核参数种类数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id,@RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        AuditParameterType auditParameterType=auditParameterTypeService.getById(id);
        if(auditParameterType == null){
            return ResponseEty.failure("审核参数种类不存在");
        }
        QueryWrapper<AuditParameterType> auditParameterTypeQueryWrapper=new QueryWrapper<>();
        auditParameterTypeQueryWrapper.eq("JUDGE_CHECK_TYPE_ID",id);
        if (auditParameterTypeService.count(auditParameterTypeQueryWrapper)>0){
            return ResponseEty.failure("当前参数种类有参数数据,不能删除");
        }
        auditParameterTypeService.deleteAuditParameterType(auditParameterType);
        systemLogService.saveMachineLog(token,"删除","删除了审核参数类型:"+auditParameterType.getJudgeCheckTypeName());
        return ResponseEty.success("删除成功");
    }
    //


    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用审核参数种类")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id,@RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        AuditParameterType auditParameterType=auditParameterTypeService.getById(id);
        if(auditParameterType == null){
            return ResponseEty.failure("审核参数种类不存在");
        }
        auditParameterTypeService.lockAuditParameterType(auditParameterType);
        systemLogService.saveMachineLog(token,"禁用","禁用了审核参数类型:"+auditParameterType.getJudgeCheckTypeName());

        return ResponseEty.success("操作成功");
    }
}
