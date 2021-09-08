package com.callteam.service;

import com.callteam.dto.PlayGroundDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface PlayGroundService {

    public ResponseEntity<?> save(MultipartFile multipartFile,String playGroundDto);

    public ResponseEntity<?> getAll();

    public ResponseEntity<?> getAllByCity(String city);
}
