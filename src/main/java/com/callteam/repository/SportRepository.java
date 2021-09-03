package com.callteam.repository;

import com.callteam.entity.SportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportRepository extends JpaRepository<SportEntity,String> {
}
