package com.callteam.service;

import com.callteam.dto.UserDto;
import org.springframework.http.ResponseEntity;

/**
 * @author sachinda
 */
public interface UserService {

    public ResponseEntity<?> addUser(UserDto userDto);
}
