package com.callteam.service;

import com.callteam.dto.LoginDto;
import org.springframework.http.ResponseEntity;

public interface AdminService {

    public ResponseEntity<?> login(LoginDto loginDto);

}
