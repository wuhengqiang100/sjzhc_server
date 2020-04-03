package com.kexin.admin.controller;

import com.kexin.admin.entity.vo.QaInspectChange;
import com.kexin.admin.entity.vo.QaInspectDatas;
import com.kexin.admin.service.QaInspectMasterService;
import com.kexin.common.util.ResponseEty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Description:设备管理Controller
 * @Author: 巫恒强  @Date: 2019/11/7 15:56
 * @Param:
 * @Return: 
 */
@Controller
@RequestMapping("/machine/check")
public class MachineCheckController {

    @Autowired
    QaInspectMasterService qaInspectMasterService;

    /**
     * @Title: 初始化机检审核的的数据
     * @param @param
     * @return @return
     * @author 巫恒强
     * @throws
     * @date 2020/3/18 11:12
     */
    @GetMapping("")
    @ResponseBody
    public ResponseEty list(){
        //获取已经分活的审核信息 allowJudge==2
//        modelMap.put("historyInspectList",qaInspectMasterService.getQaInspectMasterHistory());
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("qaInspectMasterList",qaInspectMasterService.getAllQaInspectMaster());
        return responseEty;
    }

    @PostMapping("save")
    @ResponseBody
    public ResponseEty save(@RequestBody QaInspectChange inspectChange){

        return qaInspectMasterService.saveQaInspectMaster(inspectChange);
    }


}
