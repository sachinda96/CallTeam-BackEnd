package com.callteam.service.impl;

import com.callteam.dto.PlayGroundDto;
import com.callteam.dto.ResponseDto;
import com.callteam.entity.PlayGroundEntity;
import com.callteam.entity.PlayGroundSportEntity;
import com.callteam.entity.SportEntity;
import com.callteam.repository.PlayGroundRepository;
import com.callteam.repository.PlayGroundSportRepository;
import com.callteam.repository.SportRepository;
import com.callteam.security.JwtTokenProvider;
import com.callteam.service.FileService;
import com.callteam.service.PlayGroundService;
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

@Service
public class PlayGroundServiceImpl implements PlayGroundService {

    @Autowired
    private SportRepository sportRepository;

    @Autowired
    private PlayGroundRepository playGroundRepository;

    @Autowired
    private PlayGroundSportRepository playGroundSportRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private FileService fileService;

    @Override
    public ResponseEntity<?> save(MultipartFile multipartFile, String playGround) {

        try {

            PlayGroundDto playGroundDto = new ObjectMapper().readValue(playGround,PlayGroundDto.class);

            playGroundDto.setId(UUID.randomUUID().toString());

            if(multipartFile != null){
                fileService.uploadFile(playGroundDto.getId() ,multipartFile);
            }

            PlayGroundEntity playGroundEntity = playGroundRepository.save(setPlaygroundEntity(playGroundDto));

            for (String id : playGroundDto.getSportList()) {
                SportEntity sportEntity = sportRepository.getById(id);

                    if(sportEntity != null){
                        playGroundSportRepository.save(setPlaygroundSportEntity(sportEntity,playGroundEntity));
                    }
            }

            return new ResponseEntity<>(new ResponseDto("Successfully Saved"),HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<?> getAll() {

        try {

            List<PlayGroundEntity> playGroundEntityList = playGroundRepository.findAllByStatus(AppConstance.STATUS_ACTIVE);

            List<PlayGroundDto> playGroundDtoList = new ArrayList<>();

            for (PlayGroundEntity playGroundEntity : playGroundEntityList) {
                playGroundDtoList.add(setPlayGround(playGroundEntity));
            }

            return new ResponseEntity<>(playGroundDtoList,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @Override
    public ResponseEntity<?> getAllByCity(String city) {

        try {

            List<PlayGroundEntity> playGroundEntityList = playGroundRepository.findAllByStatusAndCity(AppConstance.STATUS_ACTIVE,city);

            List<PlayGroundDto> playGroundDtoList = new ArrayList<>();

            for (PlayGroundEntity playGroundEntity : playGroundEntityList) {
                playGroundDtoList.add(setPlayGround(playGroundEntity));
            }

            return new ResponseEntity<>(playGroundDtoList,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    private PlayGroundDto setPlayGround(PlayGroundEntity playGroundEntity) {
        PlayGroundDto playGroundDto = new PlayGroundDto();
        playGroundDto.setAddress(playGroundEntity.getAddress());
        playGroundDto.setId(playGroundEntity.getId());
        playGroundDto.setOpenTime(playGroundEntity.getOpenTIme());
        playGroundDto.setName(playGroundEntity.getName());
        playGroundDto.setLongitude(playGroundEntity.getLongitude());
        playGroundDto.setLatitude(playGroundEntity.getLatitude());
        playGroundDto.setDistrict(playGroundEntity.getDistrict());
        playGroundDto.setCloseTime(playGroundEntity.getCloseTime());
        playGroundDto.setCloseDays(playGroundDto.getCloseDays());
        playGroundDto.setCity(playGroundDto.getCity());
        playGroundDto.setName(playGroundDto.getName());
        playGroundDto.setImagePath(playGroundEntity.getImagePath());
        return playGroundDto;
    }

    private PlayGroundSportEntity setPlaygroundSportEntity(SportEntity sportEntity, PlayGroundEntity playGroundEntity) {

        PlayGroundSportEntity playGroundSportEntity = new PlayGroundSportEntity();
        playGroundSportEntity.setPlayGroundEntity(playGroundEntity);
        playGroundSportEntity.setSportEntity(sportEntity);
        playGroundSportEntity.setCreateBy(jwtTokenProvider.getUser());
        playGroundSportEntity.setStatus(AppConstance.STATUS_ACTIVE);
        playGroundSportEntity.setCreateDate(new Date());
        playGroundSportEntity.setId(UUID.randomUUID().toString());
        return playGroundSportEntity;
    }

    private PlayGroundEntity setPlaygroundEntity(PlayGroundDto playGroundDto) {
        PlayGroundEntity playGroundEntity = new PlayGroundEntity();
        playGroundEntity.setAddress(playGroundDto.getAddress());
        playGroundEntity.setCity(playGroundDto.getCity());
        playGroundEntity.setDistrict(playGroundDto.getDistrict());
        playGroundEntity.setId(playGroundDto.getId());
        playGroundEntity.setLatitude(playGroundDto.getLatitude());
        playGroundEntity.setCreateDate(new Date());
        playGroundEntity.setLongitude(playGroundDto.getLongitude());
        playGroundEntity.setName(playGroundDto.getName());
        playGroundEntity.setCloseDays(playGroundDto.getCloseDays());
        playGroundEntity.setOpenTIme(playGroundDto.getOpenTime());
        playGroundEntity.setCloseTime(playGroundDto.getCloseTime());
        playGroundEntity.setStatus(AppConstance.STATUS_ACTIVE);
        playGroundEntity.setImagePath(playGroundDto.getImagePath());
        return playGroundEntity;
    }
}
