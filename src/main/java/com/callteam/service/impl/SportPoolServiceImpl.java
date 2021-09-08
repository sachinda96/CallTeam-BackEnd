package com.callteam.service.impl;
import com.callteam.dto.*;
import com.callteam.entity.*;
import com.callteam.repository.*;
import com.callteam.reservation.SportPoolReservation;
import com.callteam.security.JwtTokenProvider;
import com.callteam.service.SportPoolService;
import com.callteam.service.SportService;
import com.callteam.utill.AppConstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class SportPoolServiceImpl implements SportPoolService {

    @Autowired
    private SportPoolReservation sportPoolReservation;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SportRepository sportRepository;

    @Autowired
    private PlayGroundRepository playGroundRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private SportPoolRepository sportPoolRepository;

    @Autowired
    private SportPoolDetailsRepository sportPoolDetailsRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

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

    @Override
    public ResponseEntity<?> saveSportPool(SportPoolReservationDto sportPoolReservationDto) {

        try {

            UserEntity userEntity = userRepository.getByIdAndStatus(sportPoolReservationDto.getUserId(),AppConstance.STATUS_ACTIVE);

            if(userEntity == null){
                return new ResponseEntity<>(new ResponseDto("Invalid User"),HttpStatus.INTERNAL_SERVER_ERROR);
            }

            SportEntity sportEntity = sportRepository.getByIdAndStatus(sportPoolReservationDto.getSportId(),AppConstance.STATUS_ACTIVE);

            if(sportEntity == null){
                return new ResponseEntity<>(new ResponseDto("Invalid Sport"),HttpStatus.INTERNAL_SERVER_ERROR);
            }

            PlayGroundEntity playGroundEntity = playGroundRepository.getByIdAndStatus(sportPoolReservationDto.getGroundId(),AppConstance.STATUS_ACTIVE);

            if(playGroundEntity == null){
                return new ResponseEntity<>(new ResponseDto("Invalid PlayGround"),HttpStatus.INTERNAL_SERVER_ERROR);
            }

            PaymentEntity paymentEntity = paymentRepository.save(setPayment(sportPoolReservationDto.getPaymentDto()));

            SportPoolEntity sportPoolEntity = sportPoolRepository.save(setSportPool(sportPoolReservationDto,sportEntity,playGroundEntity,paymentEntity));

            sportPoolDetailsRepository.save(setSportPoolDetailsEntity(sportPoolEntity,userEntity));

            return new ResponseEntity<>(new ResponseDto("Saved Successfully"),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<?> getAllSportPoolByUser(String userId) {

        try {


            return null;


        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private SportPoolDetailsEntity setSportPoolDetailsEntity(SportPoolEntity sportPoolEntity, UserEntity userEntity) {
        SportPoolDetailsEntity sportPoolDetailsEntity = new SportPoolDetailsEntity();
        sportPoolDetailsEntity.setSportPoolEntity(sportPoolEntity);
        sportPoolDetailsEntity.setUserEntity(userEntity);
        sportPoolDetailsEntity.setCreateBy(jwtTokenProvider.getUser());
        sportPoolDetailsEntity.setId(UUID.randomUUID().toString());
        sportPoolDetailsEntity.setStatus(AppConstance.STATUS_ACTIVE);
        sportPoolDetailsEntity.setCreateDate(new Date());
        return sportPoolDetailsEntity;

    }

    private SportPoolEntity setSportPool(SportPoolReservationDto sportPoolReservationDto, SportEntity sportEntity, PlayGroundEntity playGroundEntity, PaymentEntity paymentEntity) {
        SportPoolEntity sportPoolEntity = new SportPoolEntity();
        sportPoolEntity.setSportEntity(sportEntity);
        sportPoolEntity.setPaymentEntity(paymentEntity);
        sportPoolEntity.setStartTime(sportPoolReservationDto.getPoolDto().getStartTime());
        sportPoolEntity.setNoOfPlayers(sportPoolReservationDto.getPoolDto().getNoOfPlayers());
        sportPoolEntity.setName(sportPoolReservationDto.getPoolDto().getName());
        sportPoolEntity.setCity(sportPoolReservationDto.getPoolDto().getCity());
        sportPoolEntity.setStatus(AppConstance.STATUS_ACTIVE);
        sportPoolEntity.setStartDate(sportPoolReservationDto.getPoolDto().getDate());
        sportPoolEntity.setId(UUID.randomUUID().toString());
        sportPoolEntity.setCreateDate(new Date());
        sportPoolEntity.setCreateBy(jwtTokenProvider.getUser());
        sportPoolEntity.setPlayGroundEntity(playGroundEntity);
        sportPoolEntity.setPoolId(sportPoolReservationDto.getPoolId());
        return sportPoolEntity;
    }

    private PaymentEntity setPayment(PaymentDto paymentDto) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setCreateBy(jwtTokenProvider.getUser());
        paymentEntity.setId(UUID.randomUUID().toString());
        //paymentEntity.setType(paymentDto.getType());
        //paymentEntity.setMethod(paymentDto.getMethod());
        paymentEntity.setCreateDate(new Date());
        //paymentEntity.setAmount(paymentDto.getAmount());
        paymentEntity.setStatus(AppConstance.STATUS_ACTIVE);
        return paymentEntity;

    }
}
