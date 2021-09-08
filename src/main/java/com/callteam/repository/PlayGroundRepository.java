package com.callteam.repository;

import com.callteam.dto.PlayGroundDto;
import com.callteam.entity.PlayGroundEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayGroundRepository extends JpaRepository<PlayGroundEntity,String> {
    List<PlayGroundEntity> findAllByStatus(String statusActive);

    List<PlayGroundEntity> findAllByStatusAndCity(String statusActive, String city);

    PlayGroundEntity getByIdAndStatus(String groundId, String statusActive);
}
