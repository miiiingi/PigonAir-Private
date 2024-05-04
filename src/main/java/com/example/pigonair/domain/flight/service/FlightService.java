package com.example.pigonair.domain.flight.service;

import com.example.pigonair.domain.flight.dto.FlightResponseDto;
import com.example.pigonair.domain.flight.entity.FlightPage;

public interface FlightService {
	FlightPage<FlightResponseDto> getFlightsByConditions(String startDate,
		String endDate,
		String departureCode,
		String destinationCode,
		int page,
		int size,
		String orderBy,
		String direction);
}