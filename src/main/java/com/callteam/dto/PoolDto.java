package com.callteam.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PoolDto {

    private String id;
    private String name;
    private String description;
    private String district;
    private String city;
    private Date date;
    private String stringDate;
    private String startTime;
    private String endTime;
    private SportDto sport;
    private Integer noOfPlayers;
    private Integer noOfTeam = 2;
    private List<TeamDto> teamDtoList = new ArrayList<>();



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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStringDate() {
        return stringDate;
    }

    public void setStringDate(String stringDate) {
        this.stringDate = stringDate;
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

    public SportDto getSport() {
        return sport;
    }

    public void setSport(SportDto sport) {
        this.sport = sport;
    }

    public Integer getNoOfPlayers() {
        return noOfPlayers;
    }

    public void setNoOfPlayers(Integer noOfPlayers) {
        this.noOfPlayers = noOfPlayers;
    }

    public List<TeamDto> getTeamDtoList() {
        return teamDtoList;
    }

    public void setTeamDtoList(List<TeamDto> teamDtoList) {
        this.teamDtoList = teamDtoList;
    }

    public Integer getNoOfTeam() {
        return noOfTeam;
    }

    public void setNoOfTeam(Integer noOfTeam) {
        this.noOfTeam = noOfTeam;
    }
}
