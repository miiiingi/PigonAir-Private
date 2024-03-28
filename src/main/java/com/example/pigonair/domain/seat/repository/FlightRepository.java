package com.example.pigonair.domain.seat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pigonair.domain.flight.entity.Flight;

public interface FlightRepository extends JpaRepository<Flight,Long> {
}
