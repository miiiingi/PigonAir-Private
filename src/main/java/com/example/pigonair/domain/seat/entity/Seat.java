package com.example.pigonair.domain.seat.entity;

import com.example.pigonair.domain.flight.entity.Flight;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

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

	public void seatPick(){
		if(this.isAvailable)
			this.isAvailable = false;
	}

	public void setIsAvailable() {
		if(!this.isAvailable){
			this.isAvailable = true;
		}
	}
}