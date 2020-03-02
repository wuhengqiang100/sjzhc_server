package com.kexin.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kexin.admin.entity.tables.TaskInfos;
import com.kexin.admin.entity.tasks.HistoryTaskInfos;
import com.kexin.admin.entity.tasks.ProductTaskAdd;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * tasks
 *  任务信息分类基础mapper
 */
public interface TaskInfosMapper extends BaseMapper<TaskInfos> {

    /**
     * 根据任务Id获取该任务下的,分任务信息
     */
    TaskInfos getInfosByTaskList(@Param("taskId") Integer taskId);


    /**
     * @Description: 根据任务id获取,子任务的信息
     * @Author: 巫恒强  @Date: 2019/10/12 13:20
     * @Param: [taskId]
     * @Return: java.util.List<com.kexin.admin.entity.tables.TaskInfos>
     */
    List<TaskInfos> getInfosByTaskId(@Param("taskId") Integer taskId);

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
