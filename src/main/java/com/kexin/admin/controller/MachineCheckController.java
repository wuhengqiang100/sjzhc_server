package com.kexin.admin.controller;

import com.kexin.admin.entity.vo.QaInspectData;
import com.kexin.admin.entity.vo.QaInspectDatas;
import com.kexin.admin.entity.vo.QaInspectTransfer;
import com.kexin.admin.service.QaInspectMasterService;
import com.kexin.common.util.ResponseEntity;
import com.kexin.common.util.ResponseEty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        responseEty.setAny("titles",qaInspectMasterService.getTransferTitles());
        responseEty.setAny("qaInspectMasterList",qaInspectMasterService.getAllQaInspectMaster());
        return responseEty;
    }


    @PostMapping("list")
    @ResponseBody
    public ResponseEntity listAll(){
        ResponseEntity responseEntity=new ResponseEntity();
//        QaInspectDatas qaInspectData =qaInspectMasterService.getAllQaInspectMaster();//获取可分活的数据

//        responseEntity.setAny("qaInspectData",qaInspectData);
        responseEntity.setAny("historyInspectList",qaInspectMasterService.getQaInspectMasterHistory());
        return responseEntity;
    }


    /**
     * 保存可以分活的数据 index
     *                    如果数据来自左边，index 为 0，否则为 1
     * @param transferListransfer
     * @return
     */
    @PostMapping("save")
    @ResponseBody
    public ResponseEntity save(@RequestBody List<QaInspectTransfer> transferListransfer){
        ResponseEntity responseEntity=new ResponseEntity();
        try{
//                QaInspectDatas qaInspectData=qaInspectMasterService.saveQaInspectAllow(transferListransfer);
                responseEntity.setSuccess(true);
//                responseEntity.setAny("qaInspectData",qaInspectData);
        }catch (Exception e){
            responseEntity.setSuccess(false);
            responseEntity.setMessage("保存失败");
        }finally {
            return responseEntity;
        }
    }
    @PostMapping("quickSave")
    @ResponseBody
    public ResponseEntity quickSave(){
        return qaInspectMasterService.quickSaveInspect();
    }
    @PostMapping("return")
    @ResponseBody
    public ResponseEntity returnData(@RequestBody List<QaInspectTransfer> transferListransfer){
        return qaInspectMasterService.returnQaInspect(transferListransfer);
    }

/*

    @Autowired
    MachineService machineService;


    @GetMapping("list")
    @SysLog("跳转系统用户列表页面")
    public String list(){
        return "/base/device/info/list";
    }


    @RequiresPermissions("device:info:list")
    @PostMapping("list")
    @ResponseBody
    public PageData<Machine> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                               @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                               ServletRequest request){
        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageData<Machine> machinePageData = new PageData<>();
        QueryWrapper<Machine> machineWrapper = new QueryWrapper<>();
        if(!map.isEmpty()){
            String useFlag = (String) map.get("useFlag");
            if(StringUtils.isNotBlank(useFlag)) {
                machineWrapper.eq("USE_FLAG", useFlag);
            }
            String keys = (String) map.get("key");
            if(StringUtils.isNotBlank(keys)) {
                machineWrapper.and(wrapper -> wrapper.like("MACHINE_NAME", keys));//模糊查询拼接
            }
        }
        IPage<Machine> machinePage = machineService.page(new Page<>(page,limit),machineWrapper);
        machinePageData.setCount(machinePage.getTotal());
        machinePageData.setData(machinePage.getRecords());
        return machinePageData;
    }
//
    // /base/device/info/list
    @GetMapping("add")
    public String add(ModelMap modelMap){
        return "/base/device/info/add";
    }
//
//    @RequiresPermissions("sys:user:add")
    @PostMapping("add")
    @ResponseBody
    @SysLog("保存新增设备数据")
    public ResponseEntity add(@RequestBody  Machine machine){
        if(StringUtils.isBlank(machine.getMachineCode())){
            return ResponseEntity.failure("设备编号不能为空");
        }
        if(StringUtils.isBlank(machine.getMachineName())){
            return ResponseEntity.failure("设备名称不能为空");
        }
    */
