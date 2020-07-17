package com.kexin.admin.controller;

import com.kexin.admin.service.SystemSetService;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.util.ResponseEty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;


/**
 * 文件下载controller
 */
@Controller
@RequestMapping("file")
public class FileController {

    @Value("${utilPath}")
    private String utilPath;
    @Value("${imgPath}")
    private String imgPath;
    @Autowired
    SystemSetService systemSetService;//系统设置service

    /**
     * 处理文件上传
     */
    @PostMapping(value = "/upload")
    @SysLog("文件上传")
    @ResponseBody
    public ResponseEty uploading(@RequestParam("file") MultipartFile file) {
//        File targetFile = new File(utilPath);
        return systemSetService.uploadLoginBg(file);
    }

    /**
     * 文件下载
     * @param response
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/download")
    @ResponseBody
    @SysLog("文件下载")
    public void downLoad(HttpServletResponse response,@RequestParam("id") Integer id) throws UnsupportedEncodingException {
        String filename = null;
        if (id==1){
            filename="Firefox-ESR-full-latest.exe";
        }else if (id==2){
            filename="install_flash_player_cn.exe";
        }
//        String filePath = "D:/file" ;
        File file = new File(utilPath + "/" + filename);
//        File file = new File( "../file/" + filename);
        if(file.exists()){
            response.setContentType("application/octet-stream");
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(filename,"utf8"));
            byte[] buffer = new byte[1024];
            //输出流
            OutputStream os = null;
            try(FileInputStream fis= new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);) {
                os = response.getOutputStream();
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    /**
     * 更改登陆北京文件
     */
    @PostMapping(value = "/editBg")
    @SysLog("修改背景图片")
    @ResponseBody
    public ResponseEty editBg(@RequestParam("fileName") String fileName) {
        ResponseEty responseEty=new ResponseEty();
        String newFileName=fileName.substring(21);
        newFileName=".."+newFileName;
        File targetFile = new File(imgPath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {

            FileInputStream inputFile=new FileInputStream(newFileName);
//            InputStream inputStream=new FileInputStream(newFileName);
//            File file=new File(String.valueOf(inputFile));
            FileOutputStream outFile=new FileOutputStream(imgPath+"/loginBg.jpg");
            outFile.write(inputFile.readAllBytes());

            responseEty.setMessage("修改成功");
            responseEty.setSuccess(20000);

        } catch (Exception e) {
            e.printStackTrace();
            responseEty.setMessage("修改出错");
        }
        return responseEty;
        }

}
