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
     * 根据模板的工序+设备+品种判断此条添加的数据是不是唯一的
     * @param machineModel
     * @return
     */
    Integer machineModelCountByOperationMachineProduct(@Param("machineModel") MachineModel machineModel);

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
     * 最新的文件上传服务
     * @param file
     * @param machineModelId
     * @param tokenId
     * @return
     */
    ResponseEty uploadTemplate1(MultipartFile[] file,  Integer machineModelId,HttpServletRequest request,   Integer tokenId,Integer token);

    /**
     * 从ftp服务器上下载文件到本地
     * @param machineModelId
     * @return
     */
    ResponseEty downloadTemplate(Integer machineModelId,Integer token);

    ResponseEty getDownloadUrl(Integer machineModelId, Integer tokenId,Integer token);
}
