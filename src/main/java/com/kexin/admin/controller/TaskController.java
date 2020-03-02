package com.kexin.admin.controller;



import com.kexin.admin.entity.tables.Products;
import com.kexin.admin.entity.tables.TaskInfos;
import com.kexin.admin.entity.tables.Tasks;
import com.kexin.admin.entity.tasks.HistoryTaskInfos;
import com.kexin.admin.entity.tasks.ProductTaskAdd;
import com.kexin.admin.service.ProductsService;
import com.kexin.admin.service.TaskInfosService;
import com.kexin.admin.service.TasksService;
import com.kexin.common.util.ResponseEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 实时任务controller层
 */
@Controller
@RequestMapping("task")
public class TaskController {

    @Autowired
    TasksService tasksService;

    @Autowired
    TaskInfosService taskInfosService;

    @Autowired
    ProductsService productsService;

    /**
     * @Description:任务重定向功能(add/info)
     * @Author: 巫恒强  @Date: 2019/10/23 12:51
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("direction")
    @ResponseBody
    public ResponseEntity direction(@RequestBody Map map){
        ResponseEntity responseEntity=new ResponseEntity();
        String userId= (String) map.get("userId");
        if (null==userId){
            return ResponseEntity.failure("当前token已失效,请重新登录");
        }
        List<Tasks> tasksUndoneList=tasksService.getUndoneTasks(Integer.parseInt(userId));//未完成的所有任务列表
//        Tasks yesterdayTask=new Tasks();//昨天未完成的任务
        Tasks taskToday=new Tasks();//今天未完成的任务
//        yesterdayTask=tasksService.getTasksYesterdayUndone(Integer.parseInt(userId));
        if (tasksUndoneList.size()!=0){//如果有未完成的任务,取最早的一个任务
            responseEntity.setSuccess(true);
//            responseEntity.setAny("taskFlag",true);
            responseEntity.setAny("task",tasksUndoneList.get(0));
            return responseEntity;
        }else{//没有未完结的任务
            responseEntity.setSuccess(false);
//            responseEntity.setAny("taskFlag",false);
//            responseEntity.setAny("task",taskToday);
            return responseEntity;
          /*  taskToday=tasksService.getTasksTodayUndone(Integer.parseInt(userId));
            responseEntity.setSuccess(true);
            if (taskToday==null){
                responseEntity.setAny("taskFlag",false);
            }else{
                responseEntity.setAny("taskFlag",true);
            }
            responseEntity.setAny("task",taskToday);
            return responseEntity;*/
        }
    }

    /**
     * @Description:今天的任务信息(info)
     * @Author: 巫恒强  @Date: 2019/10/23 12:51
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("info")
    @ResponseBody
    public ResponseEntity infos(@RequestBody Map map){
        ResponseEntity responseEntity=new ResponseEntity();
        String userId= (String) map.get("userId");
        if (null==userId){
            return ResponseEntity.failure("当前token已失效,请重新登录");
        }
        List<Tasks> tasksUndoneList=tasksService.getUndoneTasks(Integer.parseInt(userId));//未完成的所有任务列表
        Tasks task=tasksUndoneList.get(0);
//        Tasks task=tasksService.getTasksTodayUndone(Integer.parseInt(userId));
        if(task!=null){
            List<TaskInfos> infosList=taskInfosService.getInfosByTaskId(task.getRecountTaskId());
            responseEntity.setAny("infosList",infosList);
            responseEntity.setSuccess(true);
            responseEntity.setAny("task",task);
            return responseEntity;
        }else{
            return ResponseEntity.failure("当前没有任务,请添加!");
        }

    }

    /**
     * @Description:获取所有的产品信息
     * @Author: 巫恒强  @Date: 2019/10/23 12:51
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("products")
    @ResponseBody
    public ResponseEntity products(@RequestBody Map map){
        ResponseEntity responseEntity=new ResponseEntity();
        List<Products> productsList=new ArrayList<>();
//        List<Products> productsList=productsService.list();
        responseEntity.setSuccess(true);
        responseEntity.setAny("productsList",productsList);
        return responseEntity;
    }

    /**
     * @Description:根据时间获取历史信息
     * @Author: 巫恒强  @Date: 2019/10/23 12:52
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("history")
    @ResponseBody
    public ResponseEntity history(@RequestBody Map map ){
        ResponseEntity responseEntity=new ResponseEntity();
        String queryDate= (String) map.get("queryDate");
        String userId= (String) map.get("userId");
        if (null==userId){
            return ResponseEntity.failure("当前token已失效,请重新登录");
        }
        try{
            List<HistoryTaskInfos> historyList=tasksService.getTasksByDate(queryDate,Integer.parseInt(userId));
            if(historyList.size()==0){
                return ResponseEntity.failure("当前日期没有历史任务");
            }
            responseEntity.setAny("historyList",historyList);
            responseEntity.setSuccess(true);
            return responseEntity;
        }catch (Exception e){
            return ResponseEntity.failure("一个人一天只能有一个任务");
        }
    }

    /**
     * @Description:获取已经分配的任务信息,包括子任务的详细信息
     * @Author: 巫恒强  @Date: 2019/10/23 12:52
     * @Param: [task]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("find")
    @ResponseBody
    public ResponseEntity find(@RequestBody Tasks task ){
        ResponseEntity responseEntity=new ResponseEntity();
        task=tasksService.getById(task.getRecountTaskId());
        taskInfosService.deleteTaskInfosZero(task.getRecountTaskId());
        List<ProductTaskAdd> productIdOwn = taskInfosService.getProductTaskByTaskId(task.getRecountTaskId());
        responseEntity.setSuccess(true);
        responseEntity.setAny("task",task);
        List<Products> productsList=new ArrayList<>();
//        List<Products> productsList=productsService.list();
        responseEntity.setAny("productsList",productsList);
        responseEntity.setAny("productIdOwn",productIdOwn);
        return responseEntity;
    }

    /**
     * @Description:添加任务功能(add)
     * @Author: 巫恒强  @Date: 2019/10/23 12:53
     * @Param: [task]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("add")
    @ResponseBody
    public ResponseEntity add(@RequestBody Tasks task ){

        String recountTaskCode= task.getRecountTaskCode();
        Integer recountNum= task.getRecountNum();
//        String note= task.getNote();
        Integer recordOperatorId= task.getRecordOperatorId();
        List<ProductTaskAdd> productIdsList= task.getProductIds();
        if (StringUtils.isBlank(recountTaskCode)){
            return ResponseEntity.failure("请输入任务编号");
        } if (recountNum==null){
            return ResponseEntity.failure("请输入复点总数");
        } if (recordOperatorId==null){
            return ResponseEntity.failure("当前用户已失效,请重新登录");
        } if (productIdsList.size()==0){
            return ResponseEntity.failure("请至少选择一个产品类型并分配数量");
        }

        task.setRecountTaskDate(new Date());
//        task.setStartDate(new Date());
        Boolean taskSaveTrue=tasksService.save(task);//保存总任务信息
        TaskInfos taskInfos=new TaskInfos();
        if (taskSaveTrue){
            try {
                for (ProductTaskAdd m:productIdsList) {
                    if (m.getCount()!=null){
                        taskInfos.setProductId(m.getProductId());
                        taskInfos.setRecountTaskId(task.getRecountTaskId());
                        taskInfos.setRecountNum(m.getCount());
                        taskInfosService.save(taskInfos);
                    }
                }
            }catch (Exception e){
                return ResponseEntity.failure("保存失败");
            }
        }else{
            return ResponseEntity.failure("保存失败");
        }

        return ResponseEntity.success("添加成功");

    }

    /**
     * @Description:修改任务功能
     * @Author: 巫恒强  @Date: 2019/10/23 12:53
     * @Param: [task]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("edit")
    @ResponseBody
    public ResponseEntity edit(@RequestBody Tasks task ){
        String recountTaskCode= task.getRecountTaskCode();
        Integer recountNum= task.getRecountNum();
//        String note= task.getNote();
        Integer recordOperatorId= task.getRecordOperatorId();
        List<ProductTaskAdd> productIdsList= task.getProductIds();
        if (StringUtils.isBlank(recountTaskCode)){
            return ResponseEntity.failure("请输入任务编号");
        } if (recountNum==null){
            return ResponseEntity.failure("请输入复点总数");
        } if (recordOperatorId==null){
            return ResponseEntity.failure("当前用户已失效,请重新登录");
        } if (productIdsList.size()==0){
            return ResponseEntity.failure("请至少选择一个产品类型并分配数量");
        }

        task.setRecountTaskDate(new Date());
        Boolean taskSaveTrue=tasksService.saveOrUpdate(task);//保存总任务信息
        //删除任务分类表中的信息
        Integer deleteInfos=taskInfosService.deleteTaskInfosByTaskId(task.getRecountTaskId());

        TaskInfos taskInfos=new TaskInfos();
        if (taskSaveTrue && deleteInfos>0){
            try {
                for (ProductTaskAdd m:productIdsList) {
                    if (m.getCount()!=null){
                        taskInfos.setProductId(m.getProductId());
                        taskInfos.setRecountTaskId(task.getRecountTaskId());
                        taskInfos.setRecountNum(m.getCount());
                        taskInfosService.save(taskInfos);
                    }
                }
            }catch (Exception e){
                return ResponseEntity.failure("保存失败");
            }
        }else{
            return ResponseEntity.failure("保存失败");
        }

        return ResponseEntity.success("添加成功");
    }

    /**
     * @Description:完结任务功能
     * @Author: 巫恒强  @Date: 2019/10/23 12:53
     * @Param: [task]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("complete")
    @ResponseBody
    public ResponseEntity complete(@RequestBody Tasks task ){

        if(task.getRecountTaskId()==null){
            return ResponseEntity.failure("数据错误,任务Id为空");
        }
        task=tasksService.getById(task.getRecountTaskId());
        task.setFinishedFlag(1);
        Boolean saveTrue=tasksService.saveOrUpdate(task);
        if(saveTrue){
            return ResponseEntity.success("任务已完结");
        }else{
            return ResponseEntity.failure("任务完结失败");
        }

    }
}
