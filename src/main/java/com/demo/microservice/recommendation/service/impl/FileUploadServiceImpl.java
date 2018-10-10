package com.demo.microservice.recommendation.service.impl;

import com.demo.microservice.recommendation.service.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Override
    public File convertMultiPartToFile(MultipartFile file) throws IOException {
        String extension = "tmp";
        int i = file.getOriginalFilename().lastIndexOf('.');
        if (i > 0) {
            extension = file.getOriginalFilename().substring(i + 1);
        }
        File convFile = File.createTempFile("tmp", "." + extension);// /var/apps/docs/partner  D:\Upload\partner
        file.transferTo(convFile);
        return convFile;
    }
}
