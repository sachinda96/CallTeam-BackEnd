package com.callteam.controller;

import com.callteam.dto.TournamentDto;
import com.callteam.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/tournament")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;


    @PostMapping
    public ResponseEntity<?> save(@RequestBody TournamentDto tournamentDto){
        return tournamentService.save(tournamentDto);
    }

    @GetMapping("/tournamentsByUserCity/{id}")
    public ResponseEntity<?> getTournamentsByUserCity(@PathVariable String id){
        return tournamentService.getTournamentsByUserCity(id);
    }

    @GetMapping("/getTournament/{id}")
    public ResponseEntity<?> getTournament(@PathVariable String id){
        return tournamentService.getTournament(id);
    }

    @GetMapping("/getTournamentsCreateByUser/{id}")
    public ResponseEntity<?> getTournamentsCreateByUser(@PathVariable String id){
        return tournamentService.getTournamentsCreateByUser(id);
    }

    @DeleteMapping("/cancelTournament/{id}")
    public ResponseEntity<?> cancelTournament(@PathVariable String id){
        return tournamentService.cancelTouranament(id);
    }

}
