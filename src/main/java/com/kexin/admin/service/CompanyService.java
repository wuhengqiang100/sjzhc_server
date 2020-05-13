package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.Company;
import com.kexin.admin.entity.vo.Ftp;
import com.kexin.common.util.ResponseEty;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * 公司服务接口类
 */
public interface CompanyService extends IService<Company> {


    /**
     * 根据公司编码计算数量,当前机器的code的数量
     * @param companyCode
     * @return
     */
    Integer companyCountByCode(@Param("companyCode") String companyCode);


    /**
     * 根据公司名称计算数量
     * @param companyName
     * @return
     */
    Integer companyCountByName(@Param("companyName") String companyName);

    /**
     * 保存公司
     * @param company
     */
    void saveCompany(@Param("company") Company company);


    /**
     * 修改更新公司
     * @param company
     */
    void updateCompany(@Param("company") Company company);

    /**
     * 删除公司(单个)
     * @param company
     */
    void deleteCompany(@Param("company") Company company);

    /**
     * 禁用或者启用公司
     * @param company
     */
    void lockCompany(@Param("company") Company company);

}
