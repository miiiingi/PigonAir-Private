package com.example.pigonair.domain.payment.service;

import static com.example.pigonair.global.config.common.exception.ErrorCode.*;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.pigonair.domain.payment.dto.PaymentRequestDto;
import com.example.pigonair.domain.payment.dto.PaymentResponseDto;
import com.example.pigonair.domain.payment.entity.Payment;
import com.example.pigonair.domain.payment.repository.PaymentRepository;
import com.example.pigonair.domain.reservation.repository.ReservationRepository;
import com.example.pigonair.global.config.common.exception.CustomException;
import com.example.pigonair.reservation.entity.Reservation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final ReservationRepository reservationRepository;
	private final PaymentRepository paymentRepository;

	@Override
	@Transactional
	public PaymentResponseDto.TicketResponseDto postPayProcess(PaymentRequestDto.PostPayRequestDto requestDto) {
		Optional<Reservation> optionalReservation = reservationRepository.findById(requestDto.id());
		if (optionalReservation.isEmpty()) {
			// 예약을 찾지 못함 에러
			throw new CustomException(RESERVATION_NOT_FOUND);
		}
		Reservation reservation = optionalReservation.get();

		//결제 금액과 좌석 가격이 같은지 확인
		if (reservation.getSeat().getPrice() != requestDto.paidAmount()) {
			// 결제 금액 불일치 에러
			throw new CustomException(PAYMENT_AMOUNT_MISMATCH);
		}

		//결제 후 결제 여부 변경
		reservation.updateIsPayment();
		savePayInfo(requestDto.serialNumber(), reservation);

		return new PaymentResponseDto.TicketResponseDto(reservation);
	}

	@Override
	@Transactional
	public void savePayInfo(String serialNumber, Reservation reservation) {
		Payment payment = new Payment(reservation, serialNumber);
		if (paymentRepository.existsByReservationId(reservation.getId())) {
			throw new CustomException(ALREADY_PAID_RESERVATION);
		}
		paymentRepository.save(payment);
	}
}
