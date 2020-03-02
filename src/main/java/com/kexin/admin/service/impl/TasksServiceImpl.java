package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.Tasks;
import com.kexin.admin.entity.tasks.HistoryTaskInfos;
import com.kexin.admin.mapper.TasksMapper;
import com.kexin.admin.service.TasksService;
import com.kexin.common.util.ResponseEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 任务基本信息service
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TasksServiceImpl extends ServiceImpl<TasksMapper, Tasks> implements TasksService {

    @Override
    public List<HistoryTaskInfos> getTasksByDate(String queryDate, Integer userId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return baseMapper.getTasksByDate(queryDate,userId);

    }

    @Override
    public List<Tasks> getTasksToday(Integer userId) {
        return baseMapper.getTasksToday(userId);
    }

    @Override
    public Tasks getTasksTodayUndone(Integer userId) {
        return baseMapper.getTasksTodayUndone(userId);
    }

    @Override
    public Tasks getTasksYesterdayUndone(Integer userId) {
        return baseMapper.getTasksYesterdayUndone(userId);
    }

    @Override
    public List<Tasks> getUndoneTasks(Integer userId) {
        return baseMapper.getUndoneTasks(userId);
    }
}
