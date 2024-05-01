package com.example.pigonair.domain.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pigonair.domain.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
