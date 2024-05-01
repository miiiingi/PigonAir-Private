package com.example.pigonair.domain.reservation.dto;

import java.time.LocalDateTime;

public record ReservationResponseDto(
	Long id,
	LocalDateTime departureTime,
	String origin,
	String destination,
	Long seatNumber,
	Long price

) {
}

