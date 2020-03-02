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
import com.kexin.common.util.ResponseEty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;

/**
 * 设备类别配置管理controller层
 */
@Controller
@RequestMapping("deviceType")
public class DeviceTypeController {


    @Autowired
    DeviceTypeService deviceTypeService;

    @GetMapping("listOption")
    @ResponseBody
    @SysLog("设备类别options列表获取")
    public ResponseEty listopetion(){
        ResponseEty responseEty=new ResponseEty();
        QueryWrapper<DeviceType> deviceTypeWrapper = new QueryWrapper<>();
        responseEty.setData(deviceTypeService.list(deviceTypeWrapper));
        responseEty.setSuccess(20000);
         return responseEty;
    }


    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @GetMapping("list")
    @ResponseBody
    @SysLog("设备类别列表获取")
    public PageDataBase<DeviceType> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                       @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                       @RequestParam(value = "sort")String sort,
                                       @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                       @RequestParam(value = "title",defaultValue = "") String title,
                                       ServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<DeviceType> deviceTypePageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<DeviceType> deviceTypeWrapper = new QueryWrapper<>();
        if (sort.equals("+id")){
            deviceTypeWrapper.orderByAsc("MACHINE_TYPE_ID");
        }else{
            deviceTypeWrapper.orderByDesc("MACHINE_TYPE_ID");
        }
        if (StringUtils.isNotEmpty(useFlag)){
            deviceTypeWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            deviceTypeWrapper.like("MACHINE_TYPE_NAME",title);
        }
//        if(!map.isEmpty()){
//            String useFlag = (String) map.get("useFlag");
//            if(StringUtils.isNotBlank(useFlag)) {
//                deviceTypeWrapper.eq("USE_FLAG", useFlag);
//            }
//            String keys = (String) map.get("key");
//            if(StringUtils.isNotBlank(keys)) {
//                deviceTypeWrapper.and(wrapper -> wrapper.like("MACHINE_NAME", keys));//模糊查询拼接
//            }
//        }
        IPage<DeviceType> deviceTypePage = deviceTypeService.page(new Page<>(page,limit),deviceTypeWrapper);
        data.setTotal(deviceTypePage.getTotal());
        data.setItems(deviceTypePage.getRecords());
        deviceTypePageData.setData(data);
        return deviceTypePageData;
    }


    @PostMapping("create")
    @ResponseBody
    @SysLog("新增设备类别数据")
    public ResponseEty create(@RequestBody  DeviceType deviceType){
        if(StringUtils.isBlank(deviceType.getMachineTypeCode())){
            return ResponseEty.failure("设备类别编号不能为空");
        }
        if(StringUtils.isBlank(deviceType.getMachineTypeName())){
            return ResponseEty.failure("设备类别名称不能为空");
        }
    /*    if (deviceType.getUseDeviceTypeWasteNoJudge()<0 || deviceType.getUseDeviceTypeWasteNoJudge() >1){
            return ResponseEty.failure("设备类别的机检严重废人工干预标志,只能为0,1");
        }*/
        if (deviceTypeService.machineTypeCountByCode(deviceType.getMachineTypeCode())>0){
            return ResponseEty.failure("设备类别编号已使用,请重新输入");
        }
        if (deviceTypeService.machineTypeCountByName(deviceType.getMachineTypeName())>0){
            return ResponseEty.failure("设备类别名称已使用,请重新输入");
        }
        deviceTypeService.saveDeviceType(deviceType);
        if(deviceType.getMachineTypeId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存设备类别修改数据")
    public ResponseEty update(@RequestBody  DeviceType deviceType){
        if(deviceType.getMachineTypeId()==null){
            return ResponseEty.failure("设备类别ID不能为空");
        }
        if(StringUtils.isBlank(deviceType.getMachineTypeCode())){
            return ResponseEty.failure("设备类别编号不能为空");
        }
        if(StringUtils.isBlank(deviceType.getMachineTypeName())){
            return ResponseEty.failure("设备类别名称不能为空");
        }
/*        if (deviceType.getUseDeviceTypeWasteNoJudge()<0 || deviceType.getUseDeviceTypeWasteNoJudge() >1){
            return ResponseEty.failure("设备类别的机检严重废人工干预标志,只能为0,1");
        }*/
        DeviceType oldDeviceType = deviceTypeService.getById(deviceType.getMachineTypeId());
        if(StringUtils.isNotBlank(deviceType.getMachineTypeCode())){
            if(!deviceType.getMachineTypeCode().equals(oldDeviceType.getMachineTypeCode())){
                if(deviceTypeService.machineTypeCountByCode(deviceType.getMachineTypeCode())>0){
                    return ResponseEty.failure("该设备类别编码已经使用");
                }
            }
        }
        if(StringUtils.isNotBlank(deviceType.getMachineTypeName())){
            if(!deviceType.getMachineTypeName().equals(oldDeviceType.getMachineTypeName())){
                if(deviceTypeService.machineTypeCountByName(deviceType.getMachineTypeName())>0){
                    return ResponseEty.failure("该设备类别名称已经使用");
                }
            }
        }
        deviceTypeService.updateDeviceType(deviceType);

        if(deviceType.getMachineTypeId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        return ResponseEty.success("操作成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除设备类别数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        DeviceType deviceType=deviceTypeService.getById(id);
        if(deviceType == null){
            return ResponseEty.failure("设备类别不存在");
        }
        deviceTypeService.deleteDeviceType(deviceType);
        return ResponseEty.success("删除成功");
    }
    //
//    @RequiresPermissions("sys:user:delete")
    @PostMapping("deleteSome")
    @ResponseBody
    @SysLog("删除设备类别数据(多个)")
    public ResponseEty deleteSome(@RequestBody List<DeviceType> DeviceTypes){
        if(DeviceTypes == null || DeviceTypes.size()==0){
            return ResponseEty.failure("请选择需要删除的信息");
        }
        DeviceTypes.forEach(m -> deviceTypeService.deleteDeviceType(m));
        return ResponseEty.success("批量删除成功");
    }


    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用设备类别")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        DeviceType deviceType=deviceTypeService.getById(id);
        if(deviceType == null){
            return ResponseEty.failure("设备类别不存在");
        }
        deviceTypeService.lockDeviceType(deviceType);
        return ResponseEty.success("操作成功");
    }
}
