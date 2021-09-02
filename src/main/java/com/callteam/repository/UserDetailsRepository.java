package com.callteam.repository;

import com.callteam.entity.UserDetailsEntity;
import com.callteam.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author sachinda
 */
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity,String> {
    UserDetailsEntity findByUserEntityAndStatus(UserEntity userEntity, String statusActive);
}
