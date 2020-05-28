package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.WorkUnit;
import com.kexin.admin.service.MachineLogService;
import com.kexin.admin.service.OperatorService;
import com.kexin.admin.service.SystemLogService;
import com.kexin.admin.service.WorkUnitService;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.base.Data;
import com.kexin.common.base.PageDataBase;
import com.kexin.common.util.ResponseEty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 机台配置管理controller层
 */
@Controller
@RequestMapping("workUnit")
public class WorkUnitController {

    @Autowired
    WorkUnitService workUnitService;

    @Autowired
    OperatorService operatorService;

    @Autowired
    MachineLogService machineLogService;

    @Autowired
    HttpServletRequest req;

    @Autowired
    SystemLogService systemLogService;//系统日志记录service


    @GetMapping("list")
    @ResponseBody
    @SysLog("机台列表获取")
    public PageDataBase<WorkUnit> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                       @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                       @RequestParam(value = "sort")String sort,
                                       @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                       @RequestParam(value = "title",defaultValue = "") String title,
                                       @RequestParam(value = "operatorId",defaultValue = "") Integer operatorId,
                                       @RequestHeader(value="token",required = false) Integer token,
                                       ServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<WorkUnit> workUnitPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<WorkUnit> workUnitWrapper = new QueryWrapper<>();
        workUnitWrapper.orderByDesc("START_DATE");

        if (sort.equals("+id")){
            workUnitWrapper.orderByAsc("WORK_UNIT_ID");
        }else{
            workUnitWrapper.orderByDesc("WORK_UNIT_ID");
        }

        if (StringUtils.isNotEmpty(useFlag)){
            workUnitWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            workUnitWrapper.like("WORK_UNIT_NAME",title);
        }
        if (operatorId!=null){
            workUnitWrapper.eq("OPERATOR_ID",operatorId);

        }
        IPage<WorkUnit> workUnitPage = workUnitService.page(new Page<>(page,limit),workUnitWrapper);
        workUnitPage.getRecords().forEach(r->{
            if (r.getOperatorId()!=null){
                r.setOperator(operatorService.getById(r.getOperatorId()));
            }
        });//外键实体添加
        data.setTotal(workUnitPage.getTotal());
        data.setItems(workUnitPage.getRecords());
        workUnitPageData.setData(data);
        systemLogService.saveMachineLog(token,"查询","查询了机台列表");

        return workUnitPageData;
    }




    @PostMapping("create")
    @ResponseBody
    @SysLog("新增机台数据")
    public ResponseEty create(@RequestBody  WorkUnit workUnit,
                              @RequestHeader(value="token",required = false) Integer token
                              ){
        if(StringUtils.isBlank(workUnit.getWorkUnitCode())){
            return ResponseEty.failure("机台编号不能为空");
        }
        if(StringUtils.isBlank(workUnit.getWorkUnitName())){
            return ResponseEty.failure("机台名称不能为空");
        }

        if (workUnitService.workUnitCountByCode(workUnit.getWorkUnitCode())>0){
            return ResponseEty.failure("机台编号已使用,请重新输入");
        }
        if (workUnitService.workUnitCountByName(workUnit.getWorkUnitName())>0){
            return ResponseEty.failure("机台名称已使用,请重新输入");
        }
        workUnitService.saveWorkUnit(workUnit);
        if (token==null){
            return ResponseEty.reLogin();//重新登录
        }


        machineLogService.saveMachineLog(workUnit, token);
        if(workUnit.getWorkUnitId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存机台修改数据")
    public ResponseEty update(@RequestBody  WorkUnit workUnit,
                              @RequestHeader(value="token",required = false) Integer token,
                              HttpServletRequest request

                              ){
        if(workUnit.getWorkUnitId()==null){
            return ResponseEty.failure("机台ID不能为空");
        }
        if(StringUtils.isBlank(workUnit.getWorkUnitCode())){
            return ResponseEty.failure("机台编号不能为空");
        }
        if(StringUtils.isBlank(workUnit.getWorkUnitName())){
            return ResponseEty.failure("机台名称不能为空");
        }
        WorkUnit oldWorkUnit = workUnitService.getById(workUnit.getWorkUnitId());
        if(StringUtils.isNotBlank(workUnit.getWorkUnitCode())){
            if(!workUnit.getWorkUnitCode().equals(oldWorkUnit.getWorkUnitCode())){
                if(workUnitService.workUnitCountByCode(workUnit.getWorkUnitCode())>0){
                    return ResponseEty.failure("该机台编码已经使用");
                }
            }
        }
        if(StringUtils.isNotBlank(workUnit.getWorkUnitName())){
            if(!workUnit.getWorkUnitName().equals(oldWorkUnit.getWorkUnitName())){
                if(workUnitService.workUnitCountByName(workUnit.getWorkUnitName())>0){
                    return ResponseEty.failure("该机台名称已经使用");
                }
            }
        }
        workUnitService.updateWorkUnit(workUnit);
        if (token==null){
            return ResponseEty.reLogin();//重新登录
        }

        machineLogService.updateMachineLog(workUnit, token);
        if(workUnit.getWorkUnitId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        return ResponseEty.success("操作成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除机台数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id,
                              @RequestHeader(value="token",required = false) Integer token
                              ){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        WorkUnit workUnit=workUnitService.getById(id);
        if(workUnit == null){
            return ResponseEty.failure("机台不存在");
        }
        workUnitService.deleteWorkUnit(workUnit);
        if (token==null){
            return ResponseEty.reLogin();//重新登录
        }
        machineLogService.deleteMachineLog(workUnit, token);
        return ResponseEty.success("删除成功");
    }



    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用机台")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id,@RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        WorkUnit workUnit=workUnitService.getById(id);
        if(workUnit == null){
            return ResponseEty.failure("机台不存在");
        }
        workUnitService.lockWorkUnit(workUnit);
        return ResponseEty.success("操作成功");
    }
}
