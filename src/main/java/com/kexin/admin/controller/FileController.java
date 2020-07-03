package com.kexin.admin.controller;

import com.kexin.common.annotation.SysLog;
import org.apache.commons.lang3.StringUtils;
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

    /**
     * 处理文件上传
     */
/*
    @PostMapping(value = "/upload")
    @SysLog("文件上传")
    public String uploading(@RequestParam("file") MultipartFile file) {
        File targetFile = new File(utilPath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try (FileOutputStream out = new FileOutputStream(utilPath + file.getOriginalFilename());){
            out.write(file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return "uploading failure";
        }
        return "uploading success";
    }
*/

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
}
