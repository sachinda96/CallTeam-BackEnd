package com.callteam.service.impl;

import com.callteam.dto.ResponseDto;
import com.callteam.dto.SportDto;
import com.callteam.entity.CategoryEntity;
import com.callteam.entity.SportEntity;
import com.callteam.repository.CategoryRepository;
import com.callteam.repository.SportRepository;
import com.callteam.service.SportService;
import com.callteam.utill.AppConstance;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

@Service
public class SportServiceImpl implements SportService {


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SportRepository sportRepository;


    @Override
    public ResponseEntity<?> save(MultipartFile multipartFile, String sportDto) {

        try {

            SportDto sport = new ObjectMapper().readValue(sportDto,SportDto.class);

            CategoryEntity categoryEntity =  categoryRepository.getByIdAndStatus(sport.getCategoryId(), AppConstance.STATUS_ACTIVE);


            if(categoryEntity == null){
                return new ResponseEntity<>(new ResponseDto("Invalid Category"),HttpStatus.INTERNAL_SERVER_ERROR);
            }


            sportRepository.save(setSportEntity(sport,categoryEntity));

            return new ResponseEntity<>(new ResponseDto("Successfully created"),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public SportEntity setSportEntity(SportDto sportDto,CategoryEntity categoryEntity){

        SportEntity sportEntity = new SportEntity();
        sportEntity.setAgeMax(sportDto.getAgeMax());
        sportEntity.setAgeMin(sportDto.getAgeMin());
        sportEntity.setImagePath(sportDto.getImagePath());
        sportEntity.setNumberOfPlayers(sportDto.getNumberOfPlayers());
        sportEntity.setCategoryEntity(categoryEntity);
        sportEntity.setCreateDate(new Date());
        sportEntity.setDescription(sportDto.getDescription());
        sportEntity.setId(UUID.randomUUID().toString());
        sportEntity.setName(sportDto.getName());
        sportEntity.setStatus(AppConstance.STATUS_ACTIVE);
        return sportEntity;
    }
}
