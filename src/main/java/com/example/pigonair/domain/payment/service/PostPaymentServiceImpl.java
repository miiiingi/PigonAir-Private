package com.example.pigonair.domain.payment.service;

import static com.example.pigonair.global.config.common.exception.ErrorCode.*;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.pigonair.domain.payment.dto.EmailDto;
import com.example.pigonair.domain.payment.dto.PaymentRequestDto;
import com.example.pigonair.domain.payment.entity.Payment;
import com.example.pigonair.domain.payment.repository.PaymentRepository;
import com.example.pigonair.domain.reservation.entity.Reservation;
import com.example.pigonair.domain.reservation.repository.ReservationRepository;
import com.example.pigonair.global.config.common.exception.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostPaymentServiceImpl implements PostPaymentService {
	private final ReservationRepository reservationRepository;
	private final PaymentRepository paymentRepository;
	private final RabbitTemplate rabbitTemplate;

	// private final EmailService emailService;

	@Async("asyncExecutor")
	@Override
	public void savePayInfoAndSendMail(PaymentRequestDto.PostPayRequestDto postPayRequestDto) {
		Long ticketId;
		try {
			ticketId = savePayInfo(postPayRequestDto);
		} catch (Exception e) {
			// 추후에 결제 취소 로직 추가
			throw new CustomException(ALREADY_PAID_RESERVATION);
		}

		EmailDto.EmailSendDto emailSendDto = new EmailDto.EmailSendDto(ticketId, postPayRequestDto.email());
		sendEmailToMessageQ(emailSendDto);    // 메세지 큐 이용
	}

	private Long savePayInfo(
		PaymentRequestDto.PostPayRequestDto postPayRequestDto) {
		// 추후 데이터 삽입 시 외래키만 삽입하는 것으로 변경하는 것 고려
		Reservation reservation = reservationRepository.findById(postPayRequestDto.id()).orElseThrow(() ->
			new CustomException(RESERVATION_NOT_FOUND));
		Payment payment = new Payment(reservation, postPayRequestDto.serialNumber());
		paymentRepository.save(payment);
		return reservation.getId();
	}


	private void sendEmailToMessageQ(EmailDto.EmailSendDto emailSendDto) {
		rabbitTemplate.convertAndSend("payment.exchange", "payment.key", emailSendDto);
	}

}
