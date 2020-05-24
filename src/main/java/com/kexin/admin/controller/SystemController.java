package com.kexin.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.SysFunctions;
import com.kexin.admin.entity.tables.SystemSet;
import com.kexin.admin.entity.vo.query.QueryDateParent;
import com.kexin.admin.service.RoleMenuService;
import com.kexin.admin.service.SysFunctionService;
import com.kexin.admin.service.SystemLogService;
import com.kexin.admin.service.SystemSetService;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.base.Data;
import com.kexin.common.base.PageDataBase;
import com.kexin.common.util.ResponseEty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统设置Controller层,主要包括角色权限管理,以及模拟调试,维护人员使用
 */
@Controller
@RequestMapping("system")
public class SystemController {



    @Autowired
    SysFunctionService sysFunctionService; //系统权限sservice


    @Autowired
    RoleMenuService roleMenuService;//角色菜单关系service


    @Autowired
    SystemLogService systemLogService;//系统日志记录service

    /**
     * 获取网站菜单树list
     * @param query
     * @return
     */
    @PostMapping("bMenu/list")
    @ResponseBody
    @SysLog("菜单list获取")
    public PageDataBase<SysFunctions> listb(@RequestBody QueryDateParent query){
        PageDataBase<SysFunctions> sysFunctionsPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<SysFunctions> sysFunctionsQueryWrapper = new QueryWrapper<>();
        if (query.getSort().equals("+id")){
            sysFunctionsQueryWrapper.orderByAsc("FUNCTION_ID");
        }else{
            sysFunctionsQueryWrapper.orderByDesc("FUNCTION_ID");
        }
        if (StringUtils.isNotEmpty(query.getTitle())){
            sysFunctionsQueryWrapper.like("FUNCTION_TITLE",query.getTitle());
        }
        sysFunctionsQueryWrapper
                .isNull("FUNCTION_PARENT_ID")
                .eq("FUNCTION_TYPE_ID",1)
                .orderByAsc("FUNCTION_SORT");

        IPage<SysFunctions> sysFunctionsPage = sysFunctionService.page(new Page<>(query.getPage(),query.getLimit()),sysFunctionsQueryWrapper);
        data.setTotal(sysFunctionsPage.getTotal());


        sysFunctionsPage.getRecords().forEach(sysFunction ->{
            String[] childrenIds= StringUtils.split(sysFunction.getChildrenIds(),',');
            QueryWrapper<SysFunctions> sysFunctionsQueryWrapper2 = new QueryWrapper<>();

            sysFunctionsQueryWrapper2
                    .eq("FUNCTION_TYPE_ID",1)
                    .in("FUNCTION_ID",childrenIds);
            List<SysFunctions> sysFunctionsList1=sysFunctionService.list(sysFunctionsQueryWrapper2);

            sysFunction.setChildren(sysFunctionsList1);
        } );

        data.setItems(sysFunctionsPage.getRecords());
        sysFunctionsPageData.setData(data);
        return sysFunctionsPageData;
    }

    /**
     * 获取c端的操作权限list
     * @param query
     * @return
     */
    @PostMapping("cMenu/list")
    @ResponseBody
    @SysLog("操作权限list获取")
    public PageDataBase<SysFunctions> listc(@RequestBody QueryDateParent query){
        PageDataBase<SysFunctions> sysFunctionsPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<SysFunctions> sysFunctionsQueryWrapper = new QueryWrapper<>();
        if (query.getSort().equals("+id")){
            sysFunctionsQueryWrapper.orderByAsc("FUNCTION_ID");
        }else{
            sysFunctionsQueryWrapper.orderByDesc("FUNCTION_ID");
        }
        if (StringUtils.isNotEmpty(query.getUseFlag())){
            sysFunctionsQueryWrapper.eq("IS_SHOW",query.getUseFlag());
        }
        if (StringUtils.isNotEmpty(query.getTitle())){
            sysFunctionsQueryWrapper.like("FUNCTION_TITLE",query.getTitle());
        }

        //增加根据用户查询的操作
        //增加根据时间查询的操作
    /*    if (query.getStartDate()!=null && query.getEndDate()!=null ){//根据车台名称查询
            sysFunctionsQueryWrapper.between("DATAUP_SET_DATE",  query.getStartDate(), query.getEndDate());
        }*/
        sysFunctionsQueryWrapper
                .eq("FUNCTION_TYPE_ID",2) //2 c端的权限
                .orderByAsc("FUNCTION_ID");

        IPage<SysFunctions> sysFunctionsPage = sysFunctionService.page(new Page<>(query.getPage(),query.getLimit()),sysFunctionsQueryWrapper);
        data.setTotal(sysFunctionsPage.getTotal());
        data.setItems(sysFunctionsPage.getRecords());
        sysFunctionsPageData.setData(data);
        return sysFunctionsPageData;
    }



