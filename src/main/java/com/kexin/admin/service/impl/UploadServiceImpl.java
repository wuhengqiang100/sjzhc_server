package com.kexin.admin.service.impl;

import com.kexin.admin.service.UploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Service
public class UploadServiceImpl implements UploadService {

    @Override
    public String upload(MultipartFile file) throws IOException, NoSuchAlgorithmException {

        return null;
    }
}
