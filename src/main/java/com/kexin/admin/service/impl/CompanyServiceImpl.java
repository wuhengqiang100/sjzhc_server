package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.DataupLog;
import com.kexin.admin.entity.tables.Company;
import com.kexin.admin.entity.tables.Operator;
import com.kexin.admin.entity.vo.Ftp;
import com.kexin.admin.mapper.DataupLogMapper;
import com.kexin.admin.mapper.LoginUserMapper;
import com.kexin.admin.mapper.CompanyMapper;
import com.kexin.admin.mapper.OperatorMapper;
import com.kexin.admin.service.CompanyService;
import com.kexin.common.util.FileUtil.FileUtil;
import com.kexin.common.util.ResponseEty;
import com.kexin.common.util.ftpUtil.FTPUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 公司配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)




    @Override
    public Integer companyCountByCode(String companyCode) {
        QueryWrapper<Company> wrapper = new QueryWrapper<>();
        wrapper.eq("COMPANY_CODE",companyCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer companyCountByName(String companyName) {
        QueryWrapper<Company> wrapper = new QueryWrapper<>();
        wrapper.eq("COMPANY_NAME",companyName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCompany(Company company) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        baseMapper.insert(company);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCompany(Company company) {
//        dropUserRolesByUserId(user.getLoginId());
        baseMapper.updateById(company);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCompany(Company company) {
        baseMapper.deleteById(company.getCompanyId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockCompany(Company company) {
        if (company.getUseFlag()){
            company.setUseFlag(false);
            company.setEndDate(new Date());
        }else{
            company.setUseFlag(true);
            company.setEndDate(null);
        }
        baseMapper.updateById(company);
    }
}