    /**
     * 新增c端操作权限
     * @param sysFunctions
     * @return
     */
    @PostMapping("cMenu/create")
    @ResponseBody
    @SysLog("新增c端权限数据")
    public ResponseEty create(@RequestBody SysFunctions sysFunctions,
                              @RequestHeader(value="token",required = false) Integer token){
        if(StringUtils.isBlank(sysFunctions.getFunctionCode())){
            return ResponseEty.failure("权限code编号不能为空");
        }
        if(StringUtils.isBlank(sysFunctions.getTitle())){
            return ResponseEty.failure("权限名称不能为空");
        }

        if (sysFunctionService.SysFunctionsCountByCode(sysFunctions.getFunctionCode())>0){
            return ResponseEty.failure("权限编号已使用,请重新输入");
        }
        if (sysFunctionService.SysFunctionsCountByTitle(sysFunctions.getTitle())>0){
            return ResponseEty.failure("权限名称已使用,请重新输入");
        }
        sysFunctions.setFunctionTypeId(2);//c端操作权限枚举

        sysFunctionService.saveSysFunctions(sysFunctions);
        if(sysFunctions.getFunctionId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        systemLogService.saveMachineLog(token,"新增","新增了权限:"+sysFunctions.getTitle());
        return ResponseEty.success("保存成功");
    }

    /**
     * 更新保存c端操作权限
     * @param sysFunctions
     * @return
     */
    @PostMapping("cMenu/update")
    @ResponseBody
    @SysLog("保存c端权限修改数据")
    public ResponseEty update(@RequestBody  SysFunctions sysFunctions,@RequestHeader(value="token",required = false) Integer token){
        if(sysFunctions.getFunctionId()==null){
            return ResponseEty.failure("权限ID不能为空");
        }
        if(StringUtils.isBlank(sysFunctions.getFunctionCode())){
            return ResponseEty.failure("权限编号不能为空");
        }
        if(StringUtils.isBlank(sysFunctions.getTitle())){
            return ResponseEty.failure("权限名称不能为空");
        }
        SysFunctions oldFuncton = sysFunctionService.getById(sysFunctions.getFunctionId());
        if(StringUtils.isNotBlank(sysFunctions.getFunctionCode())){
            if(!sysFunctions.getFunctionCode().equals(oldFuncton.getFunctionCode())){
                if(sysFunctionService.SysFunctionsCountByCode(sysFunctions.getFunctionCode())>0){
                    return ResponseEty.failure("该权限编码已经使用");
                }
            }
        }
        if(StringUtils.isNotBlank(sysFunctions.getTitle())){
            if(!sysFunctions.getTitle().equals(oldFuncton.getTitle())){
                if(sysFunctionService.SysFunctionsCountByTitle(sysFunctions.getTitle())>0){
                    return ResponseEty.failure("该权限名称已经使用");
                }
            }
        }
        sysFunctions.setFunctionTypeId(2);//c端操作权限枚举
        sysFunctionService.updateSysFunctions(sysFunctions);

        if(sysFunctions.getFunctionId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        systemLogService.saveMachineLog(token,"新增","新增了权限:"+sysFunctions.getTitle());
        return ResponseEty.success("操作成功");
    }

    /**
     * 删除c端操作权限
     * @param id
     * @return
     */
    @PostMapping("cMenu/delete")
    @ResponseBody
    @SysLog("删除权限数据(单个)c端权限")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id,@RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        SysFunctions sysFunctions=sysFunctionService.getById(id);
        if(sysFunctions == null){
            return ResponseEty.failure("权限不存在");
        }
        if (sysFunctionService.countFunctionUseNum(id)>0){
            return ResponseEty.failure("该权限有角色在使用不能删除");
        }
        sysFunctionService.deleteSysFunctions(sysFunctions);
        systemLogService.saveMachineLog(token,"删除","删除了权限:"+sysFunctions.getTitle());

        return ResponseEty.success("删除成功");
    }


    /**
     * 禁用c端操作权限
     * @param id
     * @return
     */
    @PostMapping("cMenu/updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用c端权限")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id,@RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        SysFunctions sysFunctions=sysFunctionService.getById(id);
        if(sysFunctions == null){
            return ResponseEty.failure("权限不存在");
        }
        sysFunctionService.lockSysFunctions(sysFunctions);
        systemLogService.saveMachineLog(token,"禁用","禁用了权限:"+sysFunctions.getTitle());

        return ResponseEty.success("操作成功");
    }

    @Autowired
    SystemSetService systemSetService;//系统设置sevice

    /**
     * 保存系统设置
     * @param systemSet
     * @param token
     * @return
     */
    @PostMapping("systemSet")
    @ResponseBody
    @SysLog("保存系统设置的数据")
    public ResponseEty saveSystemSet(@RequestBody SystemSet systemSet, @RequestHeader(value="token",required = false) Integer token){
        if(systemSet.getSystemId()==null){
            return ResponseEty.failure("系统ID不能为空");
        }



        systemSetService.updateSystemSet(systemSet);

        if(systemSet.getSystemId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        systemLogService.saveMachineLog(token,"保存","保存了系统:"+systemSet.getSystemName());
        return ResponseEty.success("操作成功");
    }

}
