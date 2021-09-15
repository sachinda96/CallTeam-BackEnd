package com.callteam.repository;

import com.callteam.entity.UserEntity;
import com.callteam.entity.UserReviewFeedBackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserReviewRepository extends JpaRepository<UserReviewFeedBackEntity,String> {
    List<UserReviewFeedBackEntity> findAllByReceiveUserAndStatus(UserEntity userEntity, String statusActive);
}