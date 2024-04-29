package com.example.pigonair.domain.flight.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import com.example.pigonair.domain.flight.dto.FlightResponseDto;
import com.example.pigonair.domain.flight.entity.FlightPage;

public interface FlightService {
	Page<FlightResponseDto> getAllFlights(int page, int size, String orderBy, String direction);


	FlightPage<FlightResponseDto> getFlightsByConditions(LocalDateTime startDate, LocalDateTime endDate,
		String departureCode, String destinationCode, int page, int size, String orderBy, String direction);
}
