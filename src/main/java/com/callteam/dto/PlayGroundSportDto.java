package com.callteam.dto;

public class PlayGroundSportDto {

    private String id;
    private String sport;
    private String sportId;
    private Integer noOfTeams;
    private Double pricePerHour;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public Integer getNoOfTeams() {
        return noOfTeams;
    }

    public void setNoOfTeams(Integer noOfTeams) {
        this.noOfTeams = noOfTeams;
    }

    public Double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
}
