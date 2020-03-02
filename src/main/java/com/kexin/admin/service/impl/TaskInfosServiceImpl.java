package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.TaskInfos;
import com.kexin.admin.entity.tables.Tasks;
import com.kexin.admin.entity.tasks.ProductTaskAdd;
import com.kexin.admin.mapper.TaskInfosMapper;
import com.kexin.admin.mapper.TasksMapper;
import com.kexin.admin.service.TaskInfosService;
import com.kexin.admin.service.TasksService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务基本分类信息service
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TaskInfosServiceImpl extends ServiceImpl<TaskInfosMapper, TaskInfos> implements TaskInfosService {

    @Override
    public List<TaskInfos> getInfosByTaskList(List<Tasks> tasksList) {

        List<TaskInfos> allInfosList=new ArrayList<>();
        for (Tasks t:tasksList) {//每一个人每一天只能有一个大任务
            allInfosList.add(baseMapper.getInfosByTaskList(t.getRecountTaskId()));
        }
        return allInfosList;
    }

    @Override
    public List<TaskInfos> getInfosByTaskId(Integer taskId) {
        List<TaskInfos> infosList=baseMapper.getInfosByTaskId(taskId);
        return infosList;
    }

    @Override
    public List<ProductTaskAdd> getProductTaskByTaskId(Integer taskId) {
        return baseMapper.getProductTaskByTaskId(taskId);
    }

    @Override
    public Integer deleteTaskInfosByTaskId(Integer taskId) {
        return baseMapper.deleteTaskInfosByTaskId(taskId);
    }

    @Override
    public Integer deleteTaskInfosZero(Integer taskId) {
        return baseMapper.deleteTaskInfosZero(taskId);
    }
}
