package com.example.pigonair.domain.reservation.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.pigonair.domain.reservation.dto.ReservationRequestDto;
import com.example.pigonair.domain.reservation.dto.ReservationResponseDto;
import com.example.pigonair.domain.reservation.service.ReservationService;
import com.example.pigonair.global.config.common.exception.CustomException;
import com.example.pigonair.global.config.jmeter.JmeterService;
import com.example.pigonair.global.config.security.UserDetailsImpl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j(topic = "수행시간 측정")
public class ReservationController {

	private final ReservationService reservationService;
	private final JmeterService jmeterService;
	private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

	@PostMapping("/reservation") // 예약 진행
	public ResponseEntity<?> saveReservation(@RequestBody ReservationRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest request) {
		try {
			long startTime = System.currentTimeMillis();
			reservationService.saveReservation(requestDto, userDetails);
			long executionTime = System.currentTimeMillis() - startTime;
			log.info("saveReservation method executed in {} milliseconds", executionTime);
			jmeterService.setTransactionNameBasedOnJMeterTag(request);
			return ResponseEntity.ok().build();
		} catch (CustomException e) {
			log.error("Error occurred during saveReservation: {}", e.getMessage());
			return ResponseEntity.status(e.getHttpStatus()).build();
		}
	}

	@GetMapping("/reservation")    // 예약 확인
	public String getReservations(@AuthenticationPrincipal UserDetailsImpl userDetails,
		Model model, HttpServletRequest request) {
		try {
			long startTime = System.currentTimeMillis();
			List<ReservationResponseDto> reservations = reservationService.getReservations(userDetails);
			long executionTime = System.currentTimeMillis() - startTime;
			log.info("getReservations method executed in {} milliseconds", executionTime);
			model.addAttribute("reservations", reservations);
			jmeterService.setTransactionNameBasedOnJMeterTag(request);
			return "reservation/reservation_history";
		} catch (CustomException e) {
			log.error("Error occurred during getReservations: {}", e.getMessage());
			return "error"; // 에러 페이지로 리다이렉트 또는 메시지 표시
		}
	}

	@DeleteMapping("/reservation/{reservation_id}") // 예약 취소
	public ResponseEntity<?> cancelReservation(@PathVariable Long reservation_id,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		try {
			long startTime = System.currentTimeMillis();
			reservationService.cancelReservation(reservation_id, userDetails);
			long executionTime = System.currentTimeMillis() - startTime;
			log.info("cancelReservation method executed in {} milliseconds", executionTime);
			return ResponseEntity.ok().build();
		} catch (CustomException e) {
			log.error("Error occurred during cancelReservation: {}", e.getMessage());
			return ResponseEntity.status(e.getHttpStatus()).build();
		}
	}
}
