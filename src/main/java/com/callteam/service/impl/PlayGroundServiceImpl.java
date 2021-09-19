package com.callteam.service.impl;

import com.callteam.dto.PlayGroundDto;
import com.callteam.dto.PlayGroundSportDto;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

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
                playGroundDto.setImagePath(fileService.uploadFile(playGroundDto.getId() ,multipartFile));
            }

            PlayGroundEntity playGroundEntity = playGroundRepository.save(setPlaygroundEntity(playGroundDto));

            for (PlayGroundSportDto playGroundSportDto : playGroundDto.getPlayGroundSportDtoList()) {
                SportEntity sportEntity = sportRepository.getById(playGroundSportDto.getSportId());

                    if(sportEntity != null){
                        playGroundSportRepository.save(setPlaygroundSportEntity(sportEntity,playGroundEntity,playGroundSportDto));
                    }
            }

            return new ResponseEntity<>(new ResponseDto("Successfully Saved"),HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<?> update(MultipartFile multipartFile, String playGround) {
        try {

            PlayGroundDto playGroundDto = new ObjectMapper().readValue(playGround,PlayGroundDto.class);


            if(multipartFile != null){
                playGroundDto.setImagePath(fileService.uploadFile(playGroundDto.getId() ,multipartFile));
            }

            PlayGroundEntity playGroundEntity = playGroundRepository.getByIdAndStatus(playGroundDto.getId(),AppConstance.STATUS_ACTIVE);

            if(playGroundEntity == null){
                return new ResponseEntity<>(new ResponseDto("Invalid Ground"),HttpStatus.INTERNAL_SERVER_ERROR);
            }


           List<PlayGroundSportEntity> playGroundSportEntityList = playGroundSportRepository.findAllByPlayGroundEntityAndStatus(playGroundEntity,AppConstance.STATUS_ACTIVE);

           playGroundEntity = playGroundRepository.save(setPlaygroundEntity(playGroundEntity, playGroundDto));


            for (PlayGroundSportEntity playGroundSportEntity:
                 playGroundSportEntityList) {
                playGroundSportEntity.setStatus(AppConstance.STATUS_INACTIVE);
                playGroundSportEntity.setUpdateBy(jwtTokenProvider.getUser());
                playGroundSportEntity.setUpdateDate(new Date());
                playGroundSportRepository.save(playGroundSportEntity);
            }

            for (PlayGroundSportDto playGroundSportDto : playGroundDto.getPlayGroundSportDtoList()) {
                SportEntity sportEntity = sportRepository.getById(playGroundSportDto.getSportId());

                if(sportEntity != null){
                    playGroundSportRepository.save(setPlaygroundSportEntity(sportEntity,playGroundEntity,playGroundSportDto));
                }
            }

            return new ResponseEntity<>(new ResponseDto("Successfully Updated"),HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<?> delete(String id) {

        try {

            PlayGroundEntity playGroundEntity = playGroundRepository.getByIdAndStatus(id,AppConstance.STATUS_ACTIVE);

            if(playGroundEntity == null){
                return new ResponseEntity<>(new ResponseDto("Invalid Ground"),HttpStatus.INTERNAL_SERVER_ERROR);
            }


            List<PlayGroundSportEntity> playGroundSportEntityList = playGroundSportRepository.findAllByPlayGroundEntityAndStatus(playGroundEntity,AppConstance.STATUS_ACTIVE);

            playGroundEntity.setStatus(AppConstance.STATUS_INACTIVE);
            playGroundEntity.setUpdateBy(jwtTokenProvider.getUser());
            playGroundEntity.setUpdateDate(new Date());
            playGroundRepository.save(playGroundEntity);

            for (PlayGroundSportEntity playGroundSportEntity:
                    playGroundSportEntityList) {
                playGroundSportEntity.setStatus(AppConstance.STATUS_INACTIVE);
                playGroundSportEntity.setUpdateBy(jwtTokenProvider.getUser());
                playGroundSportEntity.setUpdateDate(new Date());
                playGroundSportRepository.save(playGroundSportEntity);
            }

            return new ResponseEntity<>(new ResponseDto("Successfully Deleted"),HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<?> getById(String id) {

        try {

            PlayGroundEntity playGroundEntity = playGroundRepository.getByIdAndStatus(id,AppConstance.STATUS_ACTIVE);

            if(playGroundEntity == null){
                return new ResponseEntity<>(new ResponseDto("Invalid PlayGround"),HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>(setPlayGround(playGroundEntity),HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @Override
    public ResponseEntity<?> getAllBySportId(String id) {
        try {

            SportEntity sportEntity = sportRepository.getByIdAndStatus(id,AppConstance.STATUS_ACTIVE);

            if(sportEntity == null){
                return new ResponseEntity<>(new ResponseDto("Invalid"),HttpStatus.INTERNAL_SERVER_ERROR);
            }

            List<PlayGroundSportEntity> playGroundSportEntityList = playGroundSportRepository.findAllBySportEntityAndStatus(sportEntity,AppConstance.STATUS_ACTIVE);


            List<PlayGroundDto> playGroundDtoList = new ArrayList<>();

            for (PlayGroundSportEntity playGroundSportEntity : playGroundSportEntityList) {
                    playGroundDtoList.add(setPlayGroundDto(playGroundSportEntity.getPlayGroundEntity(),playGroundSportEntity));
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

    @Override
    public ResponseEntity pagesCount() {

        try {

            List<Integer> pageCount= new ArrayList<>();

            long count = playGroundRepository.count();

            if(count <= AppConstance.PAGE_GROUND_DATA_COUNT){
                pageCount.add(1);
            }else {
                if(count % AppConstance.PAGE_GROUND_DATA_COUNT != 0){
                    count = count / AppConstance.PAGE_GROUND_DATA_COUNT + 1;

                }else{
                    count = count / AppConstance.PAGE_GROUND_DATA_COUNT;
                }

                for (int x = 0; x <= count;x++){
                    pageCount.add(x+1);
                }
            }

            return new ResponseEntity(pageCount,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @Override
    public ResponseEntity<?> getAllByPage(int index) {

        try {

            Pageable paging = PageRequest.of(index, AppConstance.PAGE_GROUND_DATA_COUNT);
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
        playGroundDto.setDescription(playGroundEntity.getDescription());
        playGroundDto.setCity(playGroundEntity.getCity());
        playGroundDto.setStatus(validateOpenClose(playGroundEntity));
        playGroundDto.setContactNo(playGroundEntity.getContactNumber());

        List<PlayGroundSportEntity> playGroundSportEntities = playGroundSportRepository.findAllByPlayGroundEntityAndStatus(playGroundEntity,AppConstance.STATUS_ACTIVE);

        List<PlayGroundSportDto> playGroundSportDtoList = new ArrayList<>();

        for (PlayGroundSportEntity playGroundSportEntity : playGroundSportEntities) {
            playGroundSportDtoList.add(setPlayGroundSportDto(playGroundSportEntity));
        }

        playGroundDto.setPlayGroundSportDtoList(playGroundSportDtoList);

        return playGroundDto;
    }

    public PlayGroundDto setPlayGroundDto(PlayGroundEntity playGroundEntity,PlayGroundSportEntity playGroundSportEntity){
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
        playGroundDto.setDescription(playGroundEntity.getDescription());
        playGroundDto.setCity(playGroundEntity.getCity());
        playGroundDto.setStatus(validateOpenClose(playGroundEntity));
        playGroundDto.setContactNo(playGroundEntity.getContactNumber());

        if(playGroundSportEntity.getPricePerHour() == null && playGroundSportEntity.getPricePerHour() == 0){
            playGroundDto.setPrice("FREE");
        }else {
            playGroundDto.setPrice(playGroundSportEntity.getPricePerHour().toString());
        }



        return playGroundDto;
    }

    private String validateOpenClose(PlayGroundEntity playGroundEntity) {

        try {

            String status = "OPEN";
            Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_WEEK);

            if(!playGroundEntity.getCloseDays().isEmpty()){
                if(playGroundEntity.getCloseDays().contains(getDate(day))){
                    status = "CLOSED";
                }
            }

            Calendar calendarNow = Calendar.getInstance();
            calendarNow.setTime(new Date());

            Date openTime = new SimpleDateFormat("HH:mm").parse(playGroundEntity.getOpenTIme());
            Calendar calendarOpen = Calendar.getInstance();
            calendarOpen.setTime(openTime);



            Date closeTime = new SimpleDateFormat("HH:mm").parse(playGroundEntity.getCloseTime());
            Calendar calendarClose = Calendar.getInstance();
            calendarClose.setTime(closeTime);

            if (openTime.after(new Date()) && calendarNow.before(calendarClose.getTime())) {
                status = "CLOSED";
            }

            return status;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }



    }

    private PlayGroundSportDto setPlayGroundSportDto(PlayGroundSportEntity playGroundSportEntity) {
        PlayGroundSportDto playGroundSportDto = new PlayGroundSportDto();
        playGroundSportDto.setSport(playGroundSportEntity.getSportEntity().getName());
        playGroundSportDto.setSportId(playGroundSportEntity.getSportEntity().getId());
        playGroundSportDto.setNoOfTeams(playGroundSportEntity.getNoOfTeams());
        playGroundSportDto.setId(playGroundSportEntity.getId());
        playGroundSportDto.setPricePerHour(playGroundSportEntity.getPricePerHour());
        return playGroundSportDto;
    }

    private PlayGroundSportEntity setPlaygroundSportEntity(SportEntity sportEntity, PlayGroundEntity playGroundEntity,PlayGroundSportDto playGroundSportDto) {

        PlayGroundSportEntity playGroundSportEntity = new PlayGroundSportEntity();
        playGroundSportEntity.setPlayGroundEntity(playGroundEntity);
        playGroundSportEntity.setSportEntity(sportEntity);
        playGroundSportEntity.setCreateBy(jwtTokenProvider.getUser());
        playGroundSportEntity.setStatus(AppConstance.STATUS_ACTIVE);
        playGroundSportEntity.setCreateDate(new Date());
        playGroundSportEntity.setId(UUID.randomUUID().toString());
        playGroundSportEntity.setNoOfTeams(playGroundSportDto.getNoOfTeams());
        playGroundSportEntity.setPricePerHour(playGroundSportDto.getPricePerHour());
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
        playGroundEntity.setContactNumber(playGroundDto.getContactNo());
        playGroundEntity.setCity(playGroundDto.getCity());
        playGroundEntity.setDescription(playGroundDto.getDescription());
        playGroundEntity.setImagePath(playGroundDto.getImagePath());
        return playGroundEntity;
    }


    private PlayGroundEntity setPlaygroundEntity(PlayGroundEntity playGroundEntity,PlayGroundDto playGroundDto) {
        playGroundEntity.setAddress(playGroundDto.getAddress());
        playGroundEntity.setCity(playGroundDto.getCity());
        playGroundEntity.setDistrict(playGroundDto.getDistrict());
        playGroundEntity.setLatitude(playGroundDto.getLatitude());
        playGroundEntity.setUpdateDate(new Date());
        playGroundEntity.setUpdateBy(jwtTokenProvider.getUser());
        playGroundEntity.setLongitude(playGroundDto.getLongitude());
        playGroundEntity.setName(playGroundDto.getName());
        playGroundEntity.setCloseDays(playGroundDto.getCloseDays());
        playGroundEntity.setOpenTIme(playGroundDto.getOpenTime());
        playGroundEntity.setCloseTime(playGroundDto.getCloseTime());
        playGroundEntity.setImagePath(playGroundDto.getImagePath());
        playGroundEntity.setContactNumber(playGroundDto.getContactNo());
        playGroundEntity.setCity(playGroundDto.getCity());
        playGroundEntity.setDescription(playGroundDto.getDescription());
        return playGroundEntity;
    }

    private String getDate(int day){
        switch (day) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
        }
        return null;
    }
}
