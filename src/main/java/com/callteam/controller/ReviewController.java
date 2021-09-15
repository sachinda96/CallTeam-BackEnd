package com.callteam.controller;

import com.callteam.dto.ReviewDto;
import com.callteam.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody ReviewDto reviewDto){
        return reviewService.addReview(reviewDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewByUserId(@PathVariable String id){
        return reviewService.getReviewByUserId(id);
    }

}
