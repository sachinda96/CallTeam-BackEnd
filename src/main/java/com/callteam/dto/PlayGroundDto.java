package com.callteam.dto;

import javax.persistence.Column;
import java.util.List;

public class PlayGroundDto {

    private String id;
    private String name;
    private String address;
    private String city;
    private String district;
    private String longitude;
    private String latitude;
    private String openTime;
    private String closeTime;
    private List<String> closeDays;
    private List<String> sportList;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public List<String> getSportList() {
        return sportList;
    }

    public void setSportList(List<String> sportList) {
        this.sportList = sportList;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public List<String> getCloseDays() {
        return closeDays;
    }

    public void setCloseDays(List<String> closeDays) {
        this.closeDays = closeDays;
    }
}
