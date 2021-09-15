package com.callteam.service;


import com.callteam.dto.ReviewDto;
import org.springframework.http.ResponseEntity;

public interface ReviewService {

    public ResponseEntity<?> addReview(ReviewDto reviewDto);

    public ResponseEntity<?> getReviewByUserId(String id);

}
