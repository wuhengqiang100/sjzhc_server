package com.kexin.common.util.FileUtil;

import com.kexin.admin.entity.vo.Ftp;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

public class FileUtil {


    public static List<String> saveFile(HttpServletRequest request,
                                        MultipartFile file, List<String> list, Ftp ftp,String rfilename) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                // 保存的文件路径(如果用的是Tomcat服务器，文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中
                String realPath = request.getSession().getServletContext().getRealPath("");
                //                String uploadPath= PropertiesUtil.get("/module.properties", "mall.uploadPath");
                //                File dir =new File(ftp.getLocalpath());
                //                String filePath = dir+ file.getOriginalFilename();

//                String filePath = ftp.getLocalpath()+ file.getOriginalFilename();
                //获取文件的原始名称
                String originalFilename = file.getOriginalFilename();//timg (1).jpg
                //获取最后一个.的位置
                int lastIndexOf = originalFilename.lastIndexOf(".");
                //获取文件的后缀名 .jpg
                String suffix = originalFilename.substring(lastIndexOf);
                String filePath = ftp.getLocalpath()+ rfilename+suffix;
                //                String filePath = realPath+uploadPath.replaceAll("/","\\\\") + file.getOriginalFilename();
                System.out.println(filePath);
                list.add(filePath);
                File saveDir = new File(filePath);
                if (!saveDir.getParentFile().exists())
                    saveDir.getParentFile().mkdirs();

                // 转存文件
                file.transferTo(saveDir);
                return list;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}
