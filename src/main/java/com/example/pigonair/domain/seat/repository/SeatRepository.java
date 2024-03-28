package com.example.pigonair.domain.seat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pigonair.domain.seat.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
	List<Seat> findAllByflightId(Long flightId);
}
