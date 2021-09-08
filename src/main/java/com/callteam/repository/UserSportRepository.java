package com.callteam.repository;

import com.callteam.entity.UserDetailsEntity;
import com.callteam.entity.UserSportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSportRepository extends JpaRepository<UserSportEntity,String> {
    List<UserSportEntity> findAllByUserDetailsEntityAndStatus(UserDetailsEntity userDetailsEntity, String statusActive);
}
