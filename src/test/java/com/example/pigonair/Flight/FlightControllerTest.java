package com.example.pigonair.Flight;

import com.example.pigonair.domain.flight.controller.FlightController;
import com.example.pigonair.domain.flight.dto.FlightResponseDto;
import com.example.pigonair.domain.flight.service.FlightService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightControllerTest {

	@Mock
	private FlightService flightService;

	@InjectMocks
	private FlightController flightController;

	@Mock
	private Model model;

	@Test
	void getFlightsByConditions() {
		// Given
		LocalDateTime startDate = LocalDateTime.of(2024, 3, 28, 12, 0);
		LocalDateTime endDate = LocalDateTime.of(2024, 3, 30, 12, 0);
		String departure = "ICN";
		String destination = "GMP";
		int page = 1;
		int size = 10;
		String orderBy = "Id";
		String orderDirection = "ASC";
		Page<FlightResponseDto> mockFlightList = Mockito.mock(Page.class);

		// Mock behavior of flightService
		when(flightService.getFlightsByConditions(startDate, endDate, departure, destination, page, size, orderBy, orderDirection))
			.thenReturn(mockFlightList);

		// When
		String viewName = flightController.getFlightsByConditions(startDate, endDate, departure, destination, page, size, orderBy, orderDirection, model);

		// Then
		assertEquals("flight-result", viewName);
		verify(model).addAttribute(eq("flights"), anyList());
		verify(model).addAttribute("flights", mockFlightList);
		verify(flightService).getFlightsByConditions(startDate, endDate, departure, destination, page, size, orderBy, orderDirection);
	}
}
