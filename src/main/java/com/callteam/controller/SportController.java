package com.callteam.controller;

import com.callteam.dto.SportDto;
import com.callteam.service.SportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/sport")
public class SportController {

    @Autowired
    private SportService sportService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestParam(name = "file",required = false) MultipartFile file,@RequestParam(name = "sport",required = true) String sportDto){
        return sportService.save(file, sportDto);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody SportDto sportDto) throws JsonProcessingException {

        String sport = new ObjectMapper().writeValueAsString(sportDto);
        return sportService.save(null, sport);
    }
}
