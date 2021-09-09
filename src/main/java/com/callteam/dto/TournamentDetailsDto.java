package com.callteam.dto;

public class TournamentDetailsDto {

    private String id;
    private TournamentDto tournamentDto;
    private SportDto sportDto;
    private PlayGroundDto playGroundDto;
    private PaymentDto paymentDto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TournamentDto getTournamentDto() {
        return tournamentDto;
    }

    public void setTournamentDto(TournamentDto tournamentDto) {
        this.tournamentDto = tournamentDto;
    }

    public SportDto getSportDto() {
        return sportDto;
    }

    public void setSportDto(SportDto sportDto) {
        this.sportDto = sportDto;
    }

    public PlayGroundDto getPlayGroundDto() {
        return playGroundDto;
    }

    public void setPlayGroundDto(PlayGroundDto playGroundDto) {
        this.playGroundDto = playGroundDto;
    }

    public PaymentDto getPaymentDto() {
        return paymentDto;
    }

    public void setPaymentDto(PaymentDto paymentDto) {
        this.paymentDto = paymentDto;
    }
}
