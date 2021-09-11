package com.callteam.service;

import org.springframework.http.ResponseEntity;

public interface PlayerService {

    public ResponseEntity<?> getAllPlayers();

    public ResponseEntity<?> getPlayersById(String id);
}
