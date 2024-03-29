package com.example.pigonair.domain.reservation.service;

import com.example.pigonair.domain.reservation.dto.ReservationRequestDto;
import com.example.pigonair.domain.reservation.dto.ReservationResponseDto;
import com.example.pigonair.global.config.security.UserDetailsImpl;

import java.util.List;

public interface ReservationService {
    void saveReservation(ReservationRequestDto requestDto, UserDetailsImpl userDetails);

    List<ReservationResponseDto> getReservations(UserDetailsImpl userDetails);
}
