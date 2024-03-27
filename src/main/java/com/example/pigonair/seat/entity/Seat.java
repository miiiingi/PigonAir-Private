package com.example.pigonair.seat.entity;

import com.example.pigonair.flight.entity.Flight;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Seat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Flight flight;

	private double price;
	private int grade;
	private boolean isAvailable;

	@Builder
	public Seat(Flight flight, double price, int grade, boolean isAvailable) {
		this.flight = flight;
		this.price = price;
		this.grade = grade;
		this.isAvailable = isAvailable;
	}
}