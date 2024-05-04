package com.example.pigonair.domain.flight.service;

import static com.example.pigonair.global.config.common.exception.ErrorCode.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.pigonair.domain.flight.dto.FlightResponseDto;
import com.example.pigonair.domain.flight.entity.Airport;
import com.example.pigonair.domain.flight.entity.FlightPage;
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
	@Cacheable(value = "flightCache", key = "{#startDate, #departureCode, #destinationCode, #page, #size, #orderBy, #direction}", unless = "#result.isEmpty()")
	public FlightPage<FlightResponseDto> getFlightsByConditions(String startDate, String endDate,
		String departureCode, String destinationCode, int page, int size, String orderBy, String direction) {

		LocalDateTime startDateTime;
		LocalDateTime endDateTime;

		startDateTime = startTimeParser(startDate);
		endDateTime = endTimeParser(endDate);

		try {
			Sort sort = Sort.by(orderBy);
			if (direction.equalsIgnoreCase("DESC")) {
				sort = sort.descending();
			} else {
				sort = sort.ascending();
			}

			Pageable pageable = PageRequest.of(page - 1, size, sort);

			Airport departure = Airport.valueOf(departureCode);
			Airport destination = Airport.valueOf(destinationCode);

			return new FlightPage<>(
				flightRepository.findByDepartureTimeBetweenAndOriginAndDestination(startDateTime,
					endDateTime,
					departure,
					destination,
					pageable)
					.map(FlightResponseDto::new));
		} catch (IllegalArgumentException ex) {
			log.error("항공편 검색 조건이 잘못되었습니다.", ex);
			throw new CustomException(INVALID_SEARCH_CONDITION);
		} catch (DataAccessException ex) {
			log.error("조건별 항공편 조회 중 데이터베이스 오류 발생", ex);
			throw new CustomException(DATABASE_ERROR);
		}
	}

	private LocalDateTime startTimeParser(String startDate){
		try {
			LocalDate parsedStartDate = LocalDate.parse(startDate);
			return parsedStartDate.atStartOfDay();
		} catch (Exception e) {
			return LocalDateTime.parse(startDate);
		}
	}

	private LocalDateTime endTimeParser(String endDate){
		try{
			LocalDate parsedEndDate = LocalDate.parse(endDate);
			return parsedEndDate.atTime(LocalTime.MAX);
		}
		catch (Exception e) {
			return  LocalDateTime.parse(endDate);
		}
	}
}