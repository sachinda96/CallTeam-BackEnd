package com.callteam.controller;

import com.callteam.dto.SportPoolReservationDto;
import com.callteam.dto.UserDetailsDto;
import com.callteam.dto.UserPoolDto;
import com.callteam.service.SportPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/pool")
public class SportPoolController {

    @Autowired
    private SportPoolService sportPoolService;

    @PostMapping("/create")
    public ResponseEntity<?> createPool(@RequestBody UserPoolDto userPoolDto){
        return sportPoolService.createPool(userPoolDto);
    }

    @PostMapping("/update/{poolId}/{teamId}/{index}")
    public ResponseEntity<?> updatePool(@PathVariable String poolId,@PathVariable String teamId,@PathVariable Integer index, @RequestBody UserDetailsDto userDetailsDto){
        return sportPoolService.updatePool(poolId, teamId, index, userDetailsDto);
    }

    @GetMapping("/drop/{poolId}/{teamId}/{index}/{userId}")
    public ResponseEntity<?> dropTeam(@PathVariable String poolId,@PathVariable String teamId, @PathVariable Integer index,@PathVariable String userId){
        return sportPoolService.dropTeam(poolId, teamId, index, userId);
    }

    @GetMapping("/getPoolDetails/{id}")
    public ResponseEntity<?> getPoolDetails(@PathVariable String id){
        return sportPoolService.getPoolDetails(id);
    }

    @PostMapping("/savepool")
    public ResponseEntity<?> saveSportPool(@RequestBody SportPoolReservationDto sportPoolReservationDto){
        return sportPoolService.saveSportPool(sportPoolReservationDto);
    }

    @GetMapping("/getAllSportPoolByUser/{userId}")
    public ResponseEntity<?> getAllSportPoolByUser(@PathVariable String userId){
        return sportPoolService.getAllSportPoolByUser(userId);
    }

    @GetMapping("/getSportPool/{id}")
    public ResponseEntity<?> getSportPool(@PathVariable String id){
        return sportPoolService.getSportPool(id);
    }
}
