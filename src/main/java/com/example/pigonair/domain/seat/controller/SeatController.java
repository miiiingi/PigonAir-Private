package com.example.pigonair.domain.seat.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.pigonair.domain.seat.dto.SeatResponseDto;
import com.example.pigonair.domain.seat.service.SeatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/flight")
@RequiredArgsConstructor
public class SeatController {

	private final SeatService seatService;
	@GetMapping("/{flightId}")
	public String getSeatingChart(@PathVariable Long flightId, Model model){
		System.out.println("flightId = " + flightId);
		List<SeatResponseDto> seatsDto = seatService.getSeatingChart(flightId);
		System.out.println(seatsDto);
		model.addAttribute("seats",seatsDto);
		return "seats/seatList";
	}
}
