package com.callteam.repository;

import com.callteam.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author sachinda
 */
public interface UserRepository extends JpaRepository<UserEntity,String> {


}
