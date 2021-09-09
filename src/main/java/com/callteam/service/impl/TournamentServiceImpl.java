package com.callteam.service.impl;

import com.callteam.dto.PaymentDto;
import com.callteam.dto.ResponseDto;
import com.callteam.dto.TournamentDto;
import com.callteam.entity.PaymentEntity;
import com.callteam.entity.PlayGroundEntity;
import com.callteam.entity.SportEntity;
import com.callteam.entity.TournamentEntity;
import com.callteam.repository.*;
import com.callteam.security.JwtTokenProvider;
import com.callteam.service.TournamentService;
import com.callteam.utill.AppConstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TournamentServiceImpl implements TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private TournamentDetailsRepository tournamentDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SportRepository sportRepository;

    @Autowired
    private PlayGroundRepository playGroundRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public ResponseEntity<?> save(TournamentDto tournamentDto) {

        try {

            SportEntity sportEntity = sportRepository.getByIdAndStatus(tournamentDto.getSportId(), AppConstance.STATUS_ACTIVE);

            if(sportEntity == null){
                return new ResponseEntity<>(new ResponseDto("Invalid Sport"), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            PlayGroundEntity playGroundEntity = playGroundRepository.getByIdAndStatus(tournamentDto.getGroundId(),AppConstance.STATUS_ACTIVE);

            if(playGroundEntity == null){
                return new ResponseEntity<>(new ResponseDto("Invalid Play Grounds"), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            PaymentEntity paymentEntity = paymentRepository.save(setPayment(tournamentDto.getPaymentDto()));


            tournamentRepository.save(setTournamentEntity(tournamentDto,paymentEntity,playGroundEntity,sportEntity));

            return new ResponseEntity<>(new ResponseDto("Successfully Saved"),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private TournamentEntity setTournamentEntity(TournamentDto tournamentDto,PaymentEntity paymentEntity,PlayGroundEntity playGroundEntity,SportEntity sportEntity) {
        TournamentEntity tournamentEntity = new TournamentEntity();
        tournamentEntity.setPaymentEntity(paymentEntity);
        tournamentEntity.setNoOfTeam(tournamentDto.getNoOfTeam());
        tournamentEntity.setStartTime(tournamentDto.getStartTime());
        tournamentEntity.setStartDate(tournamentDto.getStartDate());
        tournamentEntity.setDescription(tournamentDto.getTournamentDescription());
        tournamentEntity.setCity(tournamentDto.getCity());
        tournamentEntity.setStatus(AppConstance.STATUS_ACTIVE);
        tournamentEntity.setSportEntity(sportEntity);
        tournamentEntity.setId(UUID.randomUUID().toString());
        tournamentEntity.setCreateBy(jwtTokenProvider.getUser());
        tournamentEntity.setCreateDate(new Date());
        tournamentEntity.setNoOfPlayers(tournamentDto.getNoOfPlayers());
        tournamentEntity.setPlayGroundEntity(playGroundEntity);
        tournamentEntity.setName(tournamentDto.getTournamentName());
        tournamentEntity.setDistrict(tournamentDto.getDistrict());
        tournamentEntity.setEndTime(tournamentDto.getEndTime());

        return tournamentEntity;
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
