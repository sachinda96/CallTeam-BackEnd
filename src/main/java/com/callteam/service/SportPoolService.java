package com.callteam.service;

import com.callteam.dto.PoolDto;
import com.callteam.dto.UserDetailsDto;
import com.callteam.dto.UserPoolDto;
import org.springframework.http.ResponseEntity;

public interface SportPoolService {

    public ResponseEntity<?> createPool(UserPoolDto userPoolDto);

    public ResponseEntity<?> updatePool(String poolId, String teamId, Integer index, UserDetailsDto userDetailsDto);

    public ResponseEntity<?> dropTeam(String poolId, String teamId, Integer index,String userId);

    public ResponseEntity<?> getPoolDetails(String id);


}
