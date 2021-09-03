package com.callteam.controller;

import com.callteam.dto.PlayGroundDto;
import com.callteam.service.PlayGroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/playground")
public class PlayGroundController {

    @Autowired
    private PlayGroundService playGroundService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody PlayGroundDto playGroundDto){
        return playGroundService.save(playGroundDto);
    }
}
