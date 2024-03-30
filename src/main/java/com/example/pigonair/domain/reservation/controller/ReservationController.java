package com.example.pigonair.domain.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.pigonair.domain.reservation.dto.ReservationRequestDto;
import com.example.pigonair.domain.reservation.dto.ReservationResponseDto;
import com.example.pigonair.domain.reservation.service.ReservationService;
import com.example.pigonair.global.config.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReservationController {
	private final ReservationService reservationService;

	@Value("${iamport.impKey}")
	private String impKey;

	@PostMapping("/api/reservation") // 예약 진행
	public ResponseEntity<?> saveReservation(@RequestBody ReservationRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {  // 로그인 기능 구현 완료 시 UserDetail 추가
		try {
			reservationService.saveReservation(requestDto, userDetails);
			return ResponseEntity.ok().build();
		} catch (NullPointerException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@GetMapping("/api/reservation") // 예약 진행
	public String getReservations(@AuthenticationPrincipal UserDetailsImpl userDetails,
		Model model) {  // 로그인 기능 구현 완료 시 UserDetail 추가
		List<ReservationResponseDto> reservations = reservationService.getReservations(userDetails);
		model.addAttribute("reservations", reservations);
		model.addAttribute("impKey", impKey);
		return "/reservation/reservation_history";
	}
}
