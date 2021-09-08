package com.callteam.repository;

import com.callteam.entity.SportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SportRepository extends JpaRepository<SportEntity,String> {

    List<SportEntity> findAllByStatus(String statusActive);

    SportEntity getByIdAndStatus(String sportId, String statusActive);
}
