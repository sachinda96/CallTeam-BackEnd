package com.callteam.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author sachinda
 */
@Entity
@Table(name = "SPORTPOOL")
public class SportPoolEntity {

    @Id
    @Column(length = 50)
    private String id;

    private String poolId;

    private Date startDate;

    private String name;

    private String description;

    private String district;

    private String city;

    private String startTime;

    private String endTime;

    private Integer noOfPlayers;

    @Column(length = 10)
    private String status;

    @Column(length = 50)
    private String createBy;

    private Date createDate;

    @Column(length = 50)
    private String updateBy;

    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "sport_id")
    private SportEntity sportEntity;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private PaymentEntity paymentEntity;

    @ManyToOne
    @JoinColumn(name = "playground_id")
    private PlayGroundEntity playGroundEntity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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

    public SportEntity getSportEntity() {
        return sportEntity;
    }

    public void setSportEntity(SportEntity sportEntity) {
        this.sportEntity = sportEntity;
    }

    public PaymentEntity getPaymentEntity() {
        return paymentEntity;
    }

    public void setPaymentEntity(PaymentEntity paymentEntity) {
        this.paymentEntity = paymentEntity;
    }

    public PlayGroundEntity getPlayGroundEntity() {
        return playGroundEntity;
    }

    public void setPlayGroundEntity(PlayGroundEntity playGroundEntity) {
        this.playGroundEntity = playGroundEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getNoOfPlayers() {
        return noOfPlayers;
    }

    public void setNoOfPlayers(Integer noOfPlayers) {
        this.noOfPlayers = noOfPlayers;
    }

    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId;
    }
}
