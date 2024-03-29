package com.example.pigonair.domain.seat.entity;

import com.example.pigonair.domain.flight.entity.Flight;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Seat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Flight flight;

	private Long price;
	private int grade;
	private boolean isAvailable;

	@Builder
	public Seat(Flight flight, Long price, int grade, boolean isAvailable) {
		this.flight = flight;
		this.price = price;
		this.grade = grade;
		this.isAvailable = isAvailable;
	}

	public void updateIsAvailable() {
		this.isAvailable = false;
	}
}