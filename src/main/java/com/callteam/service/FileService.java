package com.callteam.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    public String uploadFile(String id, MultipartFile multipartFile)throws Exception;

    public ResponseEntity<?> downloadFile(String filePath);
}
