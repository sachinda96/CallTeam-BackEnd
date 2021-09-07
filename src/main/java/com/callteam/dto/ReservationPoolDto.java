package com.callteam.dto;

import java.util.List;

public class ReservationPoolDto {

    private String poolId;
    private List<TeamDto> teamDtoList;

    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId;
    }

    public List<TeamDto> getTeamDtoList() {
        return teamDtoList;
    }

    public void setTeamDtoList(List<TeamDto> teamDtoList) {
        this.teamDtoList = teamDtoList;
    }
}
