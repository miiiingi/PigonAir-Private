package com.example.pigonair.domain.payment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pigonair.domain.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	boolean existsByReservationId(Long id);

	List<Payment> findAllByReservationId(Long id);
}
