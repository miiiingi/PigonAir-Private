package com.example.pigonair.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pigonair.reservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
