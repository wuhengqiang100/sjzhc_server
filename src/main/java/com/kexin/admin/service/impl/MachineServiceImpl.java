package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.Machine;
import com.kexin.admin.entity.vo.Ftp;
import com.kexin.admin.mapper.MachineMapper;
import com.kexin.admin.service.MachineService;
import com.kexin.common.util.FileUtil.FileUtil;
import com.kexin.common.util.ResponseEty;
import com.kexin.common.util.ftpUtil.FTPUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 工序配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MachineServiceImpl extends ServiceImpl<MachineMapper, Machine> implements MachineService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)



    @Override
    public Integer machineCountByCode(String machineCode) {
        QueryWrapper<Machine> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_CODE",machineCode);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer machineCountByName(String machineName) {
        QueryWrapper<Machine> wrapper = new QueryWrapper<>();
        wrapper.eq("MACHINE_NAME",machineName);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMachine(Machine machine) {
//        Encodes.entryptPassword(user);
//        user.setIsLock(0);
        baseMapper.insert(machine);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMachine(Machine machine) {
//        dropUserRolesByUserId(user.getLoginId());
        baseMapper.updateById(machine);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMachine(Machine machine) {
        baseMapper.deleteById(machine.getMachineId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockMachine(Machine machine) {
        if (machine.getUseFlag()){
            machine.setUseFlag(false);
            machine.setEndDate(new Date());
        }else{
            machine.setUseFlag(true);
            machine.setEndDate(null);
        }
        baseMapper.updateById(machine);
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
    public ResponseEty uploadTemplate(MultipartFile[] file, String rfilename,Integer addId, HttpServletRequest request, Ftp ftp) {
        List<String> list = new ArrayList<String>();
        if (file != null && file.length > 0) {
            for (int i = 0; i < file.length; i++) {
                MultipartFile files = file[i];
// 保存文件
                list = FileUtil.saveFile(request, files, list,ftp,rfilename);
            }
        }
        Machine machine=baseMapper.selectById(addId);
        for (String s:list) {
            FTPClient ftpClient = FTPUtil.connectFtpServer(ftp.getIpAddr(), ftp.getPort(), ftp.getUserName(), ftp.getPwd(), ftp.getEncoding());
            FTPUtil ftpUtil=new FTPUtil();
            ftpUtil.uploadFiles(ftpClient, new File(s));
            ftpUtil.closeFTPConnect(ftpClient);
            machine.setImageModelPath(rfilename);//这里的路径是否要拼接???!!!!!!
            baseMapper.updateById(machine);
        }

        return ResponseEty.success("操作成功");
    }
}
