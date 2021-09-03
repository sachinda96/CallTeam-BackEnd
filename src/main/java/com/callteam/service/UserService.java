package com.callteam.service;

import com.callteam.dto.LoginDto;
import com.callteam.dto.UserDetailsDto;
import com.callteam.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author sachinda
 */
public interface UserService {

    public ResponseEntity<?> register(UserDto userDto);

    public ResponseEntity<?> login(LoginDto loginDto);

    public ResponseEntity<?> updateProfile(MultipartFile multipartFile,String userDetailsDto);

    public ResponseEntity<?> getProfile(String id);
}
