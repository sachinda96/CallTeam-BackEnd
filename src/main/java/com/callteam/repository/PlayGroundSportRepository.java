package com.callteam.repository;

import com.callteam.entity.PlayGroundEntity;
import com.callteam.entity.PlayGroundSportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayGroundSportRepository extends JpaRepository<PlayGroundSportEntity,String> {
    List<PlayGroundSportEntity> findAllByPlayGroundEntityAndStatus(PlayGroundEntity playGroundEntity, String statusActive);
}
