package com.callteam.service.impl;
import com.callteam.dto.PoolDto;
import com.callteam.dto.ResponseDto;
import com.callteam.dto.UserDetailsDto;
import com.callteam.dto.UserPoolDto;
import com.callteam.reservation.SportPoolReservation;
import com.callteam.service.SportPoolService;
import com.callteam.utill.AppConstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SportPoolServiceImpl implements SportPoolService {

    @Autowired
    private SportPoolReservation sportPoolReservation;

    @Override
    public ResponseEntity<?> createPool(UserPoolDto userPoolDto) {

        try {

            String id = sportPoolReservation.newSportPool(userPoolDto.getUserDetailsDto(),userPoolDto.getPoolDto(), AppConstance.MATCH_NOT);

            if(id == null){
                return new ResponseEntity<>(new ResponseDto("Failed to create pool"),HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>(new ResponseDto(id), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<?> updatePool(String poolId, String teamId, Integer index, UserDetailsDto userDetailsDto) {

        try {

            if(sportPoolReservation.joinPool(poolId,teamId,index,userDetailsDto)){
                return new ResponseEntity<>(new ResponseDto("Success"),HttpStatus.OK);
            }

            return new ResponseEntity<>(new ResponseDto("Failed"),HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<?> dropTeam(String poolId, String teamId, Integer index, String userId) {
        try {

            if(sportPoolReservation.dropTeam(poolId,teamId,index,userId)){
                return new ResponseEntity<>(new ResponseDto("Success"),HttpStatus.OK);
            }

            return new ResponseEntity<>(new ResponseDto("Failed"),HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<?> getPoolDetails(String id) {
        try {

            return new ResponseEntity<>(sportPoolReservation.getPoolDetails(id),HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
