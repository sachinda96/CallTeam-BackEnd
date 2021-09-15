package com.callteam.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface SportService {

    public ResponseEntity<?> save(MultipartFile multipartFile,String sportDto);

    public ResponseEntity<?> update(MultipartFile multipartFile,String sportDto);

    public ResponseEntity<?> delete(String id);

    public ResponseEntity<?> getAll();
}
