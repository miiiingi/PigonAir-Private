package com.example.pigonair.Flight;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.example.pigonair.domain.flight.dto.FlightResponseDto;
import com.example.pigonair.domain.flight.entity.Airport;
import com.example.pigonair.domain.flight.entity.Flight;
import com.example.pigonair.domain.flight.repository.FlightRepository;
import com.example.pigonair.domain.flight.service.FlightService;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

	@Mock
	private FlightRepository flightRepository;

	@InjectMocks
	private FlightService flightService;

	@Test
	void getFlightsByConditions() {
		// Given
		LocalDateTime startDate = LocalDateTime.of(2024, 3, 28, 12, 0);
		LocalDateTime endDate = LocalDateTime.of(2024, 3, 30, 12, 0);
		String departureCode = "LAX";
		String destinationCode = "ORD";
		int page = 1;
		int size = 10;
		String orderBy = "departureTime";
		String direction = "ASC";

		// Mocking behavior of flightRepository
		Page<Flight> mockedPage = mock(Page.class);
		when(flightRepository.findByDepartureTimeBetweenAndOriginAndDestination(
			startDate, endDate, Airport.valueOf(departureCode), Airport.valueOf(destinationCode),
			PageRequest.of(page, size, Sort.by(orderBy).ascending())
		)).thenReturn(mockedPage);

		// When
		Page<FlightResponseDto> result = flightService.getFlightsByConditions(
			startDate, endDate, departureCode, destinationCode, page, size, orderBy, direction);

		// Then
		verify(flightRepository, times(1)).findByDepartureTimeBetweenAndOriginAndDestination(
			startDate, endDate, Airport.valueOf(departureCode), Airport.valueOf(destinationCode),
			PageRequest.of(page, size, Sort.by(orderBy).ascending())
		);
	}
}
