package com.callteam.repository;

import com.callteam.entity.SportEntity;
import com.callteam.entity.TournamentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TournamentRepository extends JpaRepository<TournamentEntity,String> {

    List<TournamentEntity> findAllBySportEntityInAndCityAndStatus(List<SportEntity> sportEntityList, String city, String statusActive);

    TournamentEntity getByIdAndStatus(String id, String statusActive);
}
