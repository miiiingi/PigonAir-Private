package com.example.pigonair.domain.flight.service;

import com.example.pigonair.domain.flight.dto.FlightResponseDto;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

public interface FlightService {
	Page<FlightResponseDto> getAllFlights(int page, int size, String orderBy, String direction);

	Page<FlightResponseDto> getFlightsByConditions(LocalDateTime startDate, LocalDateTime endDate,
		String departureCode, String destinationCode, int page, int size, String orderBy, String direction);
}
