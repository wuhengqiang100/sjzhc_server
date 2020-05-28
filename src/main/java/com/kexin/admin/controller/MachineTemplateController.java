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
        return machinePageData;
    }




    @PostMapping("upload")
    @ResponseBody
    @SysLog("上传机检模板数据")
    public ResponseEty multipleSave(@RequestParam("fileUpload") MultipartFile[] file,
                                    @RequestParam("rfilename") String rfilename,
                                    @RequestParam("addId") String addId,
                                    @RequestParam("tokenId") Integer tokenId,
                                    HttpServletRequest request){

        return machineService.uploadTemplate(file,  rfilename,Integer.parseInt(addId),request,ftp,tokenId);
    }

    @PostMapping("download")
    @ResponseBody
    @SysLog("下载机检模板数据")
    public ResponseEty download(@RequestParam(name = "id") Integer machineId,
                                @RequestParam("tokenId") Integer tokenId){
        return machineService.getDownloadUrl(machineId,tokenId);
//        return machineService.downloadTemplate(machineId);
    }

}
