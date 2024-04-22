package com.example.pigonair.Flight;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.pigonair.domain.flight.dto.FlightResponseDto;
import com.example.pigonair.domain.flight.entity.Airport;
import com.example.pigonair.domain.flight.entity.Flight;
import com.example.pigonair.domain.flight.repository.FlightRepository;
import com.example.pigonair.domain.flight.service.FlightServiceImpl;

@ExtendWith(MockitoExtension.class)
class FlightServiceImplTest {

	@Mock
	private FlightRepository flightRepository;

	private FlightServiceImpl flightService;

	@BeforeEach
	void setUp() {
		flightService = new FlightServiceImpl(flightRepository);
	}

	@Test
	void getAllFlights() {
		// Mock data
		Flight flight = Flight.builder()
			.departureTime(LocalDateTime.now())
			.origin(Airport.LHR)
			.destination(Airport.JFK)
			.build();

		Page<Flight> page = new PageImpl<>(Collections.singletonList(flight));

		// Mock repository behavior
		when(flightRepository.findAll(any(Pageable.class))).thenReturn(page);

		// Test
		Page<FlightResponseDto> result = flightService.getAllFlights(1, 10, "id", "ASC");

		// Assertions
		assertEquals(1, result.getContent().size());
		assertEquals(flight.getId(), result.getContent().get(0).id());
		// Add more assertions based on your DTO and entity mappings
	}

	@Test
	void getFlightsByConditions() {
		// Mock data
		Flight flight = Flight.builder()
			.departureTime(LocalDateTime.now())
			.origin(Airport.LHR)
			.destination(Airport.JFK)
			.build();
		Page<Flight> page = new PageImpl<>(Collections.singletonList(flight));

		// Mock repository behavior
		when(flightRepository.findByDepartureTimeBetweenAndOriginAndDestination(any(LocalDateTime.class),
			any(LocalDateTime.class), any(Airport.class), any(Airport.class), any(Pageable.class))).thenReturn(page);

		// Test
		Page<FlightResponseDto> result = flightService.getFlightsByConditions(LocalDateTime.now(),
			LocalDateTime.now().plusHours(1), "LHR", "JFK", 1, 10, "id", "ASC");

		// Assertions
		assertEquals(1, result.getContent().size());
		assertEquals(flight.getId(), result.getContent().get(0).id());
		// Add more assertions based on your DTO and entity mappings
	}


}