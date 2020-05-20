package com.kexin.admin.controller;

import com.kexin.admin.entity.vo.QaInspectChange;
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
@RequestMapping("/machine")
public class MachineCheckController {

    @Autowired
    QaInspectMasterService qaInspectMasterService;

    /**
     * 获取可以审核的车次数据
     * @return
     */
    @GetMapping("canAudit/list")
    @ResponseBody
    public ResponseEty listCanAuditList(){
        return qaInspectMasterService.getCanAuditInspectMaster();
    }



    @PostMapping("canAudit/save")
    @ResponseBody
    public ResponseEty saveCanAudit(@RequestParam(required = false) Integer[] map){
        if (map==null){
            return ResponseEty.failure("请选择要审核的车次");
        }
        return qaInspectMasterService.saveCanAuditInspectMaster(map);
    }
    /**
     * 获取已经审核的车次数据,今天
     * @return
     */
    @GetMapping("alreadyAudit/list")
    @ResponseBody
    public ResponseEty listAlreadyAuditList(){
        return qaInspectMasterService.getAlreadyAuditInspectMaster();
    }

    @PostMapping("alreadyAudit/save")
    @ResponseBody
    public ResponseEty saveAlreadyAudit(@RequestParam(required = false) Integer[] map){
        if (map==null){
            return ResponseEty.failure("请选择要回退的车次");
        }
        return qaInspectMasterService.saveAlreadyAuditInspectMaster(map);
    }
    /**
    /**
     * 获取不走核查的车次数据,今天
     * @return
     */
    @GetMapping("notAudit/list")
    @ResponseBody
    public ResponseEty listNotAuditList(){
        return qaInspectMasterService.getNotAuditInspectMaster();
    }

    @PostMapping("notAudit/save")
    @ResponseBody
    public ResponseEty saveNotAudit(@RequestParam(required = false) Integer[] map){
        if (map==null){
            return ResponseEty.failure("请选择全检的车次");
        }
        return qaInspectMasterService.saveNotAuditInspectMaster(map);

    }
    @PostMapping("notAudit/return")
    @ResponseBody
    public ResponseEty returnNotAudit(@RequestParam(required = false) Integer[] map){
        if (map==null){
            return ResponseEty.failure("请选择全检回退的车次");
        }
        return qaInspectMasterService.returnNotAuditInspectMaster(map);

    }
    /**
     * @Title: 初始化机检审核的的数据
     * @param @param
     * @return @return
     * @author 巫恒强
     * @throws
     * @date 2020/3/18 11:12
     */
 /*   @GetMapping("/check")
    @ResponseBody
    public ResponseEty list(){
        //获取已经分活的审核信息 allowJudge==2
//        modelMap.put("historyInspectList",qaInspectMasterService.getQaInspectMasterHistory());
        ResponseEty responseEty=new ResponseEty();
        responseEty.setSuccess(20000);
        responseEty.setAny("qaInspectMasterList",qaInspectMasterService.getAllQaInspectMaster());
        return responseEty;
    }

    @PostMapping("check/save")
    @ResponseBody
    public ResponseEty save(@RequestBody QaInspectChange inspectChange){

        return qaInspectMasterService.saveQaInspectMaster(inspectChange);
    }
*/



}
