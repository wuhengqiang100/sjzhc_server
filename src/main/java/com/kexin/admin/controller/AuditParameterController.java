package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.AuditParameter;
import com.kexin.admin.entity.tables.Machine;
import com.kexin.admin.service.*;
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
 * 审核参数配置管理controller层
 */
@Controller
@RequestMapping("auditParameter")
public class AuditParameterController {

    @Autowired
    AuditParameterService auditParameterService;//审核参数service

    @Autowired
    AuditParameterTypeService auditParameterTypeService;//审核参数类型service
    
    @Autowired
    OperationService operationService;//审核参数service
    
    @Autowired
    ProductsService productsService;//产品servcie

    @Autowired
    MachineService machineService;//设备service
    @GetMapping("list")
    @ResponseBody
    @SysLog("审核参数列表获取")
    public PageDataBase<AuditParameter> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                       @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                       @RequestParam(value = "sort")String sort,
                                       @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                       @RequestParam(value = "title",defaultValue = "") String title,
                                       @RequestParam(value = "judgeCheckTypeId",defaultValue = "") Integer judgeCheckTypeId,
                                       @RequestParam(value = "operationId",defaultValue = "") Integer operationId,
                                       @RequestParam(value = "productId",defaultValue = "") Integer productId,
                                       @RequestParam(value = "machineId",defaultValue = "") Integer machineId,
                                       ServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<AuditParameter> auditParameterPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<AuditParameter> auditParameterWrapper = new QueryWrapper<>();
        if (sort.equals("+id")){
            auditParameterWrapper.orderByAsc("JUDGE_CHECK_ID");
        }else{
            auditParameterWrapper.orderByDesc("JUDGE_CHECK_ID");
        }

        if (StringUtils.isNotEmpty(useFlag)){
            auditParameterWrapper.eq("USE_FLAG",useFlag);
        }
        if (judgeCheckTypeId!=null){
            auditParameterWrapper.eq("JUDGE_CHECK_TYPE_ID",judgeCheckTypeId);
        }
        if (operationId!=null){
            auditParameterWrapper.eq("OPERATION_ID",operationId);
        } if (productId!=null){
            auditParameterWrapper.eq("PRODUCT_ID",productId);
        }if (machineId!=null){
            auditParameterWrapper.eq("MACHINE_ID",machineId);
        }
        IPage<AuditParameter> auditParameterPage = auditParameterService.page(new Page<>(page,limit),auditParameterWrapper);
        auditParameterPage.getRecords().forEach(r->{
                    r.setJudgeCheckType(auditParameterTypeService.getById(r.getJudgeCheckTypeId()));
                    r.setOperation(operationService.getById(r.getOperationId()));
                    r.setProducts(productsService.getById(r.getProductId()));
                    r.setMachine(machineService.getById(r.getMachineId()));
                });//外键实体添加
        data.setTotal(auditParameterPage.getTotal());
        data.setItems(auditParameterPage.getRecords());
        auditParameterPageData.setData(data);
        return auditParameterPageData;
    }




    @PostMapping("create")
    @ResponseBody
    @SysLog("新增审核参数数据")
    public ResponseEty create(@RequestBody  AuditParameter auditParameter){
        if (auditParameter.getValue()==null){
            return ResponseEty.failure("请填写参数值");
        }
        if (auditParameter.getJudgeCheckTypeId()==null){
            return ResponseEty.failure("请选择审核参数种类");
        }  if (auditParameter.getOperationId()==null){
            return ResponseEty.failure("请选择工序");
        } if (auditParameter.getProductId()==null){
            return ResponseEty.failure("请选择产品");
        }if (auditParameter.getMachineId()==null){
            return ResponseEty.failure("请选择设备");
        }
        if (auditParameterService.countParameterByTypeOperationProductMachine(auditParameter)>0){
            return ResponseEty.failure("工序->产品->设备的 审核参数,只能唯一");
        }
        auditParameterService.saveAuditParameter(auditParameter);
        if(auditParameter.getJudgeCheckId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存审核参数修改数据")
    public ResponseEty update(@RequestBody  AuditParameter auditParameter){
        if(auditParameter.getJudgeCheckId()==null){
            return ResponseEty.failure("审核参数ID不能为空");
        }
        if (auditParameter.getValue()==null){
            return ResponseEty.failure("请填写参数值");
        }
        if (auditParameter.getJudgeCheckTypeId()==null){
            return ResponseEty.failure("请选择审核参数种类");
        }  if (auditParameter.getOperationId()==null){
            return ResponseEty.failure("请选择工序");
        } if (auditParameter.getProductId()==null){
            return ResponseEty.failure("请选择产品");
        }
        if (auditParameter.getMachineId()==null){
            return ResponseEty.failure("请选择设备");
        }
        AuditParameter oldAuditParameter = auditParameterService.getById(auditParameter.getJudgeCheckId());
        if(oldAuditParameter.getOperationId()!=null && oldAuditParameter.getProductId()!=null && oldAuditParameter.getMachineId()!=null){
            if(!auditParameter.getOperationId().equals(oldAuditParameter.getOperationId()) && !auditParameter.getProductId().equals(oldAuditParameter.getProductId()) && !auditParameter.getMachineId().equals(oldAuditParameter.getMachineId())){
                if (auditParameterService.countParameterByTypeOperationProductMachine(auditParameter)>0){
                    return ResponseEty.failure("工序->产品->设备的 审核参数,只能唯一");
                }
            }
        }
        auditParameterService.updateAuditParameter(auditParameter);

        if(auditParameter.getJudgeCheckId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        return ResponseEty.success("操作成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除审核参数数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }

        AuditParameter auditParameter=auditParameterService.getById(id);
        if(auditParameter == null){
            return ResponseEty.failure("审核参数不存在");
        }
        auditParameterService.deleteAuditParameter(auditParameter);
        return ResponseEty.success("删除成功");
    }
    //
//    @RequiresPermissions("sys:user:delete")
    @PostMapping("deleteSome")
    @ResponseBody
    @SysLog("删除审核参数数据(多个)")
    public ResponseEty deleteSome(@RequestBody List<AuditParameter> AuditParameters){
        if(AuditParameters == null || AuditParameters.size()==0){
            return ResponseEty.failure("请选择需要删除的信息");
        }
        AuditParameters.forEach(m -> auditParameterService.deleteAuditParameter(m));
        return ResponseEty.success("批量删除成功");
    }


    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用审核参数")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        AuditParameter auditParameter=auditParameterService.getById(id);
        if(auditParameter == null){
            return ResponseEty.failure("审核参数不存在");
        }
        auditParameterService.lockAuditParameter(auditParameter);
        return ResponseEty.success("操作成功");
    }
}
