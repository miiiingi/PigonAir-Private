package com.example.pigonair.seat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pigonair.seat.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
	List<Seat> findAllByflightId(Long flightId);
}
