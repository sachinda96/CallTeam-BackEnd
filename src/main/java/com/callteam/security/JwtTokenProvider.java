package com.callteam.security;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    public String getUser(){
        return "Admin";
    }
}
