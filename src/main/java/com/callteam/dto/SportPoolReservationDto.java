package com.callteam.dto;

public class SportPoolReservationDto {

    private String userId;
    private String sportId;
    private PoolDto poolDto;
    private String poolId;
    private PaymentDto paymentDto;
    private String groundId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PoolDto getPoolDto() {
        return poolDto;
    }

    public void setPoolDto(PoolDto poolDto) {
        this.poolDto = poolDto;
    }

    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId;
    }

    public PaymentDto getPaymentDto() {
        return paymentDto;
    }

    public void setPaymentDto(PaymentDto paymentDto) {
        this.paymentDto = paymentDto;
    }

    public String getGroundId() {
        return groundId;
    }

    public void setGroundId(String groundId) {
        this.groundId = groundId;
    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }
}
