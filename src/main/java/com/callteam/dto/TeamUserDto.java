package com.callteam.dto;

public class TeamUserDto {

    private String id;
    private String userId;
    private String Name;
    private String teamId;
    private String poolId;
    private String imagePath;
    private Integer index;

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return Name;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getPoolId() {
        return poolId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Integer getIndex() {
        return index;
    }
}
