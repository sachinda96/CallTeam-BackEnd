package com.callteam.service;

import com.callteam.dto.PlayGroundDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface PlayGroundService {

    public ResponseEntity<?> save(MultipartFile multipartFile,String playGroundDto);

    public ResponseEntity<?> update(MultipartFile multipartFile,String playGroundDto);

    public ResponseEntity<?> delete(String id);

    public ResponseEntity<?> getAll();

    public ResponseEntity<?> getById(String id);

    public ResponseEntity<?> getAllBySportId(String id);

    public ResponseEntity<?> getAllByCity(String city);

    public ResponseEntity pagesCount();

    public ResponseEntity<?> getAllByPage(int index);
}
