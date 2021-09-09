package com.callteam.service;

import com.callteam.dto.SportPoolReservationDto;
import com.callteam.dto.UserDetailsDto;
import com.callteam.dto.UserPoolDto;
import org.springframework.http.ResponseEntity;

public interface SportPoolService {

    public ResponseEntity<?> createPool(UserPoolDto userPoolDto);

    public ResponseEntity<?> updatePool(String poolId, String teamId, Integer index, UserDetailsDto userDetailsDto);

    public ResponseEntity<?> dropTeam(String poolId, String teamId, Integer index,String userId);

    public ResponseEntity<?> getPoolDetails(String id);

    public ResponseEntity<?> saveSportPool(SportPoolReservationDto sportPoolReservationDto);

    public ResponseEntity<?> getAllSportPoolByUser(String userId);

    public ResponseEntity<?> getSportPool(String id);
}
