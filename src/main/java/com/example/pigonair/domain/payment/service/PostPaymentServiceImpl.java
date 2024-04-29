package com.example.pigonair.domain.payment.service;

import static com.example.pigonair.global.config.common.exception.ErrorCode.*;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


// import com.example.pigonair.domain.email.EmailService;

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
	// private final RabbitTemplate rabbitTemplate;

	// private final EmailService emailService;
  
	@Async("asyncExecutor")
	@Override
	public void savePayInfoAndSendMail(PaymentRequestDto.PostPayRequestDto postPayRequestDto) {
		log.info("run() - 현재 스레드 개수 : {}", Thread.activeCount());
		log.info("run() - 현재 id: {}", Thread.currentThread().getId());
		Long paymentId = savePayInfo(postPayRequestDto);
		EmailDto.EmailSendDto emailSendDto = new EmailDto.EmailSendDto(paymentId, postPayRequestDto.email());
		// sendEmailToMessageQ(emailSendDto);	// 메세지 큐 이용
		// sendEmail(emailSendDto);	// @Async 이용
	}

	private Long savePayInfo(
		PaymentRequestDto.PostPayRequestDto postPayRequestDto) {
		// 추후 데이터 삽입 시 외래키만 삽입하는 것으로 변경하는 것 고려
		Reservation reservation = reservationRepository.findById(postPayRequestDto.id()).orElseThrow(() ->
			new CustomException(RESERVATION_NOT_FOUND));
		Payment payment = new Payment(reservation, postPayRequestDto.serialNumber());
		Payment savePayment = paymentRepository.save(payment);
		return savePayment.getId();
	}

	// @Scheduled(fixedDelay = 10000) // 10초마다 배치 처리
	// public void processEmailBatch() {
	// 	if (!emailBatchQueue.isEmpty()) {
	// 		log.info("Starting email batch processing...");
	//
	// 		List<EmailDto.EmailSendDto> batchToSend = new ArrayList<>(emailBatchQueue);
	// 		emailBatchQueue.clear(); // 배치 큐 비우기
	//
	// 		// 이메일 일괄 전송
	// 		batchToSend.forEach(this::sendPaymentCompletedEvent);
	//
	// 		log.info("Email batch processing completed.");
	// 	}
	// }

	// private void sendEmailToMessageQ(EmailDto.EmailSendDto emailSendDto) {
	// 	rabbitTemplate.convertAndSend("payment.exchange", "payment.key", emailSendDto);
	// }


	private void sendEmail(EmailDto.EmailSendDto emailDto) {
		try{
			System.out.println("Payment completed for payment ID : " + emailDto.paymentId() + "\n");

			String recipientEmail = emailDto.email();
			String subject = "티켓 결제 완료";
			String body = "티켓 번호: " + emailDto.paymentId();
      
			// emailService.sendEmail(recipientEmail, subject, body);
		} catch (Exception ex) {
			log.error("Payment 처리 중 오류 발생: {}", ex.getMessage(), ex);
		}
	}

}
