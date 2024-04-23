package com.example.pigonair.domain.payment.service;

import com.example.pigonair.domain.payment.dto.PaymentRequestDto;

public interface PostPaymentService {
	void savePayInfoAndSendMail(PaymentRequestDto.PostPayRequestDto postPayRequestDto);
}
