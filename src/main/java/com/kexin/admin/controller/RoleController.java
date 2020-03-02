package com.kexin.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.Role;
import com.kexin.admin.entity.vo.AllFunction;
import com.kexin.admin.service.RoleService;
import com.kexin.admin.service.UserService;
import com.kexin.common.base.PageData;
import com.kexin.common.util.Constants;
import com.kexin.common.util.ResponseEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.Map;

/**
 * @Description:角色控制controller
 * @Author: 巫恒强  @Date: 2019/10/23 12:48
 * @Param:
 * @Return: 
 */
@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    RoleService roleService;



    /**
     * @Description:角色数据表格list get
     * @Author: 巫恒强  @Date: 2019/10/23 12:48
     * @Param: [page, limit, q]
     * @Return: com.kexin.common.base.PageData<com.kexin.admin.entity.tables.Role>
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @GetMapping("list")
    @ResponseBody
    public PageData<Role> list(@RequestParam(value = "pi",defaultValue = "1")Integer page,
                               @RequestParam(value = "ps",defaultValue = "10")Integer limit,
                               @RequestParam(value = "q",required = false) String q
                               ){
//        Map map = WebUtils.getParametersStartingWith(request, "q");
        PageData<Role> rolePageData = new PageData<>();
        QueryWrapper<Role> roleWrapper = new QueryWrapper<>();
        roleWrapper.eq("USE_FLAG",1);
        if(StringUtils.isNotBlank(q)){
            roleWrapper.eq("ROLE_NAME",q);
            roleWrapper.like("ROLE_NAME", q);
//            String keys = (String) map.get("key");
//            if(StringUtils.isNotBlank(keys)) {
//                roleWrapper.like("name", keys);
//            }
        }
        IPage<Role> rolePage = roleService.selecRolePage(new Page<>(page,limit),3,q);

        rolePageData.setTotal(rolePage.getTotal());
        rolePageData.setResults(rolePage.getRecords());
        return rolePageData;
    }

    /**
     * @Description:角色数据表格list post
     * @Author: 巫恒强  @Date: 2019/10/23 12:49
     * @Param: [page, limit, map, request]
     * @Return: com.kexin.common.base.PageData<com.kexin.admin.entity.tables.Role>
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("list")
    @ResponseBody
    public PageData<Role> listPost(@RequestParam(value = "pi",defaultValue = "1")Integer page,
                               @RequestParam(value = "ps",defaultValue = "10")Integer limit,
                               @RequestBody Map map,ServletRequest request){

        PageData<Role> rolePageData = new PageData<>();

        if (map.size()==1){
            IPage<Role> rolePage = roleService.selecRolePage(new Page<>(page,limit),1, (String) map.get("q"));
            rolePageData.setTotal(rolePage.getTotal());
            rolePageData.setResults(rolePage.getRecords());
            return rolePageData;
        }else{
            IPage<Role> rolePage = roleService.selecRolePage(new Page<>(page,limit),1, "");
            rolePageData.setTotal(rolePage.getTotal());
            rolePageData.setResults(rolePage.getRecords());
            return rolePageData;
        }

    }


    /**
     * @Description:角色保存功能
     * @Author: 巫恒强  @Date: 2019/10/23 12:49
     * @Param: [role]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("save")
    @ResponseBody
    public ResponseEntity save(@RequestBody Role role){
        ResponseEntity responseEntity=new ResponseEntity();
        if (role.getRoleId()!=0){//修改操作
            Role oldRole=roleService.getById(role.getRoleId());
            role.setUseFlag(oldRole.getUseFlag());
            Boolean updateTrue=roleService.saveOrUpdate(role);
            Boolean updateRoleString=true;//更新groupString字段
//            Boolean updateRoleString=userService.updateRoleString(oldRole,role);//更新groupString字段
            if(updateTrue && updateRoleString){//更新角色
                if (this.updateGrantRoleToFunctions(role.getRoleId(),role.getFunctions())){
                    return ResponseEntity.success("保存成功");
                }else{
                    return ResponseEntity.failure("保存失败");
                }
            }else{
                return ResponseEntity.failure("保存失败");
            }
        }else{//新增
            Role reRole=roleService.getByMapOr(role);
            if (reRole!=null){
                return ResponseEntity.failure("该角色编号或角色名已使用!");
            }
            Boolean saveTrue=roleService.save(role);
            if(saveTrue){//保存角色
                if (this.updateGrantRoleToFunctions(role.getRoleId(),role.getFunctions())){
                    return ResponseEntity.success("保存成功");
                }else{
                    return ResponseEntity.failure("保存失败");
                }
            }else{
                return ResponseEntity.failure("保存失败");
            }
        }
    }

    /**
     * @Description:角色删除功能
     * @Author: 巫恒强  @Date: 2019/10/23 12:49
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("delete")
    @ResponseBody
    public ResponseEntity delete(@RequestBody Map map){
        if (map.size()==0){
            return ResponseEntity.failure("角色ID不能为空");
        }
//        if(StringUtils.isBlank(id)){
//            return ResponseEntity.failure("角色ID不能为空");
//        }
        int id= (int) map.get("id");

        return roleService.deleteRoleAbout(id);
    }

    /**
     * @Description:角色禁止与启用功能
     * @Author: 巫恒强  @Date: 2019/10/23 12:50
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("forbidden")
    @ResponseBody
    public ResponseEntity forbidden(@RequestBody Map map){
        if (map.size()==0){
            return ResponseEntity.failure("角色ID不能为空");
        }
        int id= (int) map.get("id");
        return roleService.forbiddenRole(id);//禁止或者启用
    }


    /**
     * 更新角色,用户关系表
     * 用户管理1
     * 设备管理2
     * 角色管理3
     * 系统维护4
     * 现场监控5
     * 身份管理6
     * 设备类别7
     * 设备分组8
     * 数据分析9
     * 实时任务10
     * @return
     */
    private Boolean updateGrantRoleToFunctions(int roleId,AllFunction functions){
        StringBuffer funtionString=new StringBuffer();
        try {
            roleService.deleteGrantRole(roleId);
            if (functions.getUser()){
                roleService.updateGrantRoleAndFunctions(roleId, Constants.USER_FUNCTION);
                funtionString.append("用户管理 ");
            }
            if (functions.getFacility()){
                roleService.updateGrantRoleAndFunctions(roleId,Constants.FACILITY);
                funtionString.append("设备管理 ");
            }
            if (functions.getRole()){
                roleService.updateGrantRoleAndFunctions(roleId,Constants.ROLE_FUNCTION);
                funtionString.append("角色管理 ");
            }
            if (functions.getSysrepair()){
                roleService.updateGrantRoleAndFunctions(roleId,Constants.SYSRAPAIR_FUNCTION);
                funtionString.append("系统维护 ");
            }
            if (functions.getMonitor()){
                roleService.updateGrantRoleAndFunctions(roleId,Constants.MONITOR_FUNCTION);
                funtionString.append("现场监控 ");
            }
            if (functions.getIdentity()){
                roleService.updateGrantRoleAndFunctions(roleId,Constants.IDENTITY_FUNCTION);
                funtionString.append("身份管理 ");
            }if (functions.getfType()){
                roleService.updateGrantRoleAndFunctions(roleId,Constants.FACILITY_TYPE);
                funtionString.append("设备类别 ");
            }if (functions.getfGroup()){
                roleService.updateGrantRoleAndFunctions(roleId,Constants.FACILITY_GROUP);
                funtionString.append("设备分组 ");
            }if (functions.getAnalyze()){
                roleService.updateGrantRoleAndFunctions(roleId,Constants.ANALYZE_FUNCTION);
                funtionString.append("数据分析 ");
            }if (functions.getTask()){
                roleService.updateGrantRoleAndFunctions(roleId,Constants.RT_TASK);
                funtionString.append("实时任务 ");
            }
            Role role=roleService.getById(roleId);
            String s=new String(funtionString);
            role.setRoleId(roleId);
            if (StringUtils.isEmpty(s)){
                role.setFunctionString("无权限");
            }else{
                role.setFunctionString(s);
            }

            roleService.updateRole(role);
        } catch (Exception e) {
           return  false;
        }
        return true;
    }

