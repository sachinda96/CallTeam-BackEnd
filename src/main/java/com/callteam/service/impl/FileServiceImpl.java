package com.callteam.service.impl;

import com.callteam.service.FileService;
import com.callteam.utill.CloudConfig;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private CloudConfig cloudConfig;

    @Value("${bucket}")
    private String bucketName;

    @Value("${filepath}")
    private String filepath;

    @Override
    public String uploadFile(String id, MultipartFile multipartFile)throws Exception {
        try {

            StorageOptions options = cloudConfig.configStorage();
            Storage storage = options.getService();

            Bucket bucket = storage.get(bucketName);

            bucket.create(id, multipartFile.getBytes(), multipartFile.getContentType());

            return generateURL(id);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    private String generateURL(String id) {
        return filepath+id;
    }

    @Override
    public ResponseEntity<?> downloadFile(String filePath) {
        try {

            StorageOptions options = cloudConfig.configStorage();

            Storage storage = options.getService();

            byte[] read = storage.readAllBytes(BlobId.of(bucketName, filePath));

            InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(read));
            return ResponseEntity.ok()
                    .headers(new HttpHeaders())
                    .contentLength(read.length)
                    .contentType(MediaType.valueOf("application/octet-stream"))
                    .body(resource);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
