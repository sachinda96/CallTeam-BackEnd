package com.callteam.dto;

import java.util.Date;

public class ProgressMatchDto {

    private String id;
    private String name;
    private Date date;
    private String teamOneName;
    private String teamOneId;
    private String teamTwoName;
    private String teamTwoId;
    private String teamOneCount;
    private String teamTwoCount;
    private String groundName;
    private String groundCity;
    private String month;
    private String day;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTeamOneName() {
        return teamOneName;
    }

    public void setTeamOneName(String teamOneName) {
        this.teamOneName = teamOneName;
    }

    public String getTeamTwoName() {
        return teamTwoName;
    }

    public void setTeamTwoName(String teamTwoName) {
        this.teamTwoName = teamTwoName;
    }

    public String getTeamOneCount() {
        return teamOneCount;
    }

    public void setTeamOneCount(String teamOneCount) {
        this.teamOneCount = teamOneCount;
    }

    public String getTeamTwoCount() {
        return teamTwoCount;
    }

    public void setTeamTwoCount(String teamTwoCount) {
        this.teamTwoCount = teamTwoCount;
    }

    public String getGroundName() {
        return groundName;
    }

    public void setGroundName(String groundName) {
        this.groundName = groundName;
    }

    public String getGroundCity() {
        return groundCity;
    }

    public void setGroundCity(String groundCity) {
        this.groundCity = groundCity;
    }

    public String getTeamOneId() {
        return teamOneId;
    }

    public void setTeamOneId(String teamOneId) {
        this.teamOneId = teamOneId;
    }

    public String getTeamTwoId() {
        return teamTwoId;
    }

    public void setTeamTwoId(String teamTwoId) {
        this.teamTwoId = teamTwoId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
