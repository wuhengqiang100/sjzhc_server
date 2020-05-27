package com.kexin.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kexin.admin.entity.tables.LoginUser;
import com.kexin.admin.entity.tables.Operator;
import com.kexin.admin.entity.tables.QaInspectMaster;
import com.kexin.admin.entity.vo.QaInspectChange;
import com.kexin.admin.entity.vo.query.QueryDate;
import com.kexin.admin.entity.vo.query.SaveCheckData;
import com.kexin.admin.entity.vo.query.SaveNoteData;
import com.kexin.admin.service.LoginUserService;
import com.kexin.admin.service.OperatorService;
import com.kexin.admin.service.QaInspectMasterService;
import com.kexin.admin.service.SystemLogService;
import com.kexin.common.util.ResponseEty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
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
    @Autowired
    SystemLogService systemLogService;//系统日志记录service
    @Autowired
    LoginUserService loginUserService;
    @Autowired
    OperatorService operatorService;
    /**
     * 获取可以审核的车次数据
     * @return
     */
    @PostMapping("canAudit/list")
    @ResponseBody
    public ResponseEty listCanAuditList(@RequestBody QueryDate queryDate,@RequestHeader(value="token",required = false) Integer token){


//        systemLogService.saveMachineLog(token,"查询","查询了可审核的车次");
        return qaInspectMasterService.getCanAuditInspectMaster(queryDate);
    }




    /**
     * 获取已经审核的车次数据,今天
     * @return
     */
    @PostMapping("alreadyAudit/list")
    @ResponseBody
    public ResponseEty listAlreadyAuditList(@RequestBody QueryDate queryDate,@RequestHeader(value="token",required = false) Integer token){
//        systemLogService.saveMachineLog(token,"查询","查询了已经审核的车次");
        return qaInspectMasterService.getAlreadyAuditInspectMaster(queryDate);
    }

    @PostMapping("canAudit/save")
    @ResponseBody
    public ResponseEty saveCanAudit(@RequestBody SaveCheckData saveCheckData,@RequestHeader(value="token",required = false) Integer token){
        if (saveCheckData.getData()==null){
            return ResponseEty.failure("请选择要审核的车次");
        }
        return qaInspectMasterService.saveCanAuditInspectMaster(saveCheckData,token);
    }

    @PostMapping("alreadyAudit/save")
    @ResponseBody
    public ResponseEty saveAlreadyAudit(@RequestBody SaveCheckData saveCheckData,@RequestHeader(value="token",required = false) Integer token
                                        ){
        if (saveCheckData.getData()==null){
            return ResponseEty.failure("请选择要回退的车次");
        }
        return qaInspectMasterService.saveAlreadyAuditInspectMaster(saveCheckData,token);
    }

    @PostMapping("notAudit/save")
    @ResponseBody
    public ResponseEty saveNotAudit(@RequestBody SaveCheckData saveCheckData,@RequestHeader(value="token",required = false) Integer token){
        if (saveCheckData.getData()==null){
            return ResponseEty.failure("请选择全检的车次");
        }
        return qaInspectMasterService.saveNotAuditInspectMaster(saveCheckData,token);

    }
    @PostMapping("saveNoteCan")
    @ResponseBody
    public ResponseEty saveNoteCan(@RequestBody SaveNoteData saveNoteData, @RequestHeader(value="token",required = false) Integer token){
        if (saveNoteData.getData()==null){
            return ResponseEty.failure("请选择全检的车次");
        }
        return qaInspectMasterService.saveNoteInspectMaster(saveNoteData,token);

    }
    @PostMapping("saveNoteAlready")
    @ResponseBody
    public ResponseEty saveNoteAlready(@RequestBody SaveNoteData saveNoteData, @RequestHeader(value="token",required = false) Integer token){
        if (saveNoteData.getData()==null){
            return ResponseEty.failure("请选择全检的车次");
        }
        return qaInspectMasterService.saveNoteInspectMaster(saveNoteData,token);

    }

    /**
    /**
     * 获取不走核查的车次数据,今天
     * @return
     */
/*    @PostMapping("notAudit/list")
    @ResponseBody
    public ResponseEty listNotAuditList(@RequestBody QueryDate queryDate,@RequestHeader(value="token",required = false) Integer token){

//        systemLogService.saveMachineLog(token,"查询","查询了走全检的车次");
        return qaInspectMasterService.getNotAuditInspectMaster(queryDate);
    }*/


    /**
     * 全检回退的车次,回退到待审核状态
     * @param saveCheckData
     * @return
     */
/*    @PostMapping("notAudit/return")
    @ResponseBody
    public ResponseEty returnNotAudit(@RequestBody SaveCheckData saveCheckData,@RequestHeader(value="token",required = false) Integer token){
        if (saveCheckData.getData()==null){
            return ResponseEty.failure("请选择全检回退的车次");
        }
        return qaInspectMasterService.returnNotAuditInspectMaster(saveCheckData,token);

    }*/



}
