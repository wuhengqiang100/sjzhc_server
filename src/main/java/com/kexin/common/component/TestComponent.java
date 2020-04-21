package com.kexin.common.component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kexin.admin.entity.tables.Operator;
import com.kexin.admin.mapper.OperatorMapper;
import com.kexin.admin.service.OperatorService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TestComponent {

    @Resource
    OperatorService operatorService;

    @Resource
    OperatorMapper operatorMapper;

    public List<Operator>  list(){
        List<Operator> operatorList=operatorService.list();
        List<Operator> operatorList2=operatorMapper.selectList(new QueryWrapper<>());
        return operatorList;
    }

    public static void main(String[] args) {

        new TestComponent(). list();
    }
}
