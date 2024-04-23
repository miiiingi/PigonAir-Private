package com.example.pigonair.domain.flight.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.pigonair.domain.flight.dto.FlightResponseDto;
import com.example.pigonair.domain.flight.entity.FlightPage;
import com.example.pigonair.domain.flight.service.FlightService;
import com.example.pigonair.global.config.common.exception.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FlightController {
	private final FlightService flightService;

	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_SIZE = 10;
	private static final String DEFAULT_ORDER_BY = "departureTime";
	private static final String DEFAULT_ORDER_DIRECTION = "ASC";

	@GetMapping("/flight")
	public String getAllFlights(
		@RequestParam(defaultValue = "" + DEFAULT_PAGE) int page,
		@RequestParam(defaultValue = "" + DEFAULT_SIZE) int size,
		@RequestParam(defaultValue = DEFAULT_ORDER_BY) String orderBy,
		@RequestParam(defaultValue = DEFAULT_ORDER_DIRECTION) String orderDirection,
		Model model) {
		try {
			Page<FlightResponseDto> flightsPage = flightService.getAllFlights(page, size, orderBy, orderDirection);
			populateModel(model, flightsPage, page, size, orderBy, orderDirection);
		} catch (CustomException ex) {
			model.addAttribute("ErrorMessage", ex.getErrorCode().getMessage());
			return "index";
		}
		return "flight-result";
	}

	@GetMapping("/flight/{start_date}/{end_date}/{departure}/{destination}")
	public String getFlightsByConditions(
		@PathVariable("start_date") String startDate,
		@PathVariable("end_date") String endDate,
		@PathVariable("departure") String departure,
		@PathVariable("destination") String destination,
		@RequestParam(defaultValue = "" + DEFAULT_PAGE) int page,
		@RequestParam(defaultValue = "" + DEFAULT_SIZE) int size,
		@RequestParam(defaultValue = DEFAULT_ORDER_BY) String orderBy,
		@RequestParam(defaultValue = DEFAULT_ORDER_DIRECTION) String orderDirection,
		Model model) {

		try {
			LocalDate startTime = LocalDate.parse(startDate);
			LocalDate endTime = LocalDate.parse(endDate);
			LocalDateTime startDateTime = startTime.atStartOfDay();
			LocalDateTime endDateTime = endTime.atTime(LocalTime.MAX);
			FlightPage<FlightResponseDto> flightsPage = flightService.getFlightsByConditions(
				startDateTime, endDateTime, departure, destination, page, size, orderBy, orderDirection);
			// FlightPage<FlightResponseDto> flightsPage = flightService.getFlightsByConditions(
			// 		startDate, endDate, departure, destination, page, size, orderBy, orderDirection);
			populateModel(model, flightsPage, page, size, orderBy, orderDirection);
		}catch (CustomException ex) {
			model.addAttribute("ErrorMessage", ex.getErrorCode().getMessage());
			return "index";
		}
		return "flight-result";
	}

	private void populateModel(Model model, Page<FlightResponseDto> flightsPage, int page, int size, String orderBy,
		String orderDirection) {
		List<FlightResponseDto> flightsList = flightsPage.getContent();
		model.addAttribute("flights", flightsList);
		model.addAttribute("totalPages", flightsPage.getTotalPages());
		model.addAttribute("currentPage", page);
		model.addAttribute("flightSize", size);
		model.addAttribute("orderByVal", orderBy);
		model.addAttribute("orderDirectionVal", orderDirection);
	}
}