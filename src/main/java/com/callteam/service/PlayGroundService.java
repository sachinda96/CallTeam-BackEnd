package com.callteam.service;

import com.callteam.dto.PlayGroundDto;
import org.springframework.http.ResponseEntity;

public interface PlayGroundService {

    public ResponseEntity<?> save(PlayGroundDto playGroundDto);
}
