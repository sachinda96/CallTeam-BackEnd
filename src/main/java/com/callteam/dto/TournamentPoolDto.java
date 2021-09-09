package com.callteam.dto;

public class TournamentPoolDto {

    private String id;
    private String name;
    private String description;
    private Integer noOfTeam;
    private String sport;
    private String Month;
    private String date;
    private String GroundName;
    private String city;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getNoOfTeam() {
        return noOfTeam;
    }

    public void setNoOfTeam(Integer noOfTeam) {
        this.noOfTeam = noOfTeam;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGroundName() {
        return GroundName;
    }

    public void setGroundName(String groundName) {
        GroundName = groundName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
