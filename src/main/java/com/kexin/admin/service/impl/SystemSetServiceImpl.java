package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kexin.admin.entity.tables.QaInspectMaster;
import com.kexin.admin.entity.tables.SystemSet;
import com.kexin.admin.mapper.SystemSetMapper;
import com.kexin.admin.service.SystemSetService;
import com.kexin.common.util.ResponseEty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.util.Date;

/**
 * 系统设置配置service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemSetServiceImpl extends ServiceImpl<SystemSetMapper, SystemSet> implements SystemSetService {
    //新增和编辑加上,事务回滚时用到
    //@Transactional(rollbackFor = Exception.class)
    @Value("${imgPath}")
    private String imgPath;

    @Override
    public SystemSet getSystemSetById(Integer factoryId) {

        SystemSet systemSet=baseMapper.selectById(factoryId);
        return systemSet;
    }

    @Override
    public SystemSet getSystemSet() {
        QueryWrapper<SystemSet> systemSetQueryWrapper=new QueryWrapper<>();
        SystemSet systemSet=baseMapper.selectOne(systemSetQueryWrapper);
        return systemSet;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSystemSet(SystemSet systemSet) {


        baseMapper.insert(systemSet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSystemSet(SystemSet systemSet) {

        baseMapper.updateById(systemSet);
    }

    @Override
    public ResponseEty uploadLoginBg(MultipartFile file) {
        ResponseEty responseEty=new ResponseEty();
        QueryWrapper<SystemSet> systemSetQueryWrapper=new QueryWrapper<>();

//        Blob blob = null;
        try {

            File targetFile = new File(imgPath);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }

            String originalFilename=file.getOriginalFilename();

            //获取文件的原始名称
//            String originalFilename = "tim.g (1).jpg";//timg (1).jpg
            //获取文件的后缀名 .jpg
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

//            System.out.println("suffix = " + suffix);
            String newFileName="loginBg"+suffix;
            try (FileOutputStream out = new FileOutputStream(imgPath + "/" +newFileName)){
                out.write(file.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
//            SystemSet systemSet=baseMapper.selectOne(systemSetQueryWrapper);
//            String loginBg="/file/img/"+ newFileName;
//            systemSet.setLoginBg(loginBg);
//            baseMapper.updateById(systemSet);

            responseEty.setSuccess(20000);
            responseEty.setMessage("上传成功!");
        } catch (Exception e) {
            responseEty.setSuccess(0);
            responseEty.setMessage("系统配置错误,只能有一条数据");
        }finally {
            return responseEty;
        }

    }

}
