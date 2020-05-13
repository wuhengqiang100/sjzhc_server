package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.DataupLog;
import com.kexin.admin.entity.tables.MachineModel;
import com.kexin.admin.entity.tables.Operator;
import com.kexin.admin.entity.vo.Ftp;
import com.kexin.admin.mapper.DataupLogMapper;
import com.kexin.admin.mapper.LoginUserMapper;
import com.kexin.admin.mapper.MachineModelMapper;
import com.kexin.admin.mapper.OperatorMapper;
import com.kexin.admin.service.MachineModelService;
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
 * 设备模板配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MachineModelServiceImpl extends ServiceImpl<MachineModelMapper, MachineModel> implements MachineModelService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)

    @Resource
    Ftp ftp;

    @Resource
    DataupLogMapper dataupLogMapper;//上传日志mapper

    @Resource
    LoginUserMapper loginUserMapper;//登录用户mapper

    @Resource
    OperatorMapper operatorMapper;//人员mapper

    @Override
    public Integer machineModelCountByCode(String machineModelCode) {
        QueryWrapper<MachineModel> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_MODEL_CODE",machineModelCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer machineModelCountByName(String machineModelName) {
        QueryWrapper<MachineModel> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_MODEL_NAME",machineModelName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMachineModel(MachineModel machineModel) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        baseMapper.insert(machineModel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMachineModel(MachineModel machineModel) {
//        dropUserRolesByUserId(user.getLoginId());
        baseMapper.updateById(machineModel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMachineModel(MachineModel machineModel) {
        baseMapper.deleteById(machineModel.getMachineModelId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockMachineModel(MachineModel machineModel) {
        if (machineModel.getUseFlag()){//禁用
            machineModel.setUseFlag(false);
            machineModel.setEndDate(new Date());
        }else{//启用
            machineModel.setUseFlag(true);
            machineModel.setEndDate(null);
            machineModel.setStartDate(new Date());
        }
        baseMapper.updateById(machineModel);
    }



    /**
     * 设备模板上传服务
     * @param file
     * @param rfilename
     * @param request
     * @param ftp
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEty uploadTemplate(MultipartFile[] file, String rfilename,Integer addId, HttpServletRequest request, Ftp ftp,Integer tokenId) {
        List<String> list = new ArrayList<String>();
        String[] suffixs=new String[file.length];
        if (file.length>1){
            return ResponseEty.failure("只能上传一个文件");
        }
        MachineModel machineModel=baseMapper.selectById(addId);
        machineModel.setMachineModelNum(machineModel.getMachineModelNum()+1);//版本号+1
        String fileName=machineModel.getMachineModelNum()+rfilename;
        machineModel.setFileName(fileName);
        if (file != null && file.length > 0) {
            for (int i = 0; i < file.length; i++) {
                MultipartFile files = file[i];

// 保存文件
                list = FileUtil.saveFile(request, files, list,ftp,fileName);
                suffixs[i]=FileUtil.getSuffix(files);
            }
        }


        String s; // 本地文件的全地址
        String suffix;//文件的后缀
        for (int j=0;j<list.size();j++){
            FTPClient ftpClient = FTPUtil.connectFtpServer(ftp.getIpAddr(), ftp.getPort(), ftp.getUserName(), ftp.getPwd(), ftp.getEncoding());
//            FTPUtil ftpUtil=new FTPUtil();
            s=list.get(j);
            suffix=suffixs[j];

//            String modelName=machineModel.getMachineModelName();


            Map map = FTPUtil.uploadMachineModelFiles(ftpClient, new File(s),ftp,machineModel);

            Boolean isSuccess= (Boolean) map.get("success");
            String message= (String) map.get("message");
            if (isSuccess){//上传成功
                FTPUtil.closeFTPConnect(ftpClient);
                rfilename=ftp.getRemotepath()+rfilename+'\\'+fileName+suffix;
                machineModel.setMachineModelPath(rfilename);
//                machineModel.setMachineModelName(modelName);//重命名新的模板名称
                baseMapper.updateById(machineModel);//更新machineModel后的上传路径
                //加日志

            }else{//上传失败
                return ResponseEty.failure(message);
            }
        }
        for (String s1:list) {
            FileUtil.DeleteFolder(s1);//删除本地暂存的文件
        }

        return ResponseEty.success("文件上传成功");
    }

    @Override
    public ResponseEty downloadTemplate(Integer machineModelId) {
        MachineModel machineModel=baseMapper.selectById(machineModelId);
        System.out.println("-----------------------应用启动------------------------");
        FTPClient ftpClient = FTPUtil.connectFtpServer(ftp.getIpAddr(), ftp.getPort(), ftp.getUserName(), ftp.getPwd(), ftp.getEncoding());
//        FTPUtil.downloadSingleFile(ftpClient, ftp.getLocalpath(), machineModel.getImageModelPath());
        FTPUtil.closeFTPConnect(ftpClient);
        System.out.println("-----------------------应用关闭------------------------");
        return ResponseEty.success("下载成功");
    }

    @Override
    public ResponseEty getDownloadUrl(Integer machineModelId,Integer tokenId) {
        ResponseEty responseEty=new ResponseEty();

        MachineModel machineModel=baseMapper.selectById(machineModelId);
//        LoginUser loginUser=loginUserMapper.selectById(tokenId);
        Operator operator=operatorMapper.selectById(tokenId);
        DataupLog dataupLog=new DataupLog();
        dataupLog.setDateupSetDate(new Date());
        dataupLog.setOperatorId(operator.getOperatorId());
        dataupLog.setOperatorName(operator.getOperatorName());
        dataupLog.setNote(operator.getOperatorName()+"下载了"+machineModel.getMachineModelName()+"的机检模板");
        dataupLogMapper.insert(dataupLog);//上传日志新增下载记录
//        String url="ftp://"+ftp.getUserName()+":"+ftp.getPwd()+"@"+ftp.getIpAddr()+'\\'+machineModel.getImageModelPath();
        responseEty.setSuccess(20000);
//        responseEty.setAny("ftpUrl",url);
        return responseEty;
    }
}