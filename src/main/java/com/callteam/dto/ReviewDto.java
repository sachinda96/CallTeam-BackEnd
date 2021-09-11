package com.callteam.dto;

import javax.xml.crypto.Data;

public class ReviewDto {

    private String id;
    private String reviewUserId;
    private String reviveUserId;
    private String reviewUserName;
    private Integer rate;
    private Data createDate;
    private String review;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReviewUserId() {
        return reviewUserId;
    }

    public void setReviewUserId(String reviewUserId) {
        this.reviewUserId = reviewUserId;
    }

    public String getReviveUserId() {
        return reviveUserId;
    }

    public void setReviveUserId(String reviveUserId) {
        this.reviveUserId = reviveUserId;
    }

    public String getReviewUserName() {
        return reviewUserName;
    }

    public void setReviewUserName(String reviewUserName) {
        this.reviewUserName = reviewUserName;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Data getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Data createDate) {
        this.createDate = createDate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
