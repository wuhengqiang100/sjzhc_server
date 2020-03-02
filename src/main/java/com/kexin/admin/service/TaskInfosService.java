package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.TaskInfos;
import com.kexin.admin.entity.tables.Tasks;
import com.kexin.admin.entity.tasks.ProductTaskAdd;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务信息分类基础service
 */
public interface TaskInfosService extends IService<TaskInfos> {


    /**
     * 获取具体的详细任务信息,分类的
     * @param tasksList
     * @return
     */
    List<TaskInfos> getInfosByTaskList(List<Tasks> tasksList);

    /**
     * @Description: 根据任务id 获取子任务的详细信息
     * @Author: 巫恒强  @Date: 2019/10/12 10:24
     * @Param: [taskId]
     * @Return: java.util.List<com.kexin.admin.entity.tables.TaskInfos>
     */
    List<TaskInfos> getInfosByTaskId(Integer taskId);

    /**
     * @Description:根据任务Id获取子任务的子任务的具体修改信息
     * @Author: 巫恒强  @Date: 2019/10/12 15:12
     * @Param: [taskId]
     * @Return: java.util.List<com.kexin.admin.entity.tasks.ProductTaskAdd>
     */
    List<ProductTaskAdd> getProductTaskByTaskId(@Param("taskId") Integer taskId);

    /**
     * @Description:根据任务id删除详细任务中的信息
     * @Author: 巫恒强  @Date: 2019/10/16 11:09
     * @Param: [taskId]
     * @Return: java.lang.Integer
     */
    Integer deleteTaskInfosByTaskId(@Param("taskId") Integer taskId);
    /**
     * @Description:删除数目为0的详细任务信息
     * @Author: 巫恒强  @Date: 2019/10/16 14:54
     * @Param: [taskId]
     * @Return: java.lang.Integer
     */
    Integer deleteTaskInfosZero(@Param("taskId") Integer taskId);

}
