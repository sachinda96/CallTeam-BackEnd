package com.callteam.dto;

import java.util.Date;
import java.util.List;

public class PlayerDto {

    private String id;
    private String fullName;
    private String email;
    private String contactNo;
    private Date birthDay;
    private List<String> sportList;
    private Integer totalMatch;
    private Integer totalTournament;
    private String address;
    private String deistic;
    private String city;
    private String skills;
    private String aboutme;
    private Integer height;
    private Integer weight;
    private String imagePath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public List<String> getSportList() {
        return sportList;
    }

    public void setSportList(List<String> sportList) {
        this.sportList = sportList;
    }

    public Integer getTotalMatch() {
        return totalMatch;
    }

    public void setTotalMatch(Integer totalMatch) {
        this.totalMatch = totalMatch;
    }

    public Integer getTotalTournament() {
        return totalTournament;
    }

    public void setTotalTournament(Integer totalTournament) {
        this.totalTournament = totalTournament;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeistic() {
        return deistic;
    }

    public void setDeistic(String deistic) {
        this.deistic = deistic;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
