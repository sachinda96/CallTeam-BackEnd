package com.callteam.dto;

import java.util.List;

public class TeamDto {

    public String id;
    private String teamName;
    private String poolId;
    private List<TeamUserDto> teamUserDtoList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId;
    }

    public List<TeamUserDto> getTeamUserDtoList() {
        return teamUserDtoList;
    }

    public void setTeamUserDtoList(List<TeamUserDto> teamUserDtoList) {
        this.teamUserDtoList = teamUserDtoList;
    }
}
