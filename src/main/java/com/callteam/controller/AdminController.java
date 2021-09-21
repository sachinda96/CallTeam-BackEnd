package com.callteam.controller;

import com.callteam.dto.LoginDto;
import com.callteam.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        return adminService.login(loginDto);
    }
}
