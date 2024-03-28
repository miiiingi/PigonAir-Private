package com.example.pigonair.domain.reservation.dto;

public record ReservationRequestDto(
        Long flightId,
        Long seatId
) { }
