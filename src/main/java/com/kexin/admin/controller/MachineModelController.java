package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.MachineModel;
import com.kexin.admin.entity.tables.Operation;
import com.kexin.admin.entity.vo.Ftp;
import com.kexin.admin.service.MachineModelService;
import com.kexin.admin.service.MachineService;
import com.kexin.admin.service.OperationService;
import com.kexin.admin.service.ProductsService;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.base.Data;
import com.kexin.common.base.PageDataBase;
import com.kexin.common.util.ResponseEty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * 机器模板上传下载controller
 */
@Controller
@RequestMapping("/machine/machineModel")
public class MachineModelController {

    @Autowired
    MachineModelService machineModelService; // 设备模板service

    @Autowired
    MachineService machineService;// 设备service

    @Autowired
    OperationService operationService; // 工序service

    @Autowired
    ProductsService productsService; // 产品service


    @Autowired
    Ftp ftp;

    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @GetMapping("list")
    @ResponseBody
    @SysLog("设备模板列表获取")
    public PageDataBase<MachineModel> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                       @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                       @RequestParam(value = "sort")String sort,
                                       @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                       @RequestParam(value = "title",defaultValue = "") String title,
                                       ServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<MachineModel> machineModelPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<MachineModel> machineModelWrapper = new QueryWrapper<>();
        if (sort.equals("+id")){
            machineModelWrapper.orderByAsc("MACHINE_MODEL_ID");
        }else{
            machineModelWrapper.orderByDesc("MACHINE_MODEL_ID");
        }

        if (StringUtils.isNotEmpty(useFlag)){
            machineModelWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            machineModelWrapper.like("MACHINE_MODEL_NAME",title);
        }

        IPage<MachineModel> machineModelPage = machineModelService.page(new Page<>(page,limit),machineModelWrapper);
        //外键实体添加
        machineModelPage.getRecords().forEach(r->{
            r.setMachine(machineService.getById(r.getMachineId()));
            r.setOperation(operationService.getById(r.getOperationId()));
            r.setProduct(productsService.getById(r.getProductId()));
        });
        data.setTotal(machineModelPage.getTotal());
        data.setItems(machineModelPage.getRecords());
        machineModelPageData.setData(data);
        return machineModelPageData;
    }



    @PostMapping("upload")
    @ResponseBody
    @SysLog("上传机检模板数据")
    public ResponseEty multipleSave(@RequestParam("fileUpload") MultipartFile[] file,
                                    @RequestParam("rfilename") String rfilename,
                                    @RequestParam("addId") String addId,
                                    @RequestParam("tokenId") Integer tokenId,
                                    HttpServletRequest request){

        return machineModelService.uploadTemplate(file,  rfilename,Integer.parseInt(addId),request,ftp,tokenId);
    }

    @PostMapping("download")
    @ResponseBody
    @SysLog("下载机检模板数据")
    public ResponseEty download(@RequestParam(name = "id") Integer machineModelId,
                                @RequestParam("tokenId") Integer tokenId){
        return machineModelService.getDownloadUrl(machineModelId,tokenId);
//        return machineModelService.downloadTemplate(machineModelId);
    }
    @PostMapping("create")
    @ResponseBody
    @SysLog("新增模板数据")
    public ResponseEty create(@RequestBody  MachineModel machineModel){
        if(StringUtils.isBlank(machineModel.getMachineModelCode())){
            return ResponseEty.failure("模板编号不能为空");
        }
        if(StringUtils.isBlank(machineModel.getMachineModelName())){
            return ResponseEty.failure("模板名称不能为空");
        }
        if (machineModel.getMachineId()==null){
            return ResponseEty.failure("请选择设备");
        }
        if (machineModel.getOperationId()==null){
            return ResponseEty.failure("请选择工艺");
        }
        if (machineModel.getProductId()==null){
            return ResponseEty.failure("请选择产品");
        }
        if (machineModelService.machineModelCountByCode(machineModel.getMachineModelCode())>0){
            return ResponseEty.failure("模板编号已使用,请重新输入");
        }
        if (machineModelService.machineModelCountByName(machineModel.getMachineModelName())>0){
            return ResponseEty.failure("模板名称已使用,请重新输入");
        }
        machineModelService.saveMachineModel(machineModel);
        if(machineModel.getMachineModelId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存模板修改数据")
    public ResponseEty update(@RequestBody  MachineModel machineModel){
        if(machineModel.getMachineModelId()==null){
            return ResponseEty.failure("模板ID不能为空");
        }
        if(StringUtils.isBlank(machineModel.getMachineModelCode())){
            return ResponseEty.failure("模板编号不能为空");
        }
        if(StringUtils.isBlank(machineModel.getMachineModelName())){
            return ResponseEty.failure("模板名称不能为空");
        }
        if (machineModel.getMachineId()==null){
            return ResponseEty.failure("请选择设备");
        }
        if (machineModel.getOperationId()==null){
            return ResponseEty.failure("请选择工艺");
        }
        if (machineModel.getProductId()==null){
            return ResponseEty.failure("请选择产品");
        }
        MachineModel oldMachineModel = machineModelService.getById(machineModel.getMachineModelId());
        if(StringUtils.isNotBlank(machineModel.getMachineModelCode())){
            if(!machineModel.getMachineModelCode().equals(oldMachineModel.getMachineModelCode())){
                if(machineModelService.machineModelCountByCode(machineModel.getMachineModelCode())>0){
                    return ResponseEty.failure("该模板编码已经使用");
                }
            }
        }
        if(StringUtils.isNotBlank(machineModel.getMachineModelName())){
            if(!machineModel.getMachineModelName().equals(oldMachineModel.getMachineModelName())){
                if(machineModelService.machineModelCountByName(machineModel.getMachineModelName())>0){
                    return ResponseEty.failure("该模板名称已经使用");
                }
            }
        }
        machineModelService.updateMachineModel(machineModel);

        if(machineModel.getMachineModelId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        return ResponseEty.success("操作成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除模板数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        MachineModel machineModel=machineModelService.getById(id);
        if(machineModel == null){
            return ResponseEty.failure("模板不存在");
        }
        machineModelService.deleteMachineModel(machineModel);
        return ResponseEty.success("删除成功");
    }
    //


    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用模板")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        MachineModel machineModel=machineModelService.getById(id);
        if(machineModel == null){
            return ResponseEty.failure("模板不存在");
        }
        machineModelService.lockMachineModel(machineModel);
        return ResponseEty.success("操作成功");
    }
}
