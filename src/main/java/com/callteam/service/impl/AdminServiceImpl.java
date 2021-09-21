package com.callteam.service.impl;

import com.callteam.dto.LoginDto;
import com.callteam.dto.LoginResponseDto;
import com.callteam.dto.ResponseDto;
import com.callteam.entity.AdminEntity;
import com.callteam.repository.AdminRepository;
import com.callteam.security.JwtTokenProvider;
import com.callteam.service.AdminService;
import com.callteam.utill.AppConstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Value("${email}")
    private String adminEmail;

    @Value("${password}")
    private String adminPassword;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public ResponseEntity<?> login(LoginDto loginDto) {
        try {

            AdminEntity adminEntity = adminRepository.findByEmailAndStatus(loginDto.getEmail(), AppConstance.STATUS_ACTIVE);

            if(adminEntity != null){
                if(adminEntity.getPassword().equalsIgnoreCase(loginDto.getPassword())){
                    return new ResponseEntity<>(new LoginResponseDto(adminEntity.getId(),jwtTokenProvider.createToken(adminEntity.getEmail())),HttpStatus.OK);
                }
            }else{
                if(loginDto.getEmail().equalsIgnoreCase(adminEmail) && loginDto.getPassword().equalsIgnoreCase(adminPassword)){
                    return new ResponseEntity<>(new LoginResponseDto(loginDto.getEmail(),jwtTokenProvider.createToken(adminEmail)),HttpStatus.OK);
                }
            }


            return new ResponseEntity<>(new ResponseDto("Invalid email or password"),HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
