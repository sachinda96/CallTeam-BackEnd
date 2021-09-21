package com.callteam.service.impl;

import com.callteam.dto.*;
import com.callteam.entity.*;
import com.callteam.repository.*;
import com.callteam.security.JwtTokenProvider;
import com.callteam.service.FileService;
import com.callteam.service.UserService;
import com.callteam.utill.AppConstance;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @Autowired
    private FileService fileService;

    @Autowired
    private UserSportRepository userSportRepository;

    @Autowired
    private SportRepository sportRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     *
     * @param userDto
     * @return Response Entity
     * Save registration data to the database and return registration status
     */
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

    /**
     *
     * @param loginDto
     * @return Response Entity
     * Authentication flow and login with credentials
     */
    @Override
    public ResponseEntity<?> login(LoginDto loginDto) {

        try {

            LoginEntity loginEntity = loginRepository.findByEmailAndStatus(loginDto.getEmail(), AppConstance.STATUS_ACTIVE);

            if(loginEntity != null){

                UserEntity userEntity = userRepository.findByLoginEntityAndStatus(loginEntity,AppConstance.STATUS_ACTIVE);
                if(loginEntity.getPassword().equalsIgnoreCase(loginDto.getPassword())){
                        return new ResponseEntity<>(new LoginResponseDto(userEntity.getId(), jwtTokenProvider.createToken(userEntity.getEmail())),HttpStatus.OK);
                }
            }

            return new ResponseEntity<>(new ResponseDto("Invalid email or password"),HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    /**
     *
     * @param multipartFile
     * @param userDetailsDto
     * @return update profile status
     * Update the profile and save the data to the database
     */
    @Override
    public ResponseEntity<?> updateProfile(MultipartFile multipartFile, String userDetailsDto) {

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            UserDetailsDto userDetails = objectMapper.readValue(userDetailsDto,UserDetailsDto.class);


            UserEntity userEntity = userRepository.getByIdAndStatus(userDetails.getUserId(),AppConstance.STATUS_ACTIVE);

            if(userEntity == null){
                return new ResponseEntity<>(new ResponseDto("Invalid User Details"),HttpStatus.INTERNAL_SERVER_ERROR);
            }

            UserDetailsEntity userDetailsEntity =  userDetailsRepository.findByUserEntityAndStatus(userEntity,AppConstance.STATUS_ACTIVE);

            if(userDetailsEntity == null){
                userDetailsEntity = setUserDetails(userDetails,userEntity);
            }else {
                userDetailsEntity =updateUserDetails(userDetailsEntity,userDetails);

                List<UserSportEntity> userSportEntities = userSportRepository.findAllByUserDetailsEntityAndStatus(userDetailsEntity,AppConstance.STATUS_ACTIVE);

                for (UserSportEntity userSportEntity : userSportEntities) {
                    userSportEntity.setStatus(AppConstance.STATUS_INACTIVE);
                    userDetailsRepository.save(userDetailsEntity);
                }
            }

            if(multipartFile != null){
                userDetailsEntity.setImagePath(fileService.uploadFile(userEntity.getId(),multipartFile));
            }

            userDetailsEntity = userDetailsRepository.save(userDetailsEntity);


            for (String id : userDetails.getSportList()) {
                SportEntity sportEntity = sportRepository.getById(id);

                if(sportEntity != null){
                    userSportRepository.save(setUserSport(userDetailsEntity,sportEntity));
                }
            }

            return new ResponseEntity<>(new ResponseDto("Successfully Update"),HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     *
     * @param userDetailsEntity
     * @param sportEntity
     * @return UserSportEntity
     */
    private UserSportEntity setUserSport(UserDetailsEntity userDetailsEntity, SportEntity sportEntity) {
        UserSportEntity userSportEntity = new UserSportEntity();
        userSportEntity.setSportEntity(sportEntity);
        userSportEntity.setId(UUID.randomUUID().toString());
        userSportEntity.setStatus(AppConstance.STATUS_ACTIVE);
        userSportEntity.setCreateDate(new Date());
        userSportEntity.setUserDetailsEntity(userDetailsEntity);
        userSportEntity.setCreateDate(new Date());
        userSportEntity.setCreateBy(jwtTokenProvider.getUser());
        return userSportEntity;

    }

    /**
     *
     * @param id
     * @return Response Entity
     * Get profile data from the database using user id and generate the response
     */
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
            userDetailsDto.setImagePath(userDetailsEntity.getImagePath());
            userDetailsDto.setSportList(setUserSportList(userDetailsEntity));

        }

        userDetailsDto.setFullName(userEntity.getFullName());
        userDetailsDto.setEmail(userEntity.getEmail());
        userDetailsDto.setUserId(userEntity.getId());

        return userDetailsDto;

    }

    /**
     *
     * @param userDetailsEntity
     * @return
     */
    private List<String> setUserSportList(UserDetailsEntity userDetailsEntity) {

        List<UserSportEntity> userSportEntities = userSportRepository.findAllByUserDetailsEntityAndStatus(userDetailsEntity,AppConstance.STATUS_ACTIVE);

        List<String> sportList = new ArrayList<>();
        for (UserSportEntity userSportEntity : userSportEntities) {
            sportList.add(userSportEntity.getSportEntity().getId());
        }

        return sportList;
    }

    /**
     *
     * @param userDetails
     * @param userEntity
     * @return UserDetailsEntity
     */
    private UserDetailsEntity setUserDetails(UserDetailsDto userDetails,UserEntity userEntity) {
        UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
        userDetailsEntity.setDistrict(userDetails.getDeistic());
        userDetailsEntity.setAboutme(userDetails.getAboutme());
        userDetailsEntity.setSkills(userDetails.getSkills());
        userDetailsEntity.setUserEntity(userEntity);
        userDetailsEntity.setAddress(userDetails.getAddress());
        userDetailsEntity.setCity(userDetails.getCity());
        userDetailsEntity.setCreateDate(new Date());
        userDetailsEntity.setId(UUID.randomUUID().toString());
        userDetailsEntity.setStatus(AppConstance.STATUS_ACTIVE);
        return userDetailsEntity;
    }


    /**
     *
     * @param userDetailsEntity
     * @param userDetails
     * @return UserDetailsEntity
     */
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

    /**
     *
     * @param userDto
     * @param loginEntity
     * @return UserEntity
     */
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

    /**
     *
     * @param userDto
     * @return LoginEntity
     */
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
