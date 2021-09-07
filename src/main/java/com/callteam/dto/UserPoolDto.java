package com.callteam.dto;

public class UserPoolDto {
    private UserDetailsDto userDetailsDto;
    private PoolDto poolDto;

    public UserDetailsDto getUserDetailsDto() {
        return userDetailsDto;
    }

    public void setUserDetailsDto(UserDetailsDto userDetailsDto) {
        this.userDetailsDto = userDetailsDto;
    }

    public PoolDto getPoolDto() {
        return poolDto;
    }

    public void setPoolDto(PoolDto poolDto) {
        this.poolDto = poolDto;
    }
}
