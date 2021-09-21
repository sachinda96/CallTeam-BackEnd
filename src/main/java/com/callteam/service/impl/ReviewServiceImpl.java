package com.callteam.service.impl;

import com.callteam.dto.ResponseDto;
import com.callteam.dto.ReviewDto;
import com.callteam.entity.UserDetailsEntity;
import com.callteam.entity.UserEntity;
import com.callteam.entity.UserReviewFeedBackEntity;
import com.callteam.repository.UserDetailsRepository;
import com.callteam.repository.UserRepository;
import com.callteam.repository.UserReviewRepository;
import com.callteam.security.JwtTokenProvider;
import com.callteam.service.ReviewService;
import com.callteam.utill.AppConstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private UserReviewRepository userReviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     *
     * @param reviewDto
     * @return Response with save status
     * Save Review to the database and generate the response
     */
    @Override
    public ResponseEntity<?> addReview(ReviewDto reviewDto) {

        try {


            UserEntity reviewUserEntity = userRepository.getByIdAndStatus(reviewDto.getReviewUserId(), AppConstance.STATUS_ACTIVE);


            UserEntity receiveUserEntity = userRepository.getByIdAndStatus(reviewDto.getReceiveUserId(), AppConstance.STATUS_ACTIVE);


            if(reviewUserEntity == null && receiveUserEntity ==null){
                return new ResponseEntity<>(new ResponseDto("Invalid User"),HttpStatus.INTERNAL_SERVER_ERROR);
            }

            UserReviewFeedBackEntity userReviewFeedBackEntity = new UserReviewFeedBackEntity();
            userReviewFeedBackEntity.setReview(reviewDto.getReview());
            userReviewFeedBackEntity.setReviewUser(reviewUserEntity);
            userReviewFeedBackEntity.setCreateBy(jwtTokenProvider.getUser());
            userReviewFeedBackEntity.setId(UUID.randomUUID().toString());
            userReviewFeedBackEntity.setStatus(AppConstance.STATUS_ACTIVE);
            userReviewFeedBackEntity.setReceiveUser(receiveUserEntity);
            userReviewFeedBackEntity.setRate(reviewDto.getRate());
            userReviewFeedBackEntity.setCreateDate(new Date());

            userReviewRepository.save(userReviewFeedBackEntity);

            return new ResponseEntity<>(new ResponseDto("Successfully Added"),HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     *
     * @param id
     * @return Response with review details
     * Get Review details By User id
     */
    @Override
    public ResponseEntity<?> getReviewByUserId(String id) {

        try {

            UserEntity userEntity = userRepository.getByIdAndStatus(id, AppConstance.STATUS_ACTIVE);

            if(userEntity == null){
                return new ResponseEntity<>(new ResponseDto("Invalid User"),HttpStatus.INTERNAL_SERVER_ERROR);
            }

            List<UserReviewFeedBackEntity> userReviewFeedBackEntities = userReviewRepository.findAllByReceiveUserAndStatus(userEntity,AppConstance.STATUS_ACTIVE);

            List<ReviewDto> reviewDtoList = new ArrayList<>();

            for (UserReviewFeedBackEntity userReviewFeedBackEntity : userReviewFeedBackEntities) {
                ReviewDto reviewDto = new ReviewDto();
                reviewDto.setReceiveUserId(userReviewFeedBackEntity.getReceiveUser().getId());
                reviewDto.setReview(userReviewFeedBackEntity.getReview());
                reviewDto.setReceiveUserName(userReviewFeedBackEntity.getReceiveUser().getFullName());
                reviewDto.setId(userReviewFeedBackEntity.getId());
                reviewDto.setCreateDate(userReviewFeedBackEntity.getCreateDate());
                reviewDto.setRate(userReviewFeedBackEntity.getRate());
                reviewDto.setReviewUserName(userReviewFeedBackEntity.getReviewUser().getFullName());
                reviewDto.setReviewUserId(userReviewFeedBackEntity.getReviewUser().getId());

                UserDetailsEntity userDetailsEntity = userDetailsRepository.findByUserEntityAndStatus(userReviewFeedBackEntity.getReviewUser(),AppConstance.STATUS_ACTIVE);

                if(userDetailsEntity != null){
                    reviewDto.setImagePath(userDetailsEntity.getImagePath());
                }

                reviewDtoList.add(reviewDto);
            }

            return new ResponseEntity<>(reviewDtoList,HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