/*    if (machine.getUseMachineWasteNoJudge()<0 || machine.getUseMachineWasteNoJudge() >1){
            return ResponseEntity.failure("设备的机检严重废人工干预标志,只能为0,1");
        }*//*

        if (machineService.machineCountByCode(machine.getMachineCode())>0){
            return ResponseEntity.failure("设备编号已使用,请重新输入");
        }
        if (machineService.machineCountByName(machine.getMachineName())>0){
            return ResponseEntity.failure("设备名称已使用,请重新输入");
        }
        machineService.saveMachine(machine);
        if(machine.getMachineId()==null){
            return ResponseEntity.failure("保存用户信息出错");
        }
        return ResponseEntity.success("保存成功");
    }

    @GetMapping("edit")
    public String edit(Integer id,ModelMap modelMap){
        Machine machine=machineService.getById(id);
        modelMap.put("machine",machine);
//        User user = userService.findUserById(Integer.parseInt(id));
//        String roleIds = "";
//        if(user != null) {
//            roleIds = user.getRoleLists().stream().map(role -> String.valueOf(role.getId())).collect(Collectors.joining(","));
//        }
//        List<Role> roleList = roleService.selectAll();
//        modelMap.put("localuser",user);
//        modelMap.put("roleIds",roleIds);
//        modelMap.put("roleList",roleList);
        return "/base/device/info/edit";
    }
//
//    @RequiresPermissions("sys:user:edit")
    @PostMapping("edit")
    @ResponseBody
    @SysLog("保存设备修改数据")
    public ResponseEntity edit(@RequestBody  Machine machine){
        if(machine.getMachineId()==null){
            return ResponseEntity.failure("设备ID不能为空");
        }
        if(StringUtils.isBlank(machine.getMachineCode())){
            return ResponseEntity.failure("设备编号不能为空");
        }
        if(StringUtils.isBlank(machine.getMachineName())){
            return ResponseEntity.failure("设备名称不能为空");
        }
*/
/*        if (machine.getUseMachineWasteNoJudge()<0 || machine.getUseMachineWasteNoJudge() >1){
            return ResponseEntity.failure("设备的机检严重废人工干预标志,只能为0,1");
        }*//*

        Machine oldMachine = machineService.getById(machine.getMachineId());
        if(StringUtils.isNotBlank(machine.getMachineCode())){
            if(!machine.getMachineCode().equals(oldMachine.getMachineCode())){
                if(machineService.machineCountByCode(machine.getMachineCode())>0){
                    return ResponseEntity.failure("该设备编码已经使用");
                }
            }
        }
        if(StringUtils.isNotBlank(machine.getMachineName())){
            if(!machine.getMachineName().equals(oldMachine.getMachineName())){
                if(machineService.machineCountByName(machine.getMachineName())>0){
                    return ResponseEntity.failure("该设备名称已经使用");
                }
            }
        }
        machineService.updateMachine(machine);

        if(machine.getMachineId()==null){
            return ResponseEntity.failure("保存设备信息出错");
        }
        return ResponseEntity.success("操作成功");
    }

    //    @RequiresPermissions("sys:user:delete")
    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除设备数据(单个)")
    public ResponseEntity delete(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEntity.failure("参数错误");
        }
        Machine machine=machineService.getById(id);
        if(machine == null){
            return ResponseEntity.failure("设备不存在");
        }
        machineService.deleteMachine(machine);
        return ResponseEntity.success("删除成功");
    }
//
//    @RequiresPermissions("sys:user:delete")
    @PostMapping("deleteSome")
    @ResponseBody
    @SysLog("删除设备数据(多个)")
    public ResponseEntity deleteSome(@RequestBody List<Machine> Machines){
        if(Machines == null || Machines.size()==0){
            return ResponseEntity.failure("请选择需要删除的用户");
        }
        Machines.forEach(m -> machineService.deleteMachine(m));
        return ResponseEntity.success("批量删除成功");
    }

//
//    @RequiresPermissions("sys:user:lock")
    @PostMapping("lock")
    @ResponseBody
    @SysLog("锁定或开启系统用户")
    public ResponseEntity lock(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEntity.failure("参数错误");
        }
        Machine machine =machineService.getById(id);
        if(machine == null){
            return ResponseEntity.failure("设备不存在");
        }
        machineService.lockMachine(machine);
        return ResponseEntity.success("操作成功");
    }
*/




}
