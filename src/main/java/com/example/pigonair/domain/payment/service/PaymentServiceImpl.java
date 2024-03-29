package com.example.pigonair.domain.payment.service;

import static com.example.pigonair.global.config.common.exception.ErrorCode.*;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.pigonair.domain.payment.dto.PaymentRequestDto.PostPayRequestDto;
import com.example.pigonair.domain.payment.dto.PaymentResponseDto.TicketResponseDto;
import com.example.pigonair.domain.payment.entity.Payment;
import com.example.pigonair.domain.payment.repository.PaymentRepository;
import com.example.pigonair.domain.reservation.entity.Reservation;
import com.example.pigonair.domain.reservation.repository.ReservationRepository;
import com.example.pigonair.domain.seat.entity.Seat;
import com.example.pigonair.global.config.common.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final ReservationRepository reservationRepository;
	private final PaymentRepository paymentRepository;

	@Override
	@Transactional
	public TicketResponseDto postPayProcess(PostPayRequestDto requestDto) {
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
		//결제 정보 생성
		savePayInfo(requestDto.serialNumber(), reservation);
		//좌석 이용불가 변경
		updateSeatUnAvailable(reservation);

		return new TicketResponseDto(reservation);
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

	@Transactional
	public void updateSeatUnAvailable(Reservation reservation) {
		Seat seat = reservation.getSeat();
		if (!seat.isAvailable()) {
			throw new CustomException(UNAVAILABLE_SEAT);
		}
		seat.updateIsAvailable();
	}
}
