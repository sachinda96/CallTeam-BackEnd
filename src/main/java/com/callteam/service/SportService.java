package com.callteam.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface SportService {

    public ResponseEntity<?> save(MultipartFile multipartFile,String sportDto);
}
