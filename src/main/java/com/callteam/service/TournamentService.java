package com.callteam.service;

import com.callteam.dto.TournamentDto;
import org.springframework.http.ResponseEntity;

public interface TournamentService {

    public ResponseEntity<?> save(TournamentDto tournamentDto);

}
