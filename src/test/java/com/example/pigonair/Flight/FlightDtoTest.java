package com.example.pigonair.Flight;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.example.pigonair.domain.flight.dto.FlightRequestDto;
import com.example.pigonair.domain.flight.dto.FlightResponseDto;
import com.example.pigonair.domain.flight.entity.Airport;
import com.example.pigonair.domain.flight.entity.Flight;

public class FlightDtoTest {

	@Test
	public void testFlightDtoToEntityConversion() {
		// Given
		LocalDateTime departureTime = LocalDateTime.of(2024, 3, 28, 10, 0);
		LocalDateTime arrivalTime = LocalDateTime.of(2024, 3, 28, 12, 0);
		Airport origin = Airport.ICN;
		Airport destination = Airport.GMP;
		FlightRequestDto flightRequestDto = new FlightRequestDto(departureTime, arrivalTime, origin,
			destination);

		// When
		Flight flight = flightRequestDto.toEntity();

		// Then
		assertEquals(departureTime, flight.getDepartureTime());
		assertEquals(arrivalTime, flight.getArrivalTime());
		assertEquals(origin, flight.getOrigin());
		assertEquals(destination, flight.getDestination());
	}

	@Test
	public void testFlightToResponseDtoConversion() {
		// Given
		Long id = 1L;
		LocalDateTime departureTime = LocalDateTime.of(2024, 3, 28, 10, 0);
		LocalDateTime arrivalTime = LocalDateTime.of(2024, 3, 28, 12, 0);
		Airport origin = Airport.ICN;
		Airport destination = Airport.GMP;
		Flight flight = mock(Flight.class);
		when(flight.getId()).thenReturn(id);
		when(flight.getDepartureTime()).thenReturn(departureTime);
		when(flight.getArrivalTime()).thenReturn(arrivalTime);
		when(flight.getOrigin()).thenReturn(origin);
		when(flight.getDestination()).thenReturn(destination);

		// When
		FlightResponseDto flightResponseDto = new FlightResponseDto(flight);

		// Then
		assertEquals(id, flightResponseDto.id());
		assertEquals(departureTime, flightResponseDto.departureTime());
		assertEquals(arrivalTime, flightResponseDto.arrivalTime());
		assertEquals(origin, flightResponseDto.origin());
		assertEquals(destination, flightResponseDto.destination());
	}
}