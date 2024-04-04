package com.example.pigonair.domain.flight.service;

import static com.example.pigonair.global.config.common.exception.ErrorCode.*;

import java.time.LocalDateTime;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.pigonair.domain.flight.dto.FlightResponseDto;
import com.example.pigonair.domain.flight.entity.Airport;
import com.example.pigonair.domain.flight.entity.Flight;
import com.example.pigonair.domain.flight.repository.FlightRepository;
import com.example.pigonair.global.config.common.exception.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
	private final FlightRepository flightRepository;

	@Override
	public Page<FlightResponseDto> getAllFlights(int page, int size, String orderBy, String direction) {
		try {
			Sort sort = Sort.by(orderBy);
			if (direction.equalsIgnoreCase("DESC")) {
				sort = sort.descending();
			} else {
				sort = sort.ascending();
			}

			Pageable pageable = PageRequest.of(page - 1, size, sort);

			Page<Flight> flightPage = flightRepository.findAll(pageable);

			return flightPage.map(FlightResponseDto::new);
		} catch (DataAccessException ex) {
			log.error("모든 항공편 조회 중 데이터베이스 오류 발생", ex);
			throw new CustomException(DATABASE_ERROR);
		}
	}

	@Override
	public Page<FlightResponseDto> getFlightsByConditions(LocalDateTime startDate, LocalDateTime endDate,
		String departureCode, String destinationCode, int page, int size, String orderBy, String direction) {
		try {
			Sort sort = Sort.by(orderBy);
			if (direction.equalsIgnoreCase("DESC")) {
				sort = sort.descending();
			} else {
				sort = sort.ascending();
			}

			Pageable pageable = PageRequest.of(page - 1, size, sort);

			//IllegalArgumentException은 valueof에서 알아서 잡아준다.
			Airport departure = Airport.valueOf(departureCode);
			Airport destination = Airport.valueOf(destinationCode);

			log.info(String.valueOf(departure));

			Page<Flight> flightsPage = flightRepository.findByDepartureTimeBetweenAndOriginAndDestination(startDate, endDate, departure, destination, pageable);

			return flightsPage.map(FlightResponseDto::new);
		} catch (IllegalArgumentException ex) {
			log.error("항공편 검색 조건이 잘못되었습니다.", ex);
			throw new CustomException(INVALID_SEARCH_CONDITION);
		} catch (DataAccessException ex) {
			log.error("조건별 항공편 조회 중 데이터베이스 오류 발생", ex);
			throw new CustomException(DATABASE_ERROR);
		}
	}
}