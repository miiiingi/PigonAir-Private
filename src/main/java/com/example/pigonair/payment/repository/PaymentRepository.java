package com.example.pigonair.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pigonair.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
