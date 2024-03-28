package com.example.pigonair.payment.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.pigonair.global.error.ReservationNotFoundException;
import com.example.pigonair.payment.dto.PaymentRequestDto.PostPayRequestDto;
import com.example.pigonair.payment.dto.PaymentResponseDto.TicketResponseDto;
import com.example.pigonair.payment.entity.Payment;
import com.example.pigonair.payment.repository.PaymentRepository;
import com.example.pigonair.reservation.entity.Reservation;
import com.example.pigonair.reservation.repository.ReservationRepository;

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
			throw new ReservationNotFoundException("해당 예약을 찾을 수 없습니다");
		}
		Reservation reservation = optionalReservation.get();

		//결제 금액과 좌석 가격이 같은지 확인
		if (reservation.getSeat().getPrice() != requestDto.paidAmount()) {
			// 결제 금액 불일치 에러
			throw new IllegalArgumentException("결제 금액과 선택한 좌석의 가격이 일치하지 않습니다.");
		}

		//결제 후 결제 여부 변경
		reservation.updateIsPayment();
		savePayInfo(requestDto.serialNumber(), reservation);

		return new TicketResponseDto(reservation);
	}

	@Override
	@Transactional
	public void savePayInfo(String serialNumber, Reservation reservation) {
		Payment payment = new Payment(reservation, serialNumber);
		paymentRepository.save(payment);
	}
}
