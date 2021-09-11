package com.callteam.service.impl;

import com.callteam.dto.PlayerDto;
import com.callteam.dto.PlayersDto;
import com.callteam.dto.ResponseDto;
import com.callteam.entity.UserDetailsEntity;
import com.callteam.entity.UserEntity;
import com.callteam.entity.UserSportEntity;
import com.callteam.repository.UserDetailsRepository;
import com.callteam.repository.UserRepository;
import com.callteam.repository.UserSportRepository;
import com.callteam.service.PlayerService;
import com.callteam.utill.AppConstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
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

    @Autowired
    private UserSportRepository userSportRepository;

    @Override
    public ResponseEntity<?> getAllPlayers() {

        try {

            List<UserEntity> userEntityList = userRepository.findAllByStatus(AppConstance.STATUS_ACTIVE);

            List<PlayersDto> playersDtoList = new ArrayList<>();

            for (UserEntity userEntity : userEntityList) {

                UserDetailsEntity userDetailsEntity = userDetailsRepository.findByUserEntityAndStatus(userEntity,AppConstance.STATUS_ACTIVE);

                if(userDetailsEntity != null){
                    playersDtoList.add(setPlayersDto(userDetailsEntity));
                }
            }

            return new ResponseEntity<>(playersDtoList, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<?> getPlayersById(String id) {

        try {

            UserEntity userEntity = userRepository.getByIdAndStatus(id,AppConstance.STATUS_ACTIVE);

            if(userEntity == null){
                return new ResponseEntity<>(new ResponseDto("Invalid User"),HttpStatus.INTERNAL_SERVER_ERROR);
            }

            UserDetailsEntity userDetailsEntity = userDetailsRepository.findByUserEntityAndStatus(userEntity,AppConstance.STATUS_ACTIVE);

            return new ResponseEntity<>(setPlayerDto(userDetailsEntity),HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    private PlayerDto setPlayerDto(UserDetailsEntity userDetailsEntity) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setWeight(userDetailsEntity.getWeight());
        playerDto.setTotalTournament(setTotalTournaments(userDetailsEntity));
        playerDto.setTotalMatch(setTotalMatch(userDetailsEntity));
        playerDto.setSportList(setSportList(userDetailsEntity));
        playerDto.setSkills(userDetailsEntity.getSkills());
        playerDto.setImagePath(userDetailsEntity.getImagePath());
        playerDto.setId(userDetailsEntity.getUserEntity().getId());
        playerDto.setHeight(userDetailsEntity.getHeight());
        playerDto.setFullName(userDetailsEntity.getUserEntity().getFullName());
        playerDto.setEmail(userDetailsEntity.getUserEntity().getEmail());
        playerDto.setContactNo(userDetailsEntity.getMobileNo());
        playerDto.setCity(userDetailsEntity.getCity());
        playerDto.setAboutme(userDetailsEntity.getAboutme());
        playerDto.setBirthDay(userDetailsEntity.getBirthDay());
        playerDto.setDeistic(userDetailsEntity.getDistrict());
        playerDto.setAddress(userDetailsEntity.getAddress());
        return playerDto;

    }

    private List<String> setSportList(UserDetailsEntity userDetailsEntity) {

        List<UserSportEntity> sportEntityList = userSportRepository.findAllByUserDetailsEntityAndStatus(userDetailsEntity,AppConstance.STATUS_ACTIVE);

        List<String> sportList = new ArrayList<>();

        for (UserSportEntity userSportEntity : sportEntityList) {
            if(userSportEntity.getSportEntity().getStatus().equalsIgnoreCase(AppConstance.STATUS_ACTIVE)){
                sportList.add(userSportEntity.getSportEntity().getName());
            }
        }

        return sportList;
    }

    private Integer setTotalMatch(UserDetailsEntity userDetailsEntity) {
        return 0;
    }

    private Integer setTotalTournaments(UserDetailsEntity userDetailsEntity) {
        return 0;
    }

    private PlayersDto setPlayersDto(UserDetailsEntity userDetailsEntity) {
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
