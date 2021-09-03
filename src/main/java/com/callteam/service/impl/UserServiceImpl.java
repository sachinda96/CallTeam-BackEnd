package com.callteam.service.impl;

import com.callteam.dto.*;
import com.callteam.entity.LoginEntity;
import com.callteam.entity.UserDetailsEntity;
import com.callteam.entity.UserEntity;
import com.callteam.repository.LoginRepository;
import com.callteam.repository.UserDetailsRepository;
import com.callteam.repository.UserRepository;
import com.callteam.service.UserService;
import com.callteam.utill.AppConstance;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public ResponseEntity<?> register(UserDto userDto) {


        try {


            if(loginRepository.findByEmailAndStatus(userDto.getEmail(),AppConstance.STATUS_ACTIVE) != null){
                return new ResponseEntity<>(new ResponseDto("This user already registered"), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            LoginEntity loginEntity = loginRepository.save(setLoginDetails(userDto));

            userRepository.save(setUser(userDto,loginEntity));

            return new ResponseEntity<>(new ResponseDto("Successfully Registered"), HttpStatus.OK);
        }catch (Exception e){

            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<?> login(LoginDto loginDto) {

        try {

            LoginEntity loginEntity = loginRepository.findByEmailAndStatus(loginDto.getEmail(), AppConstance.STATUS_ACTIVE);

            if(loginEntity != null){

                UserEntity userEntity = userRepository.findByLoginEntityAndStatus(loginEntity,AppConstance.STATUS_ACTIVE);
                if(loginEntity.getPassword().equalsIgnoreCase(loginDto.getPassword())){
                        return new ResponseEntity<>(new LoginResponseDto(userEntity.getId(),"toke"),HttpStatus.OK);
                }
            }

            return new ResponseEntity<>(new ResponseDto("Invalid email or password"),HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @Override
    public ResponseEntity<?> updateProfile(MultipartFile multipartFile, String userDetailsDto) {

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            UserDetailsDto userDetails = objectMapper.readValue(userDetailsDto,UserDetailsDto.class);

            if(multipartFile != null){

            }

            UserEntity userEntity = userRepository.getByIdAndStatus(userDetails.getUserId(),AppConstance.STATUS_ACTIVE);

            if(userEntity == null){
                return new ResponseEntity<>(new ResponseDto("Invalid User Details"),HttpStatus.INTERNAL_SERVER_ERROR);
            }

            UserDetailsEntity userDetailsEntity =  userDetailsRepository.findByUserEntityAndStatus(userEntity,AppConstance.STATUS_ACTIVE);

            if(userDetailsEntity == null){
                userDetailsEntity = setUserDetails(userDetails,userEntity);
            }else {
                userDetailsEntity =updateUserDetails(userDetailsEntity,userDetails);
            }

            userDetailsRepository.save(userDetailsEntity);

            return new ResponseEntity<>(new ResponseDto("Successfully Update"),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<?> getProfile(String id) {

        try {

            UserEntity userEntity = userRepository.getByIdAndStatus(id,AppConstance.STATUS_ACTIVE);

            if(userEntity == null){
                return new ResponseEntity<>(new ResponseDto("Invalid User"),HttpStatus.INTERNAL_SERVER_ERROR);
            }

            UserDetailsEntity userDetailsEntity = userDetailsRepository.findByUserEntityAndStatus(userEntity,AppConstance.STATUS_ACTIVE);


            return new ResponseEntity<>(setUserDto(userEntity,userDetailsEntity),HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private UserDetailsDto setUserDto(UserEntity userEntity, UserDetailsEntity userDetailsEntity) {
        UserDetailsDto userDetailsDto = new UserDetailsDto();

        if(userDetailsEntity != null){
            userDetailsDto.setAboutme(userDetailsEntity.getAboutme());
            userDetailsDto.setAddress(userDetailsEntity.getAddress());
            userDetailsDto.setCity(userDetailsEntity.getCity());
            userDetailsDto.setSkills(userDetailsEntity.getSkills());
            userDetailsDto.setBirthDay(userDetailsEntity.getBirthDay());
            userDetailsDto.setContactNo(userDetailsEntity.getMobileNo());
            userDetailsDto.setDeistic(userDetailsEntity.getDistrict());
        }

        userDetailsDto.setFullName(userEntity.getFullName());
        userDetailsDto.setEmail(userEntity.getEmail());
        userDetailsDto.setUserId(userEntity.getId());

        return userDetailsDto;

    }

    private UserDetailsEntity setUserDetails(UserDetailsDto userDetails,UserEntity userEntity) {
        UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
        userDetailsEntity.setDistrict(userDetails.getDeistic());
        userDetailsEntity.setAboutme(userDetails.getAboutme());
        userDetailsEntity.setSkills(userDetails.getSkills());
        userDetailsEntity.setUserEntity(userEntity);
        //userDetailsEntity.setCreateBy();
        userDetailsEntity.setAddress(userDetails.getAddress());
        userDetailsEntity.setCity(userDetails.getCity());
        userDetailsEntity.setCreateDate(new Date());
        userDetailsEntity.setId(UUID.randomUUID().toString());
        userDetailsEntity.setStatus(AppConstance.STATUS_ACTIVE);
        return userDetailsEntity;
    }


    private UserDetailsEntity updateUserDetails(UserDetailsEntity userDetailsEntity,UserDetailsDto userDetails) {
        userDetailsEntity.setDistrict(userDetails.getDeistic());
        userDetailsEntity.setAboutme(userDetails.getAboutme());
        userDetailsEntity.setSkills(userDetails.getSkills());
        userDetailsEntity.setMobileNo(userDetails.getContactNo());
        userDetailsEntity.setBirthDay(userDetails.getBirthDay());
        userDetailsEntity.setAddress(userDetails.getAddress());
        userDetailsEntity.setCity(userDetails.getCity());
        userDetailsEntity.setUpdateDate(new Date());
        return userDetailsEntity;
    }

    public UserEntity setUser(UserDto userDto, LoginEntity loginEntity) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID().toString());
        userEntity.setFullName(userDto.getFullName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setLoginEntity(loginEntity);
        userEntity.setCreateBy(userDto.getEmail());
        userEntity.setCreateDate(new Date());
        userEntity.setStatus(AppConstance.STATUS_ACTIVE);

        return userEntity;
    }

    public LoginEntity setLoginDetails(UserDto userDto){
        LoginEntity loginEntity =  new LoginEntity();
        loginEntity.setEmail(userDto.getEmail());
        loginEntity.setCreateBy(userDto.getEmail());
        loginEntity.setPassword(userDto.getPassword());
        loginEntity.setStatus(AppConstance.STATUS_ACTIVE);
        loginEntity.setId(UUID.randomUUID().toString());
        loginEntity.setCreateDate(new Date());
        return loginEntity;
    }
}
