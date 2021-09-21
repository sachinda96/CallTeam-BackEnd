package com.callteam.service.impl;

import com.callteam.dto.ResponseDto;
import com.callteam.dto.SportDto;
import com.callteam.entity.CategoryEntity;
import com.callteam.entity.SportEntity;
import com.callteam.repository.CategoryRepository;
import com.callteam.repository.SportRepository;
import com.callteam.service.FileService;
import com.callteam.service.SportService;
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
public class SportServiceImpl implements SportService {


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SportRepository sportRepository;

    @Autowired
    private FileService fileService;


    /**
     *
     * @param multipartFile
     * @param sportDto
     * @return Response Entity
     * Save Sport data to the database and genearate the response
     */
    @Override
    public ResponseEntity<?> save(MultipartFile multipartFile, String sportDto) {

        try {

            SportDto sport = new ObjectMapper().readValue(sportDto,SportDto.class);

            sport.setId(UUID.randomUUID().toString());

            if(multipartFile != null){
                sport.setImagePath(fileService.uploadFile(sport.getId(),multipartFile));
            }

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

    /**
     *
     * @param multipartFile
     * @param sportDto
     * @return Response Entity with update status
     */
    @Override
    public ResponseEntity<?> update(MultipartFile multipartFile, String sportDto) {
        try {

            SportDto sport = new ObjectMapper().readValue(sportDto,SportDto.class);


            if(multipartFile != null){
                sport.setImagePath(fileService.uploadFile(sport.getId(),multipartFile));
            }

            CategoryEntity categoryEntity =  categoryRepository.getByIdAndStatus(sport.getCategoryId(), AppConstance.STATUS_ACTIVE);

            if(categoryEntity == null){
                return new ResponseEntity<>(new ResponseDto("Invalid Category"),HttpStatus.INTERNAL_SERVER_ERROR);
            }


            SportEntity sportEntity = sportRepository.getById(sport.getId());

            sportEntity.setAgeMax(sport.getAgeMax());
            sportEntity.setAgeMin(sport.getAgeMin());
            sportEntity.setImagePath(sport.getImagePath());
            sportEntity.setNumberOfPlayers(sport.getNumberOfPlayers());
            sportEntity.setCategoryEntity(categoryEntity);
            sportEntity.setCreateDate(new Date());
            sportEntity.setDescription(sport.getDescription());
            sportEntity.setId(sport.getId());
            sportEntity.setName(sport.getName());

            sportRepository.save(sportEntity);

            return new ResponseEntity<>(new ResponseDto("Successfully Updated"),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param id
     * @return Response Entity with delete status
     * Change the record as a INACTIVE from the database and generate the response
     */
    @Override
    public ResponseEntity<?> delete(String id) {

        try {

        SportEntity sportEntity = sportRepository.getByIdAndStatus(id,AppConstance.STATUS_ACTIVE);

        sportEntity.setStatus(AppConstance.STATUS_INACTIVE);

        sportRepository.save(sportEntity);

        return new ResponseEntity<>(new ResponseDto("Successfully Deleted"),HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @return
     * Get all data from the database and return all
     */
    @Override
    public ResponseEntity<?> getAll() {

        try {
            List<SportEntity> sportEntityList = sportRepository.findAllByStatus(AppConstance.STATUS_ACTIVE);

            List<SportDto> sportDtoList = new ArrayList<>();
            for (SportEntity sportEntity : sportEntityList) {
                sportDtoList.add(setSportDto(sportEntity));
            }

            return new ResponseEntity<>(sportDtoList,HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     *
     * @param sportEntity
     * @return SportDto
     */
    public SportDto setSportDto(SportEntity sportEntity){
        SportDto sportDto = new SportDto();
        sportDto.setAgeMax(sportEntity.getAgeMax());
        sportDto.setId(sportEntity.getId());
        sportDto.setAgeMin(sportEntity.getAgeMin());
        sportDto.setDescription(sportEntity.getDescription());
        sportDto.setName(sportEntity.getName());
        sportDto.setType(sportEntity.getType());
        sportDto.setNumberOfPlayers(sportEntity.getNumberOfPlayers());
        sportDto.setImagePath(sportEntity.getImagePath());
        sportDto.setCategoryId(sportEntity.getCategoryEntity().getId());
        return sportDto;

    }

    /**
     *
     * @param sportDto
     * @param categoryEntity
     * @return SportEntity
     */
    public SportEntity setSportEntity(SportDto sportDto,CategoryEntity categoryEntity){

        SportEntity sportEntity = new SportEntity();
        sportEntity.setAgeMax(sportDto.getAgeMax());
        sportEntity.setAgeMin(sportDto.getAgeMin());
        sportEntity.setImagePath(sportDto.getImagePath());
        sportEntity.setNumberOfPlayers(sportDto.getNumberOfPlayers());
        sportEntity.setCategoryEntity(categoryEntity);
        sportEntity.setCreateDate(new Date());
        sportEntity.setDescription(sportDto.getDescription());
        sportEntity.setId(sportDto.getId());
        sportEntity.setName(sportDto.getName());
        sportEntity.setStatus(AppConstance.STATUS_ACTIVE);
        return sportEntity;
    }
}
