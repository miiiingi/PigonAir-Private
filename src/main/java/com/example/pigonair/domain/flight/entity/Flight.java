package com.example.pigonair.domain.flight.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "flight",	indexes = {
	@Index(columnList = "departureTime"),
	@Index(columnList = "origin, destination")})
public class Flight {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime departureTime;
	private LocalDateTime arrivalTime;
	@Enumerated(EnumType.STRING)
	private Airport origin;
	@Enumerated(EnumType.STRING)
	private Airport destination;

	@Builder
	public Flight(LocalDateTime departureTime, LocalDateTime arrivalTime, Airport origin, Airport destination) {
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.origin = origin;
		this.destination = destination;
	}
}