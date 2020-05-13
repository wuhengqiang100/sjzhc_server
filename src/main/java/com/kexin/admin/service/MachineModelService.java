package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.MachineModel;
import com.kexin.admin.entity.vo.Ftp;
import com.kexin.common.util.ResponseEty;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * 设备模板服务接口类
 */
public interface MachineModelService extends IService<MachineModel> {


    /**
     * 根据设备模板编码计算数量,当前机器的code的数量
     * @param machineModelCode
     * @return
     */
    Integer machineModelCountByCode(@Param("machineModelCode") String machineModelCode);


    /**
     * 根据设备模板名称计算数量
     * @param machineModelName
     * @return
     */
    Integer machineModelCountByName(@Param("machineModelName") String machineModelName);

    /**
     * 保存设备模板
     * @param machineModel
     */
    void saveMachineModel(@Param("machineModel") MachineModel machineModel);


    /**
     * 修改更新设备模板
     * @param machineModel
     */
    void updateMachineModel(@Param("machineModel") MachineModel machineModel);

    /**
     * 删除设备模板(单个)
     * @param machineModel
     */
    void deleteMachineModel(@Param("machineModel") MachineModel machineModel);

    /**
     * 禁用或者启用设备模板
     * @param machineModel
     */
    void lockMachineModel(@Param("machineModel") MachineModel machineModel);

    /**
     * 上传文件到ftp服务器
     * @param file
     * @param rfilename
     * @param addId
     * @param request
     * @param ftp
     * @return
     */
    ResponseEty uploadTemplate(MultipartFile[] file, String rfilename, Integer addId, HttpServletRequest request, Ftp ftp, Integer tokenId);

    /**
     * 从ftp服务器上下载文件到本地
     * @param machineModelId
     * @return
     */
    ResponseEty downloadTemplate(Integer machineModelId);

    ResponseEty getDownloadUrl(Integer machineModelId, Integer tokenId);
}
