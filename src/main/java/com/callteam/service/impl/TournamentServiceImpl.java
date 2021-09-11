package com.callteam.service.impl;

import com.callteam.dto.*;
import com.callteam.entity.*;
import com.callteam.repository.*;
import com.callteam.reservation.SportPoolReservation;
import com.callteam.security.JwtTokenProvider;
import com.callteam.service.TournamentService;
import com.callteam.utill.AppConstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private SportPoolReservation sportPoolReservation;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UserSportRepository userSportRepository;

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

    @Override
    public ResponseEntity<?> getTournamentsByUserCity(String id) {

        try {

            UserEntity userEntity = userRepository.getByIdAndStatus(id,AppConstance.STATUS_ACTIVE);

            if(userEntity == null){
                return new ResponseEntity<>(new ResponseDto("Invalid User"),HttpStatus.INTERNAL_SERVER_ERROR);
            }

            List<TournamentPoolDto> tournamentPoolDtoList = new ArrayList<>();

            UserDetailsEntity userDetailsEntity = userDetailsRepository.findByUserEntityAndStatus(userEntity,AppConstance.STATUS_ACTIVE);

            if(userDetailsEntity.getCity() != null){

                List<SportEntity> sportEntityList = new ArrayList<>();

                List<UserSportEntity> userSportEntityList = userSportRepository.findAllByUserDetailsEntityAndStatus(userDetailsEntity,AppConstance.STATUS_ACTIVE);

                for (UserSportEntity userSportEntity : userSportEntityList) {
                    sportEntityList.add(userSportEntity.getSportEntity());
                }

                List<TournamentEntity> tournamentEntities = tournamentRepository.findAllBySportEntityInAndCityAndStatus(sportEntityList,userDetailsEntity.getCity(),AppConstance.STATUS_ACTIVE);

                for (TournamentEntity tournamentEntity : tournamentEntities) {

                    TournamentPoolDto tournamentPoolDto = new TournamentPoolDto();
                    tournamentPoolDto.setDate(setDate(tournamentEntity.getStartDate()));
                    tournamentPoolDto.setMonth(setMonth(tournamentEntity.getStartDate()));
                    tournamentPoolDto.setCity(tournamentEntity.getCity());
                    tournamentPoolDto.setGroundName(tournamentEntity.getPlayGroundEntity().getName());
                    tournamentPoolDto.setDescription(tournamentEntity.getDescription());
                    tournamentPoolDto.setName(tournamentEntity.getName());
                    tournamentPoolDto.setSport(tournamentEntity.getSportEntity().getName());
                    tournamentPoolDto.setNoOfTeam(tournamentEntity.getNoOfTeam());
                    tournamentPoolDto.setId(tournamentEntity.getId());
                    tournamentPoolDtoList.add(tournamentPoolDto);

                }

            }


        return new ResponseEntity<>(tournamentPoolDtoList,HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<?> getTournament(String id) {

        try {

            TournamentEntity tournamentEntity = tournamentRepository.getByIdAndStatus(id,AppConstance.STATUS_ACTIVE);


            TournamentDetailsDto tournamentDetailsDto = new TournamentDetailsDto();
            tournamentDetailsDto.setTournamentDto(setTournamentDto(tournamentEntity));
            tournamentDetailsDto.setSportDto(setSportDto(tournamentEntity.getSportEntity()));
            tournamentDetailsDto.setId(tournamentEntity.getId());
            tournamentDetailsDto.setPlayGroundDto(setPlayGround(tournamentEntity.getPlayGroundEntity()));

            return new ResponseEntity<>(tournamentDetailsDto,HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    private PlayGroundDto setPlayGround(PlayGroundEntity playGroundEntity) {
        PlayGroundDto playGroundDto = new PlayGroundDto();
        playGroundDto.setDistrict(playGroundEntity.getDistrict());
        playGroundDto.setAddress(playGroundEntity.getAddress());
        playGroundDto.setId(playGroundEntity.getId());
        playGroundDto.setCity(playGroundEntity.getCity());
        playGroundDto.setName(playGroundEntity.getName());
        playGroundDto.setImagePath(playGroundEntity.getImagePath());
        return playGroundDto;
    }

    private SportDto setSportDto(SportEntity sportEntity) {
        SportDto sportDto = new SportDto();
        sportDto.setDescription(sportEntity.getDescription());
        sportDto.setImagePath(sportEntity.getImagePath());
        sportDto.setId(sportDto.getId());
        sportDto.setName(sportEntity.getName());
        return sportDto;
    }

    private TournamentDto setTournamentDto(TournamentEntity tournamentEntity) {
        TournamentDto tournamentDto = new TournamentDto();
        tournamentDto.setStartTime(tournamentEntity.getStartTime());
        tournamentDto.setTournamentDescription(tournamentEntity.getDescription());
        tournamentDto.setStartDate(tournamentEntity.getStartDate());
        tournamentDto.setNoOfTeam(tournamentEntity.getNoOfTeam());
        tournamentDto.setNoOfPlayers(tournamentEntity.getNoOfPlayers());
        tournamentDto.setEndTime(tournamentEntity.getEndTime());
        tournamentDto.setDistrict(tournamentEntity.getDistrict());
        tournamentDto.setCity(tournamentEntity.getCity());
        tournamentDto.setStartTime(tournamentEntity.getStartTime());
        tournamentDto.setPoolId(tournamentEntity.getPoolId());
        tournamentDto.setTournamentName(tournamentEntity.getName());
        return tournamentDto;
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
        tournamentEntity.setPoolId(tournamentDto.getPoolId());

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

    private String setMonth(Date startDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
        return simpleDateFormat.format(startDate).toUpperCase();
    }
    private String setDate(Date startDate){
        Calendar date = new GregorianCalendar();
        date.setTime(startDate);
        return String.valueOf(date.get(Calendar.DAY_OF_MONTH));
    }

}
