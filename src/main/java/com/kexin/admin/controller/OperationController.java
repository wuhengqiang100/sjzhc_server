package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.Operation;
import com.kexin.admin.entity.tables.OperationType;
import com.kexin.admin.service.OperationService;
import com.kexin.admin.service.OperationTypeService;
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
 * 节点配置管理controller层
 */
@Controller
@RequestMapping("operation")
public class OperationController {

    @Autowired
    OperationService operationService;

    @Autowired
    OperationTypeService operationTypeService;

    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @GetMapping("list")
    @ResponseBody
    @SysLog("节点列表获取")
    public PageDataBase<Operation> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                       @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                       @RequestParam(value = "sort")String sort,
                                       @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                       @RequestParam(value = "title",defaultValue = "") String title,
                                       ServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<Operation> operationPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<Operation> operationWrapper = new QueryWrapper<>();
        if (sort.equals("+id")){
            operationWrapper.orderByAsc("OPERATION_ID");
        }else{
            operationWrapper.orderByDesc("OPERATION_ID");
        }

        if (StringUtils.isNotEmpty(useFlag)){
            operationWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            operationWrapper.like("OPERATION_NAME",title);
        }
//        if(!map.isEmpty()){
//            String useFlag = (String) map.get("useFlag");
//            if(StringUtils.isNotBlank(useFlag)) {
//                operationWrapper.eq("USE_FLAG", useFlag);
//            }
//            String keys = (String) map.get("key");
//            if(StringUtils.isNotBlank(keys)) {
//                operationWrapper.and(wrapper -> wrapper.like("OPERATION_NAME", keys));//模糊查询拼接
//            }
//        }
        IPage<Operation> operationPage = operationService.page(new Page<>(page,limit),operationWrapper);
        data.setTotal(operationPage.getTotal());
        data.setItems(setOperationTypeToOperation(operationPage.getRecords()));
        operationPageData.setData(data);
        return operationPageData;
    }

    private List<Operation> setOperationTypeToOperation(List<Operation> operations){
        operations.forEach(r -> {
            if(r.getOperationTypeId()!=null){
                OperationType operationType=operationTypeService.getById(r.getOperationTypeId());
                r.setOperationType(operationType);
            }
        });
         return operations;
    }


    @PostMapping("create")
    @ResponseBody
    @SysLog("新增节点数据")
    public ResponseEty create(@RequestBody  Operation operation){
        if(StringUtils.isBlank(operation.getOperationCode())){
            return ResponseEty.failure("节点编号不能为空");
        }
        if(StringUtils.isBlank(operation.getOperationName())){
            return ResponseEty.failure("节点名称不能为空");
        }
        if (operationService.operationCountByCode(operation.getOperationCode())>0){
            return ResponseEty.failure("节点编号已使用,请重新输入");
        }
        if (operationService.operationCountByName(operation.getOperationName())>0){
            return ResponseEty.failure("节点名称已使用,请重新输入");
        }
        operationService.saveOperation(operation);
        if(operation.getOperationId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存节点修改数据")
    public ResponseEty update(@RequestBody  Operation operation){
        if(operation.getOperationId()==null){
            return ResponseEty.failure("节点ID不能为空");
        }
        if(StringUtils.isBlank(operation.getOperationCode())){
            return ResponseEty.failure("节点编号不能为空");
        }
        if(StringUtils.isBlank(operation.getOperationName())){
            return ResponseEty.failure("节点名称不能为空");
        }
        Operation oldOperation = operationService.getById(operation.getOperationId());
        if(StringUtils.isNotBlank(operation.getOperationCode())){
            if(!operation.getOperationCode().equals(oldOperation.getOperationCode())){
                if(operationService.operationCountByCode(operation.getOperationCode())>0){
                    return ResponseEty.failure("该节点编码已经使用");
                }
            }
        }
        if(StringUtils.isNotBlank(operation.getOperationName())){
            if(!operation.getOperationName().equals(oldOperation.getOperationName())){
                if(operationService.operationCountByName(operation.getOperationName())>0){
                    return ResponseEty.failure("该节点名称已经使用");
                }
            }
        }
        operationService.updateOperation(operation);

        if(operation.getOperationId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        return ResponseEty.success("操作成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除节点数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        Operation operation=operationService.getById(id);
        if(operation == null){
            return ResponseEty.failure("节点不存在");
        }
        operationService.deleteOperation(operation);
        return ResponseEty.success("删除成功");
    }
    //
//    @RequiresPermissions("sys:user:delete")
    @PostMapping("deleteSome")
    @ResponseBody
    @SysLog("删除节点数据(多个)")
    public ResponseEty deleteSome(@RequestBody List<Operation> Operations){
        if(Operations == null || Operations.size()==0){
            return ResponseEty.failure("请选择需要删除的信息");
        }
        Operations.forEach(m -> operationService.deleteOperation(m));
        return ResponseEty.success("批量删除成功");
    }


    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用节点")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        Operation operation=operationService.getById(id);
        if(operation == null){
            return ResponseEty.failure("节点不存在");
        }
        operationService.lockOperation(operation);
        return ResponseEty.success("操作成功");
    }
}
