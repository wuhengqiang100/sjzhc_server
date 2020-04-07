package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.Machine;
import com.kexin.admin.entity.vo.Ftp;
import com.kexin.common.util.ResponseEty;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * 设备服务接口类
 */
public interface MachineService extends IService<Machine> {


    /**
     * 根据设备编码计算数量,当前机器的code的数量
     * @param machineCode
     * @return
     */
    Integer machineCountByCode(@Param("machineCode") String machineCode);


    /**
     * 根据设备名称计算数量
     * @param machineName
     * @return
     */
    Integer machineCountByName(@Param("machineName") String machineName);

    /**
     * 保存设备
     * @param machine
     */
    void saveMachine(@Param("machine") Machine machine);


    /**
     * 修改更新设备
     * @param machine
     */
    void updateMachine(@Param("machine") Machine machine);

    /**
     * 删除设备(单个)
     * @param machine
     */
    void deleteMachine(@Param("machine") Machine machine);

    /**
     * 禁用或者启用设备
     * @param machine
     */
    void lockMachine(@Param("machine") Machine machine);

    /**
     * 上传文件到ftp服务器
     * @param file
     * @param rfilename
     * @param addId
     * @param request
     * @param ftp
     * @return
     */
    ResponseEty uploadTemplate(MultipartFile[] file, String rfilename,Integer addId, HttpServletRequest request, Ftp ftp,Integer tokenId);

    /**
     * 从ftp服务器上下载文件到本地
     * @param machineId
     * @return
     */
    ResponseEty downloadTemplate(Integer machineId);

    ResponseEty getDownloadUrl(Integer machineId,Integer tokenId);
}
