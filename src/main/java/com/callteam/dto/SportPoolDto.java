package com.callteam.dto;

public class SportPoolDto {

    private String id;
    private String poolId;
    private SportDto sportDto;
    private PlayGroundDto playGroundDto;
    private PoolDto poolDto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId;
    }

    public SportDto getSportDto() {
        return sportDto;
    }

    public void setSportDto(SportDto sportDto) {
        this.sportDto = sportDto;
    }

    public PlayGroundDto getPlayGroundDto() {
        return playGroundDto;
    }

    public void setPlayGroundDto(PlayGroundDto playGroundDto) {
        this.playGroundDto = playGroundDto;
    }

    public PoolDto getPoolDto() {
        return poolDto;
    }

    public void setPoolDto(PoolDto poolDto) {
        this.poolDto = poolDto;
    }

}
