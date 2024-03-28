package com.example.pigonair.domain.reservation.service;

import com.example.pigonair.domain.reservation.dto.ReservationRequestDto;
import com.example.pigonair.domain.reservation.dto.ReservationResponseDto;

import java.util.List;

public interface ReservationService {
    void saveReservation(ReservationRequestDto requestDto, Long userId);

    List<ReservationResponseDto> getReservations(Long userId);
}
