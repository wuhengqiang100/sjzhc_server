package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.Machine;
import com.kexin.admin.entity.tables.MachineModel;
import com.kexin.admin.entity.tables.Operation;
import com.kexin.admin.entity.vo.Ftp;
import com.kexin.admin.service.*;
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
import java.util.Map;

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

    @Autowired
    SystemLogService systemLogService;//系统日志记录service

    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @GetMapping("list")
    @ResponseBody
    @SysLog("设备模板列表获取")
    public PageDataBase<MachineModel> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                       @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                       @RequestParam(value = "sort")String sort,
                                       @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                       @RequestParam(value = "title",defaultValue = "") String title,
                                       @RequestParam(value = "operationId",defaultValue = "") Integer operationId,
                                       @RequestParam(value = "machineId",defaultValue = "") Integer machineId,
                                       @RequestParam(value = "productId",defaultValue = "") Integer productId,
                                           @RequestHeader(value="token",required = false) Integer token,

                                       HttpServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<MachineModel> machineModelPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<MachineModel> machineModelWrapper = new QueryWrapper<>();
        machineModelWrapper.orderByDesc("START_DATE");
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
        if (operationId!=null){
            machineModelWrapper.eq("OPERATION_ID",operationId);
        } if (machineId!=null){
            machineModelWrapper.eq("MACHINE_ID",machineId);
        } if (productId!=null){
            machineModelWrapper.eq("PRODUCT_ID",productId);
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
        systemLogService.saveMachineLog(token,"查询","查询了设备模板");
        return machineModelPageData;
    }



/*    @PostMapping("upload")
    @ResponseBody
    @SysLog("上传机检模板数据")
    public ResponseEty multipleSave(@RequestParam("fileUpload") MultipartFile[] file,
                                    @RequestParam("rfilename") String rfilename,
                                    @RequestParam("addId") String addId,
                                    @RequestParam("tokenId") Integer tokenId,
                                    HttpServletRequest request){

        return machineModelService.uploadTemplate(file,  rfilename,Integer.parseInt(addId),request,ftp,tokenId);
    }*/

    @PostMapping("upload1")
    @ResponseBody
    @SysLog("上传机检模板数据")
    public ResponseEty multipleSave1(@RequestParam("file") MultipartFile[] file,
                                     @RequestParam("tokenId") Integer tokenId,
                                     @RequestParam("machineModelId") Integer machineModelId,
                                     @RequestHeader(value="token",required = false) Integer token,
                                     HttpServletRequest request){

        return machineModelService.uploadTemplate1(file, machineModelId,request,tokenId,token);
    }

    @PostMapping("download")
    @ResponseBody
    @SysLog("下载机检模板数据")
    public ResponseEty download(@RequestParam(name = "id") Integer machineModelId,
                                @RequestHeader(value="token",required = false) Integer token,
                                @RequestParam("tokenId") Integer tokenId){
        return machineModelService.getDownloadUrl(machineModelId,tokenId,token);
//        return machineModelService.downloadTemplate(machineModelId);
    }
    @PostMapping("create")
    @ResponseBody
    @SysLog("新增模板数据")
    public ResponseEty create(@RequestBody  MachineModel machineModel         ,
                              @RequestHeader(value="token",required = false) Integer token
                              ){
        if(StringUtils.isBlank(machineModel.getMachineModelCode())){
            return ResponseEty.failure("模板编号不能为空");
        }
        if (machineModel.getOperationId()==null){
            return ResponseEty.failure("请选择工艺");
        }
        if(StringUtils.isBlank(machineModel.getMachineModelName())){
            return ResponseEty.failure("模板名称不能为空");
        }
        machineModel.setOperation(operationService.getById(machineModel.getOperationId()));
        if (machineModel.getMachineId()==null){
            return ResponseEty.failure("请选择设备");
        }
        machineModel.setMachine(machineService.getById(machineModel.getMachineId()));
        if (machineModel.getProductId()==null){
            return ResponseEty.failure("请选择产品");
        }
        machineModel.setProduct(productsService.getById(machineModel.getProductId()));
        if (machineModelService.machineModelCountByCode(machineModel.getMachineModelCode())>0){
            return ResponseEty.failure("模板编号已使用,请重新输入");
        }
        if (machineModelService.machineModelCountByName(machineModel.getMachineModelName())>0){
            return ResponseEty.failure("模板名称已使用,请重新输入");
        }
        if(machineModelService.machineModelCountByOperationMachineProduct(machineModel)>0){
            String message=machineModel.getOperation().getOperationName()+","+machineModel.getMachine().getMachineName()+"," +
                    ""+machineModel.getProduct().getProductName()+"的模板已经存在,不能添加";
            return ResponseEty.failure(message);
        }
        machineModelService.saveMachineModel(machineModel);
        Machine machine=machineService.getById(machineModel.getMachineId());
        systemLogService.saveMachineLog(token,"新增","新增了设备"+machine.getMachineName()+"模板");
        if(machineModel.getMachineModelId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存模板修改数据")
    public ResponseEty update(@RequestBody  MachineModel machineModel,@RequestHeader(value="token",required = false) Integer token){
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
        Machine machine=machineService.getById(machineModel.getMachineId());
        systemLogService.saveMachineLog(token,"更新","更新了设备"+machine.getMachineName()+"模板");

        if(machineModel.getMachineModelId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        return ResponseEty.success("操作成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除模板数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id,@RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        MachineModel machineModel=machineModelService.getById(id);
        if(machineModel == null){
            return ResponseEty.failure("模板不存在");
        }
        machineModelService.deleteMachineModel(machineModel);
        Machine machine=machineService.getById(machineModel.getMachineId());
        systemLogService.saveMachineLog(token,"删除","删除了设备"+machine.getMachineName()+"模板");

        return ResponseEty.success("删除成功");
    }



    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用模板")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id,@RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        MachineModel machineModel=machineModelService.getById(id);
        if(machineModel == null){
            return ResponseEty.failure("模板不存在");
        }
        machineModelService.lockMachineModel(machineModel);
        Machine machine=machineService.getById(machineModel.getMachineId());
        systemLogService.saveMachineLog(token,"禁用","禁用了设备"+machine.getMachineName()+"模板");
        return ResponseEty.success("操作成功");
    }
}
