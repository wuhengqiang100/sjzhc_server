package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.ErrorType;
import com.kexin.admin.service.ErrorTypeService;
import com.kexin.admin.service.OperationService;
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
 * 错误类型配置管理controller层
 */
@Controller
@RequestMapping("errorType")
public class ErrorTypeController {

    @Autowired
    ErrorTypeService errorTypeService;

    @Autowired
    OperationService operationService;

    @GetMapping("list")
    @ResponseBody
    @SysLog("错误类型列表获取")
    public PageDataBase<ErrorType> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                       @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                       @RequestParam(value = "sort")String sort,
                                       @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                       @RequestParam(value = "title",defaultValue = "") String title,
                                       @RequestParam(value = "operationId",defaultValue = "") Integer operationId,

                                       ServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<ErrorType> errorTypePageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<ErrorType> errorTypeWrapper = new QueryWrapper<>();
        if (sort.equals("+id")){
            errorTypeWrapper.orderByAsc("ERR_TYPE_NOTE_ID");
        }else{
            errorTypeWrapper.orderByDesc("ERR_TYPE_NOTE_ID");
        }

        if (StringUtils.isNotEmpty(useFlag)){
            errorTypeWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            errorTypeWrapper.like("ERR_TYPE_NOTE_NAME",title);
        }
        if (operationId!=null){
            errorTypeWrapper.eq("OPERATION_ID",operationId);
        }

        IPage<ErrorType> errorTypePage = errorTypeService.page(new Page<>(page,limit),errorTypeWrapper);
        errorTypePage.getRecords().forEach(r->{
            if (r.getOperationId()!=null){
                r.setOperation(operationService.getById(r.getOperationId()));
            }
        });//外键实体添加
        data.setTotal(errorTypePage.getTotal());
        data.setItems(errorTypePage.getRecords());
        errorTypePageData.setData(data);
        return errorTypePageData;
    }




    @PostMapping("create")
    @ResponseBody
    @SysLog("新增错误类型数据")
    public ResponseEty create(@RequestBody  ErrorType errorType){
        if(StringUtils.isBlank(errorType.getErrTypeNoteCode())){
            return ResponseEty.failure("错误类型编号不能为空");
        }
        if(StringUtils.isBlank(errorType.getErrTypeNoteName())){
            return ResponseEty.failure("错误类型名称不能为空");
        }
        if (errorType.getOperationId()==null){
            return ResponseEty.failure("请选择工序");
        }
        if (errorTypeService.errorTypeCountByCode(errorType.getErrTypeNoteCode())>0){
            return ResponseEty.failure("错误类型编号已使用,请重新输入");
        }
        if (errorTypeService.errorTypeCountByName(errorType.getErrTypeNoteName())>0){
            return ResponseEty.failure("错误类型名称已使用,请重新输入");
        }
        errorTypeService.saveErrorType(errorType);
        if(errorType.getErrTypeNoteId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存错误类型修改数据")
    public ResponseEty update(@RequestBody  ErrorType errorType){
        if(errorType.getErrTypeNoteId()==null){
            return ResponseEty.failure("错误类型ID不能为空");
        }
        if(StringUtils.isBlank(errorType.getErrTypeNoteCode())){
            return ResponseEty.failure("错误类型编号不能为空");
        }
        if(StringUtils.isBlank(errorType.getErrTypeNoteName())){
            return ResponseEty.failure("错误类型名称不能为空");
        }
        if (errorType.getOperationId()==null){
            return ResponseEty.failure("请选择工序");
        }
        ErrorType oldErrorType = errorTypeService.getById(errorType.getErrTypeNoteId());
        if(StringUtils.isNotBlank(errorType.getErrTypeNoteCode())){
            if(!errorType.getErrTypeNoteCode().equals(oldErrorType.getErrTypeNoteCode())){
                if(errorTypeService.errorTypeCountByCode(errorType.getErrTypeNoteCode())>0){
                    return ResponseEty.failure("该错误类型编码已经使用");
                }
            }
        }
        if(StringUtils.isNotBlank(errorType.getErrTypeNoteName())){
            if(!errorType.getErrTypeNoteName().equals(oldErrorType.getErrTypeNoteName())){
                if(errorTypeService.errorTypeCountByName(errorType.getErrTypeNoteName())>0){
                    return ResponseEty.failure("该错误类型名称已经使用");
                }
            }
        }
        errorTypeService.updateErrorType(errorType);

        if(errorType.getErrTypeNoteId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        return ResponseEty.success("操作成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除错误类型数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        ErrorType errorType=errorTypeService.getById(id);
        if(errorType == null){
            return ResponseEty.failure("错误类型不存在");
        }
        errorTypeService.deleteErrorType(errorType);
        return ResponseEty.success("删除成功");
    }
    //


    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用错误类型")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        ErrorType errorType=errorTypeService.getById(id);
        if(errorType == null){
            return ResponseEty.failure("错误类型不存在");
        }
        errorTypeService.lockErrorType(errorType);
        return ResponseEty.success("操作成功");
    }
}
