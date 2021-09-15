package com.callteam.repository;

import com.callteam.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity,String> {

    CategoryEntity getByIdAndStatus(String categoryId, String statusActive);

    List<CategoryEntity> getAllByStatus(String statusActive);
}
