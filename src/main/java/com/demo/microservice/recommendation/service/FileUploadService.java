package com.demo.microservice.recommendation.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileUploadService {
    File convertMultiPartToFile(MultipartFile file) throws IOException;
}
