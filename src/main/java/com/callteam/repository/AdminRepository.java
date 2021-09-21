package com.callteam.repository;

import com.callteam.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity,String> {
    AdminEntity findByEmailAndStatus(String email, String statusActive);
}
