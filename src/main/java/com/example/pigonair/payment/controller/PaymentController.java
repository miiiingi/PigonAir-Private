package com.example.pigonair.payment.controller;

import static org.springframework.web.socket.CloseStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pigonair.global.error.ReservationNotFoundException;
import com.example.pigonair.payment.dto.PaymentRequestDto.PostPayRequestDto;
import com.example.pigonair.payment.dto.PaymentResponseDto.TicketResponseDto;
import com.example.pigonair.payment.service.PaymentServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentServiceImpl paymentService;

	@PostMapping("/ticket")
	public ResponseEntity<?> postPay(@RequestBody PostPayRequestDto requestDto, Model model) {
		try {
			TicketResponseDto responseDto = paymentService.postPayProcess(requestDto);
			model.addAttribute(responseDto);
			return ResponseEntity.ok().build();
		} catch (ReservationNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SERVER_ERROR);
		}
	}
}
