package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.DeviceType;
import com.kexin.admin.entity.tables.Operator;
import com.kexin.admin.service.OperatorService;
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
 * 人员配置管理controller层
 */
@Controller
@RequestMapping("operator")
public class OperatorController {


    @Autowired
    OperatorService operatorService;

  /*  @GetMapping("listOption")
    @ResponseBody
    @SysLog("人员options列表获取")
    public ResponseEty listopetion(){
        ResponseEty responseEty=new ResponseEty();
        QueryWrapper<Operator> operatorWrapper = new QueryWrapper<>();
        responseEty.setData(operatorService.list(operatorWrapper));
        responseEty.setSuccess(20000);
        return responseEty;
    }
*/
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @GetMapping("list")
    @ResponseBody
    @SysLog("人员列表获取")
    public PageDataBase<Operator> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                            @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                            @RequestParam(value = "sort")String sort,
                                            @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                            @RequestParam(value = "title",defaultValue = "") String title,
                                            ServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<Operator> operatorPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<Operator> operatorWrapper = new QueryWrapper<>();
        if (sort.equals("+id")){
            operatorWrapper.orderByAsc("OPERATOR_ID");
        }else{
            operatorWrapper.orderByDesc("OPERATOR_ID");
        }
        if (StringUtils.isNotEmpty(useFlag)){
            operatorWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            operatorWrapper.like("OPERATOR_NAME",title);
        }
        IPage<Operator> operatorPage = operatorService.page(new Page<>(page,limit),operatorWrapper);
        data.setTotal(operatorPage.getTotal());
        data.setItems(operatorPage.getRecords());
        operatorPageData.setData(data);
        return operatorPageData;
    }


    @PostMapping("create")
    @ResponseBody
    @SysLog("新增人员数据")
    public ResponseEty create(@RequestBody  Operator operator){
        if(StringUtils.isBlank(operator.getOperatorCode())){
            return ResponseEty.failure("人员编号不能为空");
        }
        if(StringUtils.isBlank(operator.getOperatorName())){
            return ResponseEty.failure("人员名称不能为空");
        }
        if (operatorService.operatorCountByCode(operator.getOperatorCode())>0){
            return ResponseEty.failure("人员编号已使用,请重新输入");
        }
        if (operatorService.operatorCountByName(operator.getOperatorName())>0){
            return ResponseEty.failure("人员名称已使用,请重新输入");
        }
        operatorService.saveOperator(operator);
        if(operator.getOperatorId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存人员修改数据")
    public ResponseEty update(@RequestBody  Operator operator){
        if(operator.getOperatorId()==null){
            return ResponseEty.failure("人员ID不能为空");
        }
        if(StringUtils.isBlank(operator.getOperatorCode())){
            return ResponseEty.failure("人员编号不能为空");
        }
        if(StringUtils.isBlank(operator.getOperatorName())){
            return ResponseEty.failure("人员名称不能为空");
        }
        Operator oldOperator = operatorService.getById(operator.getOperatorId());
        if(StringUtils.isNotBlank(operator.getOperatorCode())){
            if(!operator.getOperatorCode().equals(oldOperator.getOperatorCode())){
                if(operatorService.operatorCountByCode(operator.getOperatorCode())>0){
                    return ResponseEty.failure("该人员编码已经使用");
                }
            }
        }
        if(StringUtils.isNotBlank(operator.getOperatorName())){
            if(!operator.getOperatorName().equals(oldOperator.getOperatorName())){
                if(operatorService.operatorCountByName(operator.getOperatorName())>0){
                    return ResponseEty.failure("该人员名称已经使用");
                }
            }
        }
        operatorService.updateOperator(operator);

        if(operator.getOperatorId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        return ResponseEty.success("操作成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除人员数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        Operator operator=operatorService.getById(id);
        if(operator == null){
            return ResponseEty.failure("人员不存在");
        }
        operatorService.deleteOperator(operator);
        return ResponseEty.success("删除成功");
    }
    //
//    @RequiresPermissions("sys:user:delete")
    @PostMapping("deleteSome")
    @ResponseBody
    @SysLog("删除人员数据(多个)")
    public ResponseEty deleteSome(@RequestBody List<Operator> Operators){
        if(Operators == null || Operators.size()==0){
            return ResponseEty.failure("请选择需要删除的信息");
        }
        Operators.forEach(m -> operatorService.deleteOperator(m));
        return ResponseEty.success("批量删除成功");
    }


    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用人员")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        Operator operator=operatorService.getById(id);
        if(operator == null){
            return ResponseEty.failure("人员不存在");
        }
        operatorService.lockOperator(operator);
        return ResponseEty.success("操作成功");
    }
}
