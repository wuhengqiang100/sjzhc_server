package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.Device;
import com.kexin.admin.entity.tables.DeviceType;
import com.kexin.admin.service.DeviceService;
import com.kexin.admin.service.DeviceTypeService;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.base.Data;
import com.kexin.common.base.PageDataBase;
import com.kexin.common.util.ResponseEntity;
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
@RequestMapping("device")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @Autowired
    DeviceTypeService deviceTypeService;

    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @GetMapping("list")
    @ResponseBody
    @SysLog("设备列表获取")
    public PageDataBase<Device> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                       @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                       @RequestParam(value = "sort")String sort,
                                       @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                       @RequestParam(value = "title",defaultValue = "") String title,
                                       ServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<Device> devicePageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<Device> deviceWrapper = new QueryWrapper<>();
        if (sort.equals("+id")){
            deviceWrapper.orderByAsc("MACHINE_ID");
        }else{
            deviceWrapper.orderByDesc("MACHINE_ID");
        }

        if (StringUtils.isNotEmpty(useFlag)){
            deviceWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            deviceWrapper.like("MACHINE_NAME",title);
        }
//        if(!map.isEmpty()){
//            String useFlag = (String) map.get("useFlag");
//            if(StringUtils.isNotBlank(useFlag)) {
//                deviceWrapper.eq("USE_FLAG", useFlag);
//            }
//            String keys = (String) map.get("key");
//            if(StringUtils.isNotBlank(keys)) {
//                deviceWrapper.and(wrapper -> wrapper.like("MACHINE_NAME", keys));//模糊查询拼接
//            }
//        }
        IPage<Device> devicePage = deviceService.page(new Page<>(page,limit),deviceWrapper);
        data.setTotal(devicePage.getTotal());
        data.setItems(setDeviceTypeToDevice(devicePage.getRecords()));
        devicePageData.setData(data);
        return devicePageData;
    }

    private List<Device> setDeviceTypeToDevice(List<Device> devices){
        devices.forEach(r -> {
            if(r.getMachineTypeId()!=null){
                DeviceType deviceType=deviceTypeService.getById(r.getMachineTypeId());
                r.setDeviceType(deviceType);
            }
        });
         return devices;
    }


    @PostMapping("create")
    @ResponseBody
    @SysLog("新增设备数据")
    public ResponseEty create(@RequestBody  Device device){
        if(StringUtils.isBlank(device.getMachineCode())){
            return ResponseEty.failure("设备编号不能为空");
        }
        if(StringUtils.isBlank(device.getMachineName())){
            return ResponseEty.failure("设备名称不能为空");
        }
        if(StringUtils.isBlank(device.getMachineIp())){
            return ResponseEty.failure("设备Ip不能为空");
        }
    /*    if (device.getUseDeviceWasteNoJudge()<0 || device.getUseDeviceWasteNoJudge() >1){
            return ResponseEty.failure("设备的机检严重废人工干预标志,只能为0,1");
        }*/
        if (deviceService.deviceCountByCode(device.getMachineCode())>0){
            return ResponseEty.failure("设备编号已使用,请重新输入");
        }
        if (deviceService.deviceCountByName(device.getMachineName())>0){
            return ResponseEty.failure("设备名称已使用,请重新输入");
        }
        if (deviceService.deviceCountByIp(device.getMachineIp())>0){
            return ResponseEty.failure("设备Ip已使用,请重新输入");
        }
        deviceService.saveDevice(device);
        if(device.getMachineId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存设备修改数据")
    public ResponseEty update(@RequestBody  Device device){
        if(device.getMachineId()==null){
            return ResponseEty.failure("设备ID不能为空");
        }
        if(StringUtils.isBlank(device.getMachineCode())){
            return ResponseEty.failure("设备编号不能为空");
        }
        if(StringUtils.isBlank(device.getMachineName())){
            return ResponseEty.failure("设备名称不能为空");
        }
        if(StringUtils.isBlank(device.getMachineIp())){
            return ResponseEty.failure("设备Ip不能为空");
        }
/*        if (device.getUseDeviceWasteNoJudge()<0 || device.getUseDeviceWasteNoJudge() >1){
            return ResponseEty.failure("设备的机检严重废人工干预标志,只能为0,1");
        }*/
        Device oldDevice = deviceService.getById(device.getMachineId());
        if(StringUtils.isNotBlank(device.getMachineCode())){
            if(!device.getMachineCode().equals(oldDevice.getMachineCode())){
                if(deviceService.deviceCountByCode(device.getMachineCode())>0){
                    return ResponseEty.failure("该设备编码已经使用");
                }
            }
        }
        if(StringUtils.isNotBlank(device.getMachineName())){
            if(!device.getMachineName().equals(oldDevice.getMachineName())){
                if(deviceService.deviceCountByName(device.getMachineName())>0){
                    return ResponseEty.failure("该设备名称已经使用");
                }
            }
        }
        if(StringUtils.isNotBlank(device.getMachineIp())){
            if(!device.getMachineIp().equals(oldDevice.getMachineIp())){
                if(deviceService.deviceCountByIp(device.getMachineIp())>0){
                    return ResponseEty.failure("设备Ip已使用,请重新输入");
                }
            }
        }
        deviceService.updateDevice(device);

        if(device.getMachineId()==null){
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
        Device device=deviceService.getById(id);
        if(device == null){
            return ResponseEty.failure("设备不存在");
        }
        deviceService.deleteDevice(device);
        return ResponseEty.success("删除成功");
    }
    //
//    @RequiresPermissions("sys:user:delete")
    @PostMapping("deleteSome")
    @ResponseBody
    @SysLog("删除设备数据(多个)")
    public ResponseEty deleteSome(@RequestBody List<Device> Devices){
        if(Devices == null || Devices.size()==0){
            return ResponseEty.failure("请选择需要删除的信息");
        }
        Devices.forEach(m -> deviceService.deleteDevice(m));
        return ResponseEty.success("批量删除成功");
    }


    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用设备")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        Device device=deviceService.getById(id);
        if(device == null){
            return ResponseEty.failure("设备不存在");
        }
        deviceService.lockDevice(device);
        return ResponseEty.success("操作成功");
    }
}
