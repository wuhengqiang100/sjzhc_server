package com.kexin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kexin.admin.entity.Rescource;
import com.kexin.admin.service.UploadService;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {

    @Override
    public String upload(MultipartFile file) throws IOException, NoSuchAlgorithmException {

        return null;
    }
}
