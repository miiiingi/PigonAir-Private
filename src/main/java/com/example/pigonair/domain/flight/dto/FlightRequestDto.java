package com.example.pigonair.domain.flight.dto;

import java.time.LocalDateTime;

import com.example.pigonair.domain.flight.entity.Airport;
import com.example.pigonair.domain.flight.entity.Flight;

public record FlightRequestDto(LocalDateTime departureTime,
							   LocalDateTime arrivalTime,
							   Airport origin,
							   Airport destination) {
	public Flight toEntity()
	{
		return Flight.builder()
			.departureTime(departureTime)
			.arrivalTime(arrivalTime)
			.origin(origin)
			.destination(destination)
			.build();
	}
}