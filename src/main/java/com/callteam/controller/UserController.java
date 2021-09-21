package com.callteam.controller;

import com.callteam.dto.LoginDto;
import com.callteam.dto.UserDto;
import com.callteam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author sachinda
 */

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto){
        return userService.register(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        return userService.login(loginDto);
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(@RequestParam(name = "file",required = false) MultipartFile multipartFile,@RequestParam(name = "userDetailsDto") String userDetailsDto){
        return userService.updateProfile(multipartFile,userDetailsDto);
    }

    @GetMapping("/getProfile/{id}")
    public ResponseEntity<?> getProfile(@PathVariable String id){
        return userService.getProfile(id);
    }
}
