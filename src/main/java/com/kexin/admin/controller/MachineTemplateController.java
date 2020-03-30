package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.Machine;
import com.kexin.admin.entity.vo.Ftp;
import com.kexin.admin.service.MachineService;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.base.Data;
import com.kexin.common.base.PageDataBase;
import com.kexin.common.util.FileUtil.FileUtil;
import com.kexin.common.util.ResponseEty;
import com.kexin.common.util.ftpUtil.FTPUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 机器模板上传下载controller
 */
@Controller
@RequestMapping("/machine/template")
public class MachineTemplateController {

    @Autowired
    MachineService machineService;

    @Autowired
    Ftp ftp;

    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @GetMapping("list")
    @ResponseBody
    @SysLog("设备模板列表获取")
    public PageDataBase<Machine> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                       @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                       @RequestParam(value = "sort")String sort,
                                       @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                       @RequestParam(value = "title",defaultValue = "") String title,
                                       ServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<Machine> machinePageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<Machine> machineWrapper = new QueryWrapper<>();
        if (sort.equals("+id")){
            machineWrapper.orderByAsc("MACHINE_ID");
        }else{
            machineWrapper.orderByDesc("MACHINE_ID");
        }

        if (StringUtils.isNotEmpty(useFlag)){
            machineWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            machineWrapper.like("MACHINE_NAME",title);
        }
//        if(!map.isEmpty()){
//            String useFlag = (String) map.get("useFlag");
//            if(StringUtils.isNotBlank(useFlag)) {
//                machineWrapper.eq("USE_FLAG", useFlag);
//            }
//            String keys = (String) map.get("key");
//            if(StringUtils.isNotBlank(keys)) {
//                machineWrapper.and(wrapper -> wrapper.like("MACHINE_NAME", keys));//模糊查询拼接
//            }
//        }
        IPage<Machine> machinePage = machineService.page(new Page<>(page,limit),machineWrapper);
        data.setTotal(machinePage.getTotal());
        data.setItems(machinePage.getRecords());
        machinePageData.setData(data);
        return machinePageData;
    }



/*    private List<Machine> setMachineTypeToMachine(List<Machine> machines){
        machines.forEach(r -> {
            if(r.getMachineTypeId()!=null){
                MachineType machineType=machineTypeService.getById(r.getMachineTypeId());
                r.setMachineType(machineType);
            }
        });
         return machines;
    }*/
    @PostMapping("upload")
    @ResponseBody
    @SysLog("上传机检模板数据")
    public ResponseEty multipleSave(@RequestParam("fileUpload") MultipartFile[] file,
                                    @RequestParam("rfilename") String rfilename,
                                    @RequestParam("addId") String addId,
                                    HttpServletRequest request){

        return machineService.uploadTemplate(file,  rfilename,Integer.parseInt(addId),request,ftp);
    }

    @PostMapping("download")
    @ResponseBody
    @SysLog("下载机检模板数据")
    public ResponseEty download(@RequestParam(name = "id") Integer machineId){
        return machineService.getDownloadUrl(machineId);
//        return machineService.downloadTemplate(machineId);
    }
/*    @PostMapping("create")
    @ResponseBody
    @SysLog("新增设备数据")
    public ResponseEty create(@RequestBody  Machine machine){
        if(StringUtils.isBlank(machine.getMachineCode())){
            return ResponseEty.failure("设备编号不能为空");
        }
        if(StringUtils.isBlank(machine.getMachineName())){
            return ResponseEty.failure("设备名称不能为空");
        }
        if (machineService.machineCountByCode(machine.getMachineCode())>0){
            return ResponseEty.failure("设备编号已使用,请重新输入");
        }
        if (machineService.machineCountByName(machine.getMachineName())>0){
            return ResponseEty.failure("设备名称已使用,请重新输入");
        }
        machineService.saveMachine(machine);
        if(machine.getMachineId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存设备修改数据")
    public ResponseEty update(@RequestBody  Machine machine){
        if(machine.getMachineId()==null){
            return ResponseEty.failure("设备ID不能为空");
        }
        if(StringUtils.isBlank(machine.getMachineCode())){
            return ResponseEty.failure("设备编号不能为空");
        }
        if(StringUtils.isBlank(machine.getMachineName())){
            return ResponseEty.failure("设备名称不能为空");
        }
        Machine oldMachine = machineService.getById(machine.getMachineId());
        if(StringUtils.isNotBlank(machine.getMachineCode())){
            if(!machine.getMachineCode().equals(oldMachine.getMachineCode())){
                if(machineService.machineCountByCode(machine.getMachineCode())>0){
                    return ResponseEty.failure("该设备编码已经使用");
                }
            }
        }
        if(StringUtils.isNotBlank(machine.getMachineName())){
            if(!machine.getMachineName().equals(oldMachine.getMachineName())){
                if(machineService.machineCountByName(machine.getMachineName())>0){
                    return ResponseEty.failure("该设备名称已经使用");
                }
            }
        }
        machineService.updateMachine(machine);

        if(machine.getMachineId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        return ResponseEty.success("操作成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除设备数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        Machine machine=machineService.getById(id);
        if(machine == null){
            return ResponseEty.failure("设备不存在");
        }
        machineService.deleteMachine(machine);
        return ResponseEty.success("删除成功");
    }
    //
//    @RequiresPermissions("sys:user:delete")
    @PostMapping("deleteSome")
    @ResponseBody
    @SysLog("删除设备数据(多个)")
    public ResponseEty deleteSome(@RequestBody List<Machine> Machines){
        if(Machines == null || Machines.size()==0){
            return ResponseEty.failure("请选择需要删除的信息");
        }
        Machines.forEach(m -> machineService.deleteMachine(m));
        return ResponseEty.success("批量删除成功");
    }


    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用设备")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        Machine machine=machineService.getById(id);
        if(machine == null){
            return ResponseEty.failure("设备不存在");
        }
        machineService.lockMachine(machine);
        return ResponseEty.success("操作成功");
    }*/
}
