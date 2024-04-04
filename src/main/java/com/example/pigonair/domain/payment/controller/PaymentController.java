package com.example.pigonair.domain.payment.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.pigonair.domain.payment.dto.PaymentRequestDto.PostPayRequestDto;
import com.example.pigonair.domain.payment.dto.PaymentResponseDto.PayResponseDto;
import com.example.pigonair.domain.payment.dto.PaymentResponseDto.TicketResponseDto;
import com.example.pigonair.domain.payment.service.PaymentServiceImpl;
import com.example.pigonair.global.config.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentServiceImpl paymentService;

	@GetMapping("/pay/{reservationId}")
	public String pay(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model,
		@PathVariable("reservationId") Long id) {
		PayResponseDto responseDto = paymentService.payProcess(id, userDetails.getUser());
		model.addAttribute("responseDto", responseDto);
		return "pay";
	}

	@PostMapping("/pay")
	public String postPay(@RequestBody PostPayRequestDto requestDto, Model model) {
		TicketResponseDto responseDto = paymentService.postPayProcess(requestDto);
		model.addAttribute("responseDto", responseDto);
		return "ticket";
	}
}
