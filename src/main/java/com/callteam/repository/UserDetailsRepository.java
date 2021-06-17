package com.callteam.repository;

import com.callteam.entity.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author sachinda
 */
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity,String> {
}
