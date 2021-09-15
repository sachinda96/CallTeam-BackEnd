package com.callteam.service;

import com.callteam.entity.CategoryEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {

    public ResponseEntity<?> getAll();

}
