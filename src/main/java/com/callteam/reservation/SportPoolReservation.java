package com.callteam.reservation;

import com.callteam.dto.*;
import com.callteam.utill.AppConstance;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SportPoolReservation {

   private Map<String, PoolDto> sportPool = new HashMap<>();


   public String newSportPool(UserDetailsDto userDetailsDto,PoolDto poolDto,Integer noOfTeam) throws Exception{

       String id = UUID.randomUUID().toString();
       List<TeamDto> teamList = new ArrayList<>();

       for (int i= 1; i <= noOfTeam; i++){
           TeamDto teamDto = new TeamDto();
           teamDto.setPoolId(id);
           teamDto.setTeamName(AppConstance.TEAM_NAME+i);
           teamDto.setId(UUID.randomUUID().toString());

           List<TeamUserDto> teamUserDtoList = new ArrayList<>();

           for (int x= 1; x <= poolDto.getNoOfPlayers(); x++){
               TeamUserDto teamUserDto = new TeamUserDto();
               if(x == 1 && i == 1){
                   teamUserDto.setUserId(userDetailsDto.getUserId());
                   teamUserDto.setImagePath(userDetailsDto.getImagePath());
                   teamUserDto.setName(userDetailsDto.getFullName().split(" ",2)[0]);
               }else {
                   teamUserDto.setImagePath(AppConstance.TEMP_IMAGE);
               }

               teamUserDto.setPoolId(teamDto.getPoolId());
               teamUserDto.setId(UUID.randomUUID().toString());
               teamUserDto.setTeamId(teamDto.id);
               teamUserDto.setIndex(x);

               teamUserDtoList.add(teamUserDto);
           }

           teamDto.setTeamUserDtoList(teamUserDtoList);
           teamList.add(teamDto);
       }

       poolDto.setTeamDtoList(teamList);

       sportPool.put(id,poolDto);

       return id;
   }

   public boolean joinPool(String poolId, String teamId, Integer index, UserDetailsDto userDetailsDto)throws Exception{

       PoolDto poolDto = sportPool.get(poolId);

       if(poolDto == null){
           throw new Exception("Invalid pool");
       }

       this.validateUserOldJoin(poolId,poolDto,userDetailsDto);

       Optional<TeamDto> teamDto = poolDto.getTeamDtoList().stream()
               .filter(x -> x.id.equals(teamId))
               .findFirst();

       int i = poolDto.getTeamDtoList().indexOf(teamDto.get());
       if(teamDto.isPresent()){

           index = index-1;
           TeamUserDto teamUserDto = teamDto.get().getTeamUserDtoList().get(index);

           if(teamDto.get().getTeamUserDtoList().get(index).getUserId() == null){
               teamUserDto.setUserId(userDetailsDto.getUserId());
               teamUserDto.setImagePath(userDetailsDto.getImagePath());
               teamUserDto.setName(userDetailsDto.getFullName().split(" ",0).toString());
               //teamDto.get().getTeamUserDtoList().add(index,teamUserDto);

           }else {
               throw new Exception("Already user joined");
           }

       }else {
           return false;
       }

       //poolDto.getTeamDtoList().add(i,teamDto.get());
       sportPool.put(poolId,poolDto);

       return true;
   }

    public boolean validateUserOldJoin(String poolId,PoolDto poolDto, UserDetailsDto userDetailsDto) throws Exception {


       boolean isJoined = false;
       String teamId = null;
       Integer index = null;
        for (TeamDto teamDto : poolDto.getTeamDtoList()) {
            for (TeamUserDto teamUserDto : teamDto.getTeamUserDtoList()) {
                if(teamUserDto.getUserId() != null){
                    if(teamUserDto.getUserId().equalsIgnoreCase(userDetailsDto.getUserId())){
                        isJoined = true;
                        teamId = teamDto.id;
                        index = teamUserDto.getIndex();
                        break;
                    }
                }

            }

        }

        if(isJoined){
            dropTeam(poolId,teamId,index,userDetailsDto.getUserId());
        }

        return true;

   }

    public boolean dropTeam(String poolId, String teamId, Integer index,String userId) throws Exception{

       PoolDto poolDto = sportPool.get(poolId);

       if(poolDto == null){
           throw new Exception("Invalid pool");
       }

       Optional<TeamDto> teamDto = poolDto.getTeamDtoList().stream()
               .filter(x -> x.id.equals(teamId))
               .findFirst();

       int i = poolDto.getTeamDtoList().indexOf(teamDto.get());

       if(teamDto.isPresent()){
           index = index - 1;

           TeamUserDto teamUserDto = teamDto.get().getTeamUserDtoList().get(index);
           if(teamDto.get().getTeamUserDtoList().get(index).getUserId().equals(userId)){
               teamUserDto.setUserId(null);
               teamUserDto.setImagePath(AppConstance.TEMP_IMAGE);
               teamUserDto.setName(null);
           }

       }else {
           return false;
       }


       //poolDto.getTeamDtoList().add(i,teamDto.get());
       sportPool.put(poolId,poolDto);

       return true;

   }

   public boolean updateTeamName(String poolId,String teamId,String name) throws Exception{

       PoolDto poolDto = sportPool.get(poolId);

       if(poolDto == null){
           throw new Exception("Invalid pool");
       }

       Optional<TeamDto> teamDto = poolDto.getTeamDtoList().stream()
               .filter(x -> x.id.equals(teamId))
               .findFirst();

       int i = poolDto.getTeamDtoList().indexOf(teamDto);

       if(teamDto.isPresent()){
           teamDto.get().setTeamName(name);
           poolDto.getTeamDtoList().add(i,teamDto.get());

       }else {
           return false;
       }

       sportPool.put(poolId,poolDto);
       return true;

   }

    public ReservationPoolDto getPoolDetails(String poolId) throws Exception{

        PoolDto poolDto = sportPool.get(poolId);

        if(poolDto == null){
            throw new Exception("Invalid pool");
        }

        ReservationPoolDto reservationPoolDto = new ReservationPoolDto();
        reservationPoolDto.setPoolId(poolId);
        reservationPoolDto.setTeamDtoList(poolDto.getTeamDtoList());

        return reservationPoolDto;

    }

}
