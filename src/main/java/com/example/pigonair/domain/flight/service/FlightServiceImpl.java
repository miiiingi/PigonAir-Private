package com.example.pigonair.domain.flight.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.pigonair.domain.flight.dto.FlightResponseDto;
import com.example.pigonair.domain.flight.entity.Airport;
import com.example.pigonair.domain.flight.entity.Flight;
import com.example.pigonair.domain.flight.repository.FlightRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
	private final FlightRepository flightRepository;

	@Override
	public Page<FlightResponseDto> getAllFlights(int page, int size, String orderBy, String direction) {
		Sort sort = Sort.by(orderBy);
		if (direction.equalsIgnoreCase("DESC")) {
			sort = sort.descending();
		} else {
			sort = sort.ascending();
		}

		Pageable pageable = PageRequest.of(page - 1, size, sort);

		Page<Flight> flightPage = flightRepository.findAll(pageable);

		return flightPage.map(FlightResponseDto::new);
	}

	@Override
	public Page<FlightResponseDto> getFlightsByConditions(LocalDateTime startDate, LocalDateTime endDate,
		String departureCode, String destinationCode, int page, int size, String orderBy, String direction) {
		Sort sort = Sort.by(orderBy);
		if (direction.equalsIgnoreCase("DESC")) {
			sort = sort.descending();
		} else {
			sort = sort.ascending();
		}

		Pageable pageable = PageRequest.of(page - 1, size, sort);

		Airport departure = Airport.valueOf(departureCode);
		Airport destination = Airport.valueOf(destinationCode);

		log.info(String.valueOf(departure));

		Page<Flight> flightsPage = flightRepository.findByDepartureTimeBetweenAndOriginAndDestination(startDate, endDate, departure, destination, pageable);

		return flightsPage.map(FlightResponseDto::new);
	}
}
