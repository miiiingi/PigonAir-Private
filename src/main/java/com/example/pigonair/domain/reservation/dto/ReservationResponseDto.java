package com.example.pigonair.domain.reservation.dto;

import java.time.LocalDateTime;

public record ReservationResponseDto(
	Long id,
	String memberName,
	LocalDateTime departureDate,
	LocalDateTime departureTime,
	String origin,
	String destination,
	Long seatNumber,
	Long price,
	String phoneNumber

) {
}

