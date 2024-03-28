package com.example.pigonair.domain.reservation.controller;

import com.example.pigonair.domain.reservation.dto.ReservationRequestDto;
import com.example.pigonair.domain.reservation.dto.ReservationResponseDto;
import com.example.pigonair.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/api/reservation") // 예약 진행
    public ResponseEntity<?> saveReservation(@RequestBody ReservationRequestDto requestDto) {  // 로그인 기능 구현 완료 시 UserDetail 추가
        Long userId = 1L;
        reservationService.saveReservation(requestDto, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/reservation") // 예약 진행
    public String getReservations(Model model) {  // 로그인 기능 구현 완료 시 UserDetail 추가
        Long userId = 1L;
        List<ReservationResponseDto> reservations = reservationService.getReservations(userId);
        model.addAttribute("reservations",reservations);
        return "/reservation/reservation_history";
    }
}
