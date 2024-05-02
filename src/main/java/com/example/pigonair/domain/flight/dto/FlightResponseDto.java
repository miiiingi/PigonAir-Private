package com.example.pigonair.domain.flight.dto;

import java.time.LocalDateTime;

import com.example.pigonair.domain.flight.entity.Airport;
import com.example.pigonair.domain.flight.entity.Flight;

public record FlightResponseDto(
	Long id,
	LocalDateTime departureTime,
	LocalDateTime arrivalTime,
	Airport origin,
	Airport destination
) {
	public FlightResponseDto(Flight flight) {
		this(flight.getId(), flight.getDepartureTime(), flight.getArrivalTime(), flight.getOrigin(),
			flight.getDestination());
	}
}