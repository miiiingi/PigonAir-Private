package com.example.pigonair.seat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pigonair.flight.entity.Flight;

public interface FlightRepository extends JpaRepository<Flight,Long> {
}
