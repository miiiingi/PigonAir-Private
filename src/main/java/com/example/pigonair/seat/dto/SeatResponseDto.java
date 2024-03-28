package com.example.pigonair.seat.dto;

import com.example.pigonair.seat.entity.Seat;

import lombok.Getter;

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
