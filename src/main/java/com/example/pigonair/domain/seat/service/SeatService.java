package com.example.pigonair.domain.seat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.pigonair.domain.flight.entity.Flight;
import com.example.pigonair.domain.flight.repository.FlightRepository;
import com.example.pigonair.domain.seat.dto.SeatResponseDto;
import com.example.pigonair.domain.seat.entity.Seat;
import com.example.pigonair.domain.seat.repository.SeatRepository;
import com.example.pigonair.global.config.common.exception.CustomException;
import com.example.pigonair.global.config.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatService {
	private final SeatRepository seatRepository;
	private final FlightRepository flightRepository;

	@Cacheable(value = "seatCache", key = "#flightId")
	public List<SeatResponseDto> getSeatingChart(Long flightId) {
		if(!validateFlight(flightId)){
			throw new CustomException(ErrorCode.NOT_EXITS_FLIGHT);
		}
		List<Seat> seats = seatRepository.findAllByflightId(flightId);
		List<SeatResponseDto> seatResponseDtos = new ArrayList<>();
		for (Seat seat : seats) {
			seatResponseDtos.add(new SeatResponseDto(seat));
		}
		return seatResponseDtos;

	}
	public boolean validateFlight(Long flightId){
		Flight flight = flightRepository.findById(flightId).orElseThrow(() ->
			new NullPointerException("해당 비행기는 존재하지 않습니다."));

		return true;
	}
}

