package com.example.pigonair.domain.payment.service;

import static com.example.pigonair.global.config.common.exception.ErrorCode.*;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.pigonair.domain.member.entity.Member;
import com.example.pigonair.domain.payment.dto.PaymentRequestDto.PostPayRequestDto;
import com.example.pigonair.domain.payment.dto.PaymentResponseDto.PayResponseDto;
import com.example.pigonair.domain.payment.entity.Payment;
import com.example.pigonair.domain.payment.repository.PaymentRepository;
import com.example.pigonair.domain.reservation.entity.Reservation;
import com.example.pigonair.domain.reservation.repository.ReservationRepository;
import com.example.pigonair.global.config.common.exception.CustomException;
import com.example.pigonair.global.config.common.ulid.Ulid;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PostPaymentService postPaymentService;
	private final ReservationRepository reservationRepository;
	private final PaymentRepository paymentRepository;
	@Value("${iamport.impKey}")
	private String impKey;

	@Override
	@Transactional
	public PayResponseDto payProcess(Long ReservationId, Member member) {
		Reservation reservation = reservationRepository.findById(ReservationId).orElseThrow(() ->
			new CustomException(RESERVATION_NOT_FOUND));
		String payUlid = getUlid();

		return new PayResponseDto(reservation, member, payUlid, impKey);
	}

	@Override
	@Transactional
	public void postPayProcess(PostPayRequestDto requestDto) {
		Reservation reservation = reservationRepository.findById(requestDto.id()).orElseThrow(() ->
			new CustomException(RESERVATION_NOT_FOUND));

		checkPay(requestDto, reservation);
		reservation.updateIsPayment();
		postPaymentService.savePayInfoAndSendMail(requestDto);
	}

	private static void checkPay(PostPayRequestDto requestDto, Reservation reservation) {
		if (reservation.isPayment()) {
			throw new CustomException(ALREADY_PAID_RESERVATION);
		}

		//결제 금액과 좌석 가격이 같은지 확인
		if (!Objects.equals(reservation.getSeat().getPrice(), requestDto.paidAmount())) {
			// 결제 금액 불일치 에러
			throw new CustomException(PAYMENT_AMOUNT_MISMATCH);
		}
	}

	@Override
	@Transactional
	public Long savePayInfo(PostPayRequestDto postPayRequestDto) { // 추후 데이터 삽입 시 외래키만 삽입하는 것으로 변경하는 것 고려
		Reservation reservation = reservationRepository.findById(postPayRequestDto.id()).orElseThrow(() ->
			new CustomException(RESERVATION_NOT_FOUND));
		Payment payment = new Payment(reservation, postPayRequestDto.serialNumber());
		Payment savePayment = paymentRepository.save(payment);
		return savePayment.getId();
	}

	private String getUlid() {
		Ulid ulid = new Ulid();
		return ulid.nextULID();
	}

}
