package com.example.pigonair.domain.seat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.pigonair.domain.seat.dto.SeatResponseDto;
import com.example.pigonair.domain.seat.entity.Seat;
import com.example.pigonair.domain.seat.repository.SeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatService {
	private final SeatRepository seatRepository;

	public List<SeatResponseDto> getSeatingChart(Long flightId) {
		List<Seat> seats = seatRepository.findAllByflightId(flightId);
		List<SeatResponseDto> seatResponseDtos = new ArrayList<>();
		for (Seat seat : seats) {
			seatResponseDtos.add(new SeatResponseDto(seat));
		}

		return seatResponseDtos;
	}
}