/*
    @Autowired
    RoleService01 roleService;

    @Autowired
    UserService userService;

    @Autowired
    MenuService menuService;

    @GetMapping(value = "list")
    public String list(){
        return "admin/role/list";
    }

//    @RequiresPermissions("sys:role:list")
    @PostMapping("list")
    @ResponseBody
    public PageData<Role01> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                ServletRequest request){
        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageData<Role01> rolePageData = new PageData<>();
        QueryWrapper<Role01> roleWrapper = new QueryWrapper<>();
        roleWrapper.eq("del_flag",false);
        if(!map.isEmpty()){
            String keys = (String) map.get("key");
            if(StringUtils.isNotBlank(keys)) {
                roleWrapper.like("name", keys);
            }
        }
        IPage<Role01> rolePage = roleService.page(new Page<>(page,limit),roleWrapper);
        rolePageData.setCount(rolePage.getTotal());
        rolePageData.setData(setUserToRole(rolePage.getRecords()));
        return rolePageData;
    }

    private List<Role01> setUserToRole(List<Role01> roles){
        roles.forEach(r -> {
            if(StringUtils.isNotBlank(r.getCreateId())){
                User u = userService.findUserById(r.getCreateId());
                if(StringUtils.isBlank(u.getNickName())){
                    u.setNickName(u.getLoginName());
                }
                r.setCreateUser(u);
            }
            if(StringUtils.isNotBlank(r.getUpdateId())){
                User u  = userService.findUserById(r.getUpdateId());
                if(StringUtils.isBlank(u.getNickName())){
                    u.setNickName(u.getLoginName());
                }
                r.setUpdateUser(u);
            }
        });

        return roles;
    }

    @GetMapping("add")
    public String add(ModelMap modelMap){
        Map<String,Object> map =  new HashMap();
        map.put("parentId",null);
        map.put("isShow",false);
        List<Menu> menuList = menuService.selectAllMenus(map);
        modelMap.put("menuList",menuList);
        return "admin/role/add";
    }

//    @RequiresPermissions("sys:role:add")
    @PostMapping("add")
    @ResponseBody
    @SysLog("保存新增角色数据")
    public ResponseEntity add(@RequestBody Role01 role){
        if(StringUtils.isBlank(role.getName())){
            return ResponseEntity.failure("角色名称不能为空");
        }
        if(roleService.getRoleNameCount(role.getName())>0){
            return ResponseEntity.failure("角色名称已存在");
        }
        roleService.saveRole(role);
        return ResponseEntity.success("操作成功");
    }

    @GetMapping("edit")
    public String edit(String id,ModelMap modelMap){
        Role01 role = roleService.getRoleById(id);
        String menuIds = null;
        if(role != null) {
            menuIds  = role.getMenuSet().stream().map(menu -> menu.getId()).collect(Collectors.joining(","));
        }
        Map<String,Object> map = new HashMap();
        map.put("parentId",null);
        map.put("isShow",Boolean.FALSE);
        List<Menu> menuList = menuService.selectAllMenus(map);
        modelMap.put("role",role);
        modelMap.put("menuList",menuList);
        modelMap.put("menuIds",menuIds);
        return "admin/role/edit";
    }

//    @RequiresPermissions("sys:role:edit")
    @PostMapping("edit")
    @ResponseBody
    @SysLog("保存编辑角色数据")
    public ResponseEntity edit(@RequestBody Role01 role){
        if(StringUtils.isBlank(role.getId())){
            return ResponseEntity.failure("角色ID不能为空");
        }
        if(StringUtils.isBlank(role.getName())){
            return ResponseEntity.failure("角色名称不能为空");
        }
        Role01 oldRole = roleService.getRoleById(role.getId());
        if(!oldRole.getName().equals(role.getName())){
            if(roleService.getRoleNameCount(role.getName())>0){
                return ResponseEntity.failure("角色名称已存在");
            }
        }
        roleService.updateRole(role);
        return ResponseEntity.success("操作成功");
    }


//    @RequiresPermissions("sys:role:delete")
    @PostMapping("deleteSome")
    @ResponseBody
    @SysLog("多选删除角色数据")
    public ResponseEntity deleteSome(@RequestBody List<Role01> roles){
        if(roles == null || roles.size()==0){
            return ResponseEntity.failure("请选择需要删除的角色");
        }
        for (Role01 r : roles){
            roleService.deleteRole(r);
        }
        return ResponseEntity.success("操作成功");
    }*/
}
