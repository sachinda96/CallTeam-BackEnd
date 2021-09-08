package com.callteam.controller;

import com.callteam.service.PlayGroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/playground")
public class PlayGroundController {

    @Autowired
    private PlayGroundService playGroundService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestParam(required = false,name = "file") MultipartFile multipartFile , @RequestParam(name = "playGround") String playGround){
        return playGroundService.save(multipartFile,playGround);
    }

    @GetMapping
    public ResponseEntity<?> getAllGrounds(){
        return playGroundService.getAll();
    }

    @GetMapping("/getAllByCity/{city}")
    public ResponseEntity<?> getAllByCity(@PathVariable String city){
        return playGroundService.getAllByCity(city);
    }
}
