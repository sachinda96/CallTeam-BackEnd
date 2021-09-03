package com.callteam.repository;

import com.callteam.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity,String> {

    CategoryEntity getByIdAndStatus(String categoryId, String statusActive);
}
