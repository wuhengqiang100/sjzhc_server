package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.Tasks;
import com.kexin.admin.entity.tasks.HistoryTaskInfos;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务信息基础表
 */
public interface TasksService extends IService<Tasks> {



    /**
     * 根据时间获取当前日期的子任务信息
     */
    List<HistoryTaskInfos> getTasksByDate(@Param("queryDate") String queryDate, @Param("userId") Integer userId);

    /**
     * 获取今天的任务信息
     */
    List<Tasks> getTasksToday(@Param("userId") Integer userId);

    /**
     * @Description:获取今天未完成的任务( 每人一天只能有一个任务)()
     * @Author: 巫恒强  @Date: 2019/10/10 9:59
     * @Param: [userId]
     * @Return: com.kexin.admin.entity.tables.Tasks
     */
    Tasks getTasksTodayUndone(@Param("userId") Integer userId);

    /**
     * 获取昨天没有完成的任务
     * @param userId
     * @return
     */
    Tasks getTasksYesterdayUndone(@Param("userId") Integer userId);

    /**
     * 获取所有没有完成的任务list
     * @param userId
     * @return
     */
    List<Tasks> getUndoneTasks(@Param("userId") Integer userId);
}
