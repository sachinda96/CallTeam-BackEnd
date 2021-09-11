package com.callteam.controller;

import com.callteam.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllPlayers(){
        return playerService.getAllPlayers();
    }

    @GetMapping("/getPlayersById/{id}")
    public ResponseEntity<?> getPlayersById(@PathVariable String id){
        return playerService.getPlayersById(id);
    }
}
