package com.callteam.service.impl;

import com.callteam.entity.LoginEntity;
import com.callteam.repository.LoginRepository;
import com.callteam.utill.AppConstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private LoginRepository loginRepository;

    /**
     *
     * @param email
     * @return User Details
     * @throws UsernameNotFoundException
     * This method working in all authentication level get requested user credentials data ana validate
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        try {
            ArrayList<GrantedAuthority> roles=new ArrayList<GrantedAuthority>();
            LoginEntity loginEntity =loginRepository.findByEmailAndStatus(email, AppConstance.STATUS_ACTIVE);
            roles.add(new SimpleGrantedAuthority("PLAYER"));
            return new User(loginEntity.getEmail(),loginEntity.getPassword(), roles);
        } catch (Exception e) {
            throw new UsernameNotFoundException("email not fount: " + email );
        }
    }
}
