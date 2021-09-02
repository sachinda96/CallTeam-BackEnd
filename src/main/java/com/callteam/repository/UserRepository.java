package com.callteam.repository;

import com.callteam.entity.LoginEntity;
import com.callteam.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author sachinda
 */
public interface UserRepository extends JpaRepository<UserEntity,String> {


    UserEntity findByLoginEntityAndStatus(LoginEntity loginEntity, String statusActive);

    UserEntity getByIdAndStatus(String userId, String statusActive);
}
