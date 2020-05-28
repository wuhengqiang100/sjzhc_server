package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.Machine;
import com.kexin.admin.service.MachineService;
import com.kexin.admin.service.SystemLogService;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.base.Data;
import com.kexin.common.base.PageDataBase;
import com.kexin.common.config.MySysUser;
import com.kexin.common.util.ResponseEty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;

/**
 * 设备配置管理controller层
 */
@Controller
@RequestMapping("machine")
public class MachineController {

    @Autowired
    MachineService machineService;

    @Autowired
    SystemLogService systemLogService;//系统日志记录service


    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @GetMapping("list")
    @ResponseBody
    @SysLog("设备列表获取")
    public PageDataBase<Machine> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                       @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                       @RequestParam(value = "sort")String sort,
                                       @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                       @RequestParam(value = "title",defaultValue = "") String title,
                                       @RequestHeader(value="token",required = false) Integer token){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<Machine> machinePageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<Machine> machineWrapper = new QueryWrapper<>();
        machineWrapper.orderByDesc("START_DATE");
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

        IPage<Machine> machinePage = machineService.page(new Page<>(page,limit),machineWrapper);
        data.setTotal(machinePage.getTotal());
        data.setItems(machinePage.getRecords());
        machinePageData.setData(data);
        systemLogService.saveMachineLog(token,"查询","查询了设备列表");

        return machinePageData;
    }

    @PostMapping("create")
    @ResponseBody
    @SysLog("新增设备数据")
    public ResponseEty create(@RequestBody  Machine machine,@RequestHeader(value="token",required = false) Integer token){
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
        systemLogService.saveMachineLog(token,"新增","新增了设备:"+machine.getMachineName());

        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存设备修改数据")
    public ResponseEty update(@RequestBody  Machine machine,@RequestHeader(value="token",required = false) Integer token){
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
        systemLogService.saveMachineLog(token,"更新","更新了设备:"+machine.getMachineName());

        return ResponseEty.success("操作成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除设备数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id,@RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        Machine machine=machineService.getById(id);
        if(machine == null){
            return ResponseEty.failure("设备不存在");
        }
        machineService.deleteMachine(machine);
        systemLogService.saveMachineLog(token,"删除","删除了设备:"+machine.getMachineName());

        return ResponseEty.success("删除成功");
    }


    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用设备")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id,@RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        Machine machine=machineService.getById(id);
        if(machine == null){
            return ResponseEty.failure("设备不存在");
        }
        machineService.lockMachine(machine);
        systemLogService.saveMachineLog(token,"删除","禁用了设备:"+machine.getMachineName());

        return ResponseEty.success("操作成功");
    }
}
