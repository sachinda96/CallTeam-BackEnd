package com.callteam.service.impl;

import com.callteam.dto.PlayersDto;
import com.callteam.dto.ResponseDto;
import com.callteam.entity.UserDetailsEntity;
import com.callteam.entity.UserEntity;
import com.callteam.repository.UserDetailsRepository;
import com.callteam.repository.UserRepository;
import com.callteam.service.PlayerService;
import com.callteam.utill.AppConstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public ResponseEntity<?> getAllPlayers() {

        try {

            List<UserEntity> userEntityList = userRepository.findAllByStatus(AppConstance.STATUS_ACTIVE);

            List<PlayersDto> playersDtoList = new ArrayList<>();

            for (UserEntity userEntity : userEntityList) {

                UserDetailsEntity userDetailsEntity = userDetailsRepository.findByUserEntityAndStatus(userEntity,AppConstance.STATUS_ACTIVE);

                if(userDetailsEntity != null){
                    playersDtoList.add(setPlayerDto(userDetailsEntity));
                }
            }

            return new ResponseEntity<>(playersDtoList, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private PlayersDto setPlayerDto(UserDetailsEntity userDetailsEntity) {
        PlayersDto playersDto = new PlayersDto();
        playersDto.setAge(calculateAge(userDetailsEntity.getBirthDay()));
        playersDto.setCity(userDetailsEntity.getCity());
        playersDto.setFullName(userDetailsEntity.getUserEntity().getFullName());
        playersDto.setId(userDetailsEntity.getUserEntity().getId());
        playersDto.setImagePath(userDetailsEntity.getImagePath());
        return playersDto;

    }

    public Integer calculateAge(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        Calendar cal2 = Calendar.getInstance();
        int i = 0;
        while (cal1.before(cal2)) {
            cal1.add(Calendar.YEAR, 1);
            i += 1;
        }
        return i;
    }
}
