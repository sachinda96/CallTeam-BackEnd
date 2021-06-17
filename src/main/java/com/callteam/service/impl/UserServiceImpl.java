package com.callteam.service.impl;

import com.callteam.dto.UserDto;
import com.callteam.entity.UserDetailsEntity;
import com.callteam.entity.UserEntity;
import com.callteam.repository.UserDetailsRepository;
import com.callteam.repository.UserRepository;
import com.callteam.service.UserService;
import com.callteam.utill.AppConstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @author sachinda
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public ResponseEntity<?> addUser(UserDto userDto) {


        try {

            UserEntity userEntity = new UserEntity();
            userEntity.setId(UUID.randomUUID().toString());
            userEntity.setCreateDate(new Date());
            userEntity.setUserName(userDto.getUsername());
            userEntity.setPassword(userDto.getPassword());
            userEntity.setStatus(AppConstance.STATUS_ACTIVE);
            userRepository.save(userEntity);


            UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
            userDetailsEntity.setUserEntity(userEntity);
            userDetailsEntity.setAddress(userDto.getAddress());
            userDetailsEntity.setCity(userDto.getCity());
            userDetailsEntity.setCreateDate(new Date());
            userDetailsEntity.setId(UUID.randomUUID().toString());
            userDetailsEntity.setExperience(userDto.getExperience());
            userDetailsEntity.setStatus(AppConstance.STATUS_ACTIVE);
            userDetailsEntity.setMobileNo(userDto.getMobileNo());
            userDetailsRepository.save(userDetailsEntity);

            return new ResponseEntity<>("200", HttpStatus.OK);


        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
