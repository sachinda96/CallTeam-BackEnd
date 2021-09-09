package com.callteam.repository;

import com.callteam.entity.SportEntity;
import com.callteam.entity.SportPoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SportPoolRepository extends JpaRepository<SportPoolEntity,String> {
    List<SportPoolEntity> findAllBySportEntityInAndCityAndStatus(List<SportEntity> sportEntityList, String city, String statusActive);

    SportPoolEntity getByIdAndStatus(String id, String statusActive);
}
