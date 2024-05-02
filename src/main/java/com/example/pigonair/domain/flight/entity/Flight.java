package com.example.pigonair.domain.flight.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Flight {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime departureTime;
	private LocalDateTime arrivalTime;
	private Airport origin;
	private Airport destination;

	@Builder
	public Flight(LocalDateTime departureTime, LocalDateTime arrivalTime, Airport origin, Airport destination) {
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.origin = origin;
		this.destination = destination;
	}
}