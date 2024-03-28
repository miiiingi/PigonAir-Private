package com.example.pigonair.domain.seat.dto;

import com.example.pigonair.domain.seat.entity.Seat;

public record SeatResponseDto (
	Long seatId,
	Boolean isAvailable
){
	public SeatResponseDto(Seat seat){
		this(
			seat.getId(),
			seat.isAvailable()
		);
	}
}
