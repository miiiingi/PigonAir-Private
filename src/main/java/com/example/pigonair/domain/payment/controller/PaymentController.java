package com.example.pigonair.domain.payment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.pigonair.domain.payment.dto.PaymentRequestDto.PostPayRequestDto;
import com.example.pigonair.domain.payment.dto.PaymentResponseDto;
import com.example.pigonair.domain.payment.service.PaymentServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentServiceImpl paymentService;

	@PostMapping("/pay")
	public String postPay(@RequestBody PostPayRequestDto requestDto, Model model) {
		PaymentResponseDto.TicketResponseDto responseDto = paymentService.postPayProcess(requestDto);
		model.addAttribute("responseDto", responseDto);
		return "ticket";
	}

}
