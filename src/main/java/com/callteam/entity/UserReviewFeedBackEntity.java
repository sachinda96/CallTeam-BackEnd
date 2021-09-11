package com.callteam.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author sachinda
 */
@Entity
@Table(name = "REVIEWFEEDBACK")
public class UserReviewFeedBackEntity {

    @Id
    @Column(length = 50)
    private String id;

    private String review;

    private Integer rate;

    @Column(length = 10)
    private String status;

    @Column(length = 50)
    private String createBy;

    private Date createDate;

    @Column(length = 50)
    private String updateBy;

    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "review_user_id")
    private UserEntity reviewUser;

    @ManyToOne
    @JoinColumn(name = "receive_user_id")
    private UserEntity receiveUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public UserEntity getReviewUser() {
        return reviewUser;
    }

    public void setReviewUser(UserEntity reviewUser) {
        this.reviewUser = reviewUser;
    }

    public UserEntity getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(UserEntity receiveUser) {
        this.receiveUser = receiveUser;
    }
}
