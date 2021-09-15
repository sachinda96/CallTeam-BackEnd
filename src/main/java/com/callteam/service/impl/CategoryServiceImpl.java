package com.callteam.service.impl;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.callteam.dto.CategoryDto;
import com.callteam.dto.ResponseDto;
import com.callteam.entity.CategoryEntity;
import com.callteam.repository.CategoryRepository;
import com.callteam.service.CategoryService;
import com.callteam.utill.AppConstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<?> getAll() {

        try {

            List<CategoryEntity> categoryEntityList = categoryRepository.getAllByStatus(AppConstance.STATUS_ACTIVE);


            List<CategoryDto> categoryDtoList = new ArrayList<>();

            for (CategoryEntity categoryEntity : categoryEntityList) {

                CategoryDto categoryDto = new CategoryDto();
                categoryDto.setId(categoryEntity.getId());
                categoryDto.setName(categoryEntity.getName());
                categoryDtoList.add(categoryDto);
            }

            return new ResponseEntity<>(categoryDtoList,HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
