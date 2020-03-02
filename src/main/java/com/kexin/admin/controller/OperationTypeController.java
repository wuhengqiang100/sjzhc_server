package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.DeviceType;
import com.kexin.admin.entity.tables.OperationType;
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
 * 节点类别配置管理controller层
 */
@Controller
@RequestMapping("operationType")
public class OperationTypeController {


    @Autowired
    OperationTypeService operationTypeService;

    @GetMapping("listOption")
    @ResponseBody
    @SysLog("节点类别options列表获取")
    public ResponseEty listopetion(){
        ResponseEty responseEty=new ResponseEty();
        QueryWrapper<OperationType> operationTypeWrapper = new QueryWrapper<>();
        responseEty.setData(operationTypeService.list(operationTypeWrapper));
        responseEty.setSuccess(20000);
        return responseEty;
    }

    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @GetMapping("list")
    @ResponseBody
    @SysLog("节点类别列表获取")
    public PageDataBase<OperationType> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                       @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                       @RequestParam(value = "sort")String sort,
                                       @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                       @RequestParam(value = "title",defaultValue = "") String title,
                                       ServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<OperationType> operationTypePageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<OperationType> operationTypeWrapper = new QueryWrapper<>();
        if (sort.equals("+id")){
            operationTypeWrapper.orderByAsc("OPERATION_TYPE_ID");
        }else{
            operationTypeWrapper.orderByDesc("OPERATION_TYPE_ID");
        }
        if (StringUtils.isNotEmpty(useFlag)){
            operationTypeWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            operationTypeWrapper.like("OPERATION_TYPE_NAME",title);
        }
//        if(!map.isEmpty()){
//            String useFlag = (String) map.get("useFlag");
//            if(StringUtils.isNotBlank(useFlag)) {
//                operationTypeWrapper.eq("USE_FLAG", useFlag);
//            }
//            String keys = (String) map.get("key");
//            if(StringUtils.isNotBlank(keys)) {
//                operationTypeWrapper.and(wrapper -> wrapper.like("OPERATION_NAME", keys));//模糊查询拼接
//            }
//        }
        IPage<OperationType> operationTypePage = operationTypeService.page(new Page<>(page,limit),operationTypeWrapper);
        data.setTotal(operationTypePage.getTotal());
        data.setItems(operationTypePage.getRecords());
        operationTypePageData.setData(data);
        return operationTypePageData;
    }


    @PostMapping("create")
    @ResponseBody
    @SysLog("新增节点类别数据")
    public ResponseEty create(@RequestBody  OperationType operationType){
        if(StringUtils.isBlank(operationType.getOperationTypeCode())){
            return ResponseEty.failure("节点类别编号不能为空");
        }
        if(StringUtils.isBlank(operationType.getOperationTypeName())){
            return ResponseEty.failure("节点类别名称不能为空");
        }
    /*    if (operationType.getUseOperationTypeWasteNoJudge()<0 || operationType.getUseOperationTypeWasteNoJudge() >1){
            return ResponseEty.failure("节点类别的机检严重废人工干预标志,只能为0,1");
        }*/
        if (operationTypeService.operationTypeCountByCode(operationType.getOperationTypeCode())>0){
            return ResponseEty.failure("节点类别编号已使用,请重新输入");
        }
        if (operationTypeService.operationTypeCountByName(operationType.getOperationTypeName())>0){
            return ResponseEty.failure("节点类别名称已使用,请重新输入");
        }
        operationTypeService.saveOperationType(operationType);
        if(operationType.getOperationTypeId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存节点类别修改数据")
    public ResponseEty update(@RequestBody  OperationType operationType){
        if(operationType.getOperationTypeId()==null){
            return ResponseEty.failure("节点类别ID不能为空");
        }
        if(StringUtils.isBlank(operationType.getOperationTypeCode())){
            return ResponseEty.failure("节点类别编号不能为空");
        }
        if(StringUtils.isBlank(operationType.getOperationTypeName())){
            return ResponseEty.failure("节点类别名称不能为空");
        }
/*        if (operationType.getUseOperationTypeWasteNoJudge()<0 || operationType.getUseOperationTypeWasteNoJudge() >1){
            return ResponseEty.failure("节点类别的机检严重废人工干预标志,只能为0,1");
        }*/
        OperationType oldOperationType = operationTypeService.getById(operationType.getOperationTypeId());
        if(StringUtils.isNotBlank(operationType.getOperationTypeCode())){
            if(!operationType.getOperationTypeCode().equals(oldOperationType.getOperationTypeCode())){
                if(operationTypeService.operationTypeCountByCode(operationType.getOperationTypeCode())>0){
                    return ResponseEty.failure("该节点类别编码已经使用");
                }
            }
        }
        if(StringUtils.isNotBlank(operationType.getOperationTypeName())){
            if(!operationType.getOperationTypeName().equals(oldOperationType.getOperationTypeName())){
                if(operationTypeService.operationTypeCountByName(operationType.getOperationTypeName())>0){
                    return ResponseEty.failure("该节点类别名称已经使用");
                }
            }
        }
        operationTypeService.updateOperationType(operationType);

        if(operationType.getOperationTypeId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        return ResponseEty.success("操作成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除节点类别数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        OperationType operationType=operationTypeService.getById(id);
        if(operationType == null){
            return ResponseEty.failure("节点类别不存在");
        }
        operationTypeService.deleteOperationType(operationType);
        return ResponseEty.success("删除成功");
    }
    //
//    @RequiresPermissions("sys:user:delete")
    @PostMapping("deleteSome")
    @ResponseBody
    @SysLog("删除节点类别数据(多个)")
    public ResponseEty deleteSome(@RequestBody List<OperationType> OperationTypes){
        if(OperationTypes == null || OperationTypes.size()==0){
            return ResponseEty.failure("请选择需要删除的信息");
        }
        OperationTypes.forEach(m -> operationTypeService.deleteOperationType(m));
        return ResponseEty.success("批量删除成功");
    }


    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用节点类别")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        OperationType operationType=operationTypeService.getById(id);
        if(operationType == null){
            return ResponseEty.failure("节点类别不存在");
        }
        operationTypeService.lockOperationType(operationType);
        return ResponseEty.success("操作成功");
    }
}
