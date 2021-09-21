package com.callteam.service.impl;
import com.callteam.dto.*;
import com.callteam.entity.*;
import com.callteam.repository.*;
import com.callteam.reservation.SportPoolReservation;
import com.callteam.security.JwtTokenProvider;
import com.callteam.service.SportPoolService;
import com.callteam.utill.AppConstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UserSportRepository userSportRepository;

    /**
     *
     * @param userPoolDto
     * @return Response Entity
     * Create sport pool with user pool data and reserve new pool using reservation class
     */
    @Override
    public ResponseEntity<?> createPool(UserPoolDto userPoolDto) {

        try {

            String id = sportPoolReservation.newSportPool(userPoolDto.getUserDetailsDto(),userPoolDto.getPoolDto(), userPoolDto.getPoolDto().getNoOfTeam());

            if(id == null){
                return new ResponseEntity<>(new ResponseDto("Failed to create pool"),HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>(new ResponseDto(id), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     *
     * @param poolId
     * @param teamId
     * @param index
     * @param userDetailsDto
     * @return Response Entity
     * Update the created pool with user action
     */
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

    /**
     *
     * @param poolId
     * @param teamId
     * @param index
     * @param userId
     * @return Response entity with status
     * Drop from the involved pool
     */
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

    /**
     *
     * @param id
     * @return ResponseEntity with pool details
     * Return Inprogress pool details regarding the poolId
     */
    @Override
    public ResponseEntity<?> getPoolDetails(String id) {
        ResponseEntity<?> result;
        try {

            result = new ResponseEntity<>(sportPoolReservation.getPoolDetails(id), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            result = new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *
     * @param sportPoolReservationDto
     * @return Response of save status
     * Save all match pool details to the database
     */
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

            SportPoolEntity sportPoolEntity = sportPoolRepository.save(setSportPool(sportPoolReservationDto,sportEntity,playGroundEntity,paymentEntity,userEntity));

            sportPoolDetailsRepository.save(setSportPoolDetailsEntity(sportPoolEntity,userEntity));

            return new ResponseEntity<>(new ResponseDto("Saved Successfully"),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     *
     * @param userId
     * @return Response all sport pool
     * Get all sport pool regarding the user city and user interested sport
     */
    @Override
    public ResponseEntity<?> getAllSportPoolByUser(String userId) {

        try {

            UserEntity userEntity = userRepository.getByIdAndStatus(userId,AppConstance.STATUS_ACTIVE);

            if(userEntity == null){
                return new ResponseEntity<>(new ResponseDto("Invalid User"),HttpStatus.INTERNAL_SERVER_ERROR);
            }

            List<SportEntity> sportEntityList = new ArrayList<>();

            UserDetailsEntity userDetailsEntity = userDetailsRepository.findByUserEntityAndStatus(userEntity,AppConstance.STATUS_ACTIVE);

            if(userDetailsEntity != null){
                List<UserSportEntity> userSportEntityList = userSportRepository.findAllByUserDetailsEntityAndStatus(userDetailsEntity,AppConstance.STATUS_ACTIVE);

                for (UserSportEntity userSportEntity:userSportEntityList) {
                    sportEntityList.add(userSportEntity.getSportEntity());
                }

            }

            List<SportPoolEntity> sportPoolEntities = sportPoolRepository.findAllBySportEntityInAndCityAndStatus(sportEntityList,userDetailsEntity.getCity(),AppConstance.STATUS_ACTIVE);

            List<ProgressMatchDto> progressMatchDtoList = new ArrayList<>();

            for (SportPoolEntity sportPoolEntity : sportPoolEntities) {

                ProgressMatchDto progressMatchDto = new ProgressMatchDto();
                progressMatchDto.setDate(sportPoolEntity.getStartDate());
                progressMatchDto.setGroundCity(sportPoolEntity.getPlayGroundEntity().getCity());
                progressMatchDto.setGroundName(sportPoolEntity.getPlayGroundEntity().getName());
                progressMatchDto.setId(sportPoolEntity.getId());
                progressMatchDto.setName(sportPoolEntity.getName());

                ReservationPoolDto reservationPoolDto = sportPoolReservation.getPoolDetails(sportPoolEntity.getPoolId());

                TeamDto teamOneDto = reservationPoolDto.getTeamDtoList().get(0);
                TeamDto teamTwoDto = reservationPoolDto.getTeamDtoList().get(1);

                progressMatchDto.setTeamOneName(teamOneDto.getTeamName());
                progressMatchDto.setTeamTwoName(teamTwoDto.getTeamName());
                progressMatchDto.setTeamOneId(teamOneDto.id);
                progressMatchDto.setTeamTwoId(teamTwoDto.id);
                progressMatchDto.setTeamTwoCount(setTeamCount(teamTwoDto.getTeamUserDtoList()));
                progressMatchDto.setTeamOneCount(setTeamCount(teamOneDto.getTeamUserDtoList()));
                progressMatchDto.setMonth(setMonth(sportPoolEntity.getStartDate()));
                progressMatchDto.setDay(setDate(sportPoolEntity.getStartDate()));
                progressMatchDtoList.add(progressMatchDto);
            }

            return new ResponseEntity<>(progressMatchDtoList,HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param userId
     * @return response with all pool deatils
     * Get all pool details from the database using Create by user
     */
    @Override
    public ResponseEntity<?> getAllSportPoolCreateByUser(String userId) {
        try {

            UserEntity userEntity = userRepository.getByIdAndStatus(userId,AppConstance.STATUS_ACTIVE);

            if(userEntity == null){
                return new ResponseEntity<>(new ResponseDto("Invalid User"),HttpStatus.INTERNAL_SERVER_ERROR);
            }


            List<SportPoolEntity> sportPoolEntities = sportPoolRepository.findAllByCreateByAndStatus(userEntity.getId(),AppConstance.STATUS_ACTIVE);

            List<ProgressMatchDto> progressMatchDtoList = new ArrayList<>();

            for (SportPoolEntity sportPoolEntity : sportPoolEntities) {

                ProgressMatchDto progressMatchDto = new ProgressMatchDto();
                progressMatchDto.setDate(sportPoolEntity.getStartDate());
                progressMatchDto.setGroundCity(sportPoolEntity.getPlayGroundEntity().getCity());
                progressMatchDto.setGroundName(sportPoolEntity.getPlayGroundEntity().getName());
                progressMatchDto.setId(sportPoolEntity.getId());
                progressMatchDto.setName(sportPoolEntity.getName());

                try {
                    ReservationPoolDto reservationPoolDto = sportPoolReservation.getPoolDetails(sportPoolEntity.getPoolId());
                    TeamDto teamOneDto = reservationPoolDto.getTeamDtoList().get(0);
                    TeamDto teamTwoDto = reservationPoolDto.getTeamDtoList().get(1);
                    progressMatchDto.setTeamOneName(teamOneDto.getTeamName());
                    progressMatchDto.setTeamTwoName(teamTwoDto.getTeamName());
                    progressMatchDto.setTeamOneId(teamOneDto.id);
                    progressMatchDto.setTeamTwoId(teamTwoDto.id);

                    progressMatchDto.setTeamTwoCount(setTeamCount(teamTwoDto.getTeamUserDtoList()));
                    progressMatchDto.setTeamOneCount(setTeamCount(teamOneDto.getTeamUserDtoList()));
                }catch (Exception e){}

                progressMatchDto.setMonth(setMonth(sportPoolEntity.getStartDate()));
                progressMatchDto.setDay(setDate(sportPoolEntity.getStartDate()));
                progressMatchDtoList.add(progressMatchDto);
            }

            return new ResponseEntity<>(progressMatchDtoList,HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param teamUserDtoList
     * @return
     */
    private String setTeamCount(List<TeamUserDto> teamUserDtoList) {

        Integer count =0 ;

        for (TeamUserDto teamUserDto : teamUserDtoList) {
            if(teamUserDto.getUserId() != null){
                count = count + 1;
            }
        }

        return count.toString();
    }

    /**
     *
     * @param id
     * @return Response with pool details
     * Get pool details from the database using pool id and generate the response
     */
    @Override
    public ResponseEntity<?> getSportPool(String id) {

        try {

            SportPoolEntity sportPoolEntity = sportPoolRepository.getByIdAndStatus(id,AppConstance.STATUS_ACTIVE);

            if(sportPoolEntity == null){
                return new ResponseEntity<>(new ResponseDto("Invalid Pool"),HttpStatus.INTERNAL_SERVER_ERROR);
            }

            SportPoolDto sportPoolDto = new SportPoolDto();
            sportPoolDto.setId(sportPoolEntity.getId());
            sportPoolDto.setSportDto(setSportPoolDto(sportPoolEntity.getSportEntity()));
            sportPoolDto.setPlayGroundDto(setPlayGroundDto(sportPoolEntity.getPlayGroundEntity()));
            sportPoolDto.setPoolId(sportPoolEntity.getPoolId());
            sportPoolDto.setPoolDto(setPoolDto(sportPoolEntity));

            return new ResponseEntity<>(sportPoolDto,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param id
     * @return Response status of cancel status
     * Change status in sport poll as a CANCEL
     */
    @Override
    public ResponseEntity<?> cancelSportPool(String id) {

        try {

            SportPoolEntity sportPoolEntity = sportPoolRepository.getByIdAndStatus(id,AppConstance.STATUS_ACTIVE);

            if(sportPoolEntity == null){
                return new ResponseEntity<>(new ResponseDto("Invalid Sport Details"),HttpStatus.INTERNAL_SERVER_ERROR);
            }

            sportPoolEntity.setStatus(AppConstance.STATUS_CANCEL);
            sportPoolRepository.save(sportPoolEntity);

            sportPoolReservation.removePool(sportPoolEntity.getPoolId());

            return new ResponseEntity<>(new ResponseDto("Match Pool Cancelled"),HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param sportPoolEntity
     * @return poolDeto
     */
    private PoolDto setPoolDto(SportPoolEntity sportPoolEntity) {
        PoolDto poolDto = new PoolDto();
        poolDto.setCity(sportPoolEntity.getCity());
        poolDto.setStartTime(sportPoolEntity.getStartTime());
        poolDto.setNoOfPlayers(sportPoolEntity.getNoOfPlayers());
        poolDto.setEndTime(sportPoolEntity.getEndTime());
        poolDto.setDistrict(sportPoolEntity.getDistrict());
        poolDto.setDescription(sportPoolEntity.getDescription());
        poolDto.setDate(sportPoolEntity.getStartDate());
        poolDto.setCity(sportPoolEntity.getCity());
        poolDto.setName(sportPoolEntity.getName());
        poolDto.setId(sportPoolEntity.getId());
        return poolDto;
    }

    /**
     *
     * @param playGroundEntity
     * @return PlayGroundDto
     */
    private PlayGroundDto setPlayGroundDto(PlayGroundEntity playGroundEntity) {
        PlayGroundDto playGroundDto = new PlayGroundDto();
        playGroundDto.setName(playGroundEntity.getName());
        playGroundDto.setImagePath(playGroundEntity.getImagePath());
        playGroundDto.setAddress(playGroundEntity.getAddress());
        playGroundDto.setCity(playGroundEntity.getCity());
        playGroundDto.setId(playGroundEntity.getId());
        playGroundDto.setDistrict(playGroundEntity.getDistrict());
        return playGroundDto;
    }

    /**
     *
     * @param sportEntity
     * @return SportDto
     */
    private SportDto setSportPoolDto(SportEntity sportEntity) {
        SportDto sportDto = new SportDto();
        sportDto.setImagePath(sportEntity.getImagePath());
        sportDto.setName(sportEntity.getName());
        sportDto.setId(sportEntity.getId());
        sportDto.setCategoryId(sportEntity.getCategoryEntity().getId());
        sportDto.setDescription(sportEntity.getDescription());
        sportDto.setNumberOfPlayers(sportEntity.getNumberOfPlayers());
        sportDto.setAgeMin(sportEntity.getAgeMin());
        sportDto.setAgeMax(sportEntity.getAgeMax());
        return sportDto;
    }

    /**
     *
     * @param startDate
     * @return String
     */
    private String setMonth(Date startDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
        return simpleDateFormat.format(startDate).toUpperCase();
    }

    /**
     *
     * @param startDate
     * @return
     */
    private String setDate(Date startDate){
        Calendar date = new GregorianCalendar();
        date.setTime(startDate);
        return String.valueOf(date.get(Calendar.DAY_OF_MONTH));
    }

    /**
     *
     * @param sportPoolEntity
     * @param userEntity
     * @return SportPoolDetailsEntity
     */
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

    /**
     *
     * @param sportPoolReservationDto
     * @param sportEntity
     * @param playGroundEntity
     * @param paymentEntity
     * @param userEntity
     * @return SportPoolEntity
     */
    private SportPoolEntity setSportPool(SportPoolReservationDto sportPoolReservationDto, SportEntity sportEntity, PlayGroundEntity playGroundEntity, PaymentEntity paymentEntity,UserEntity userEntity) {
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
        sportPoolEntity.setCreateBy(userEntity.getId());
        sportPoolEntity.setPlayGroundEntity(playGroundEntity);
        sportPoolEntity.setPoolId(sportPoolReservationDto.getPoolId());
        return sportPoolEntity;
    }

    /**
     *
     * @param paymentDto
     * @return
     */
    private PaymentEntity setPayment(PaymentDto paymentDto) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setCreateBy(jwtTokenProvider.getUser());
        paymentEntity.setId(UUID.randomUUID().toString());
        paymentEntity.setCreateDate(new Date());
        paymentEntity.setStatus(AppConstance.STATUS_ACTIVE);
        return paymentEntity;

    }
}
