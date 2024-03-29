package com.example.pigonair.domain.flight.entity;
public enum Airport {
	JFK("John F. Kennedy International Airport"),
	LAX("Los Angeles International Airport"),
	ORD("O'Hare International Airport"),
	ATL("Hartsfield-Jackson Atlanta International Airport"),
	DXB("Dubai International Airport"),
	LHR("Heathrow Airport"),
	CDG("Charles de Gaulle Airport"),
	AMS("Amsterdam Airport Schiphol"),
	SIN("Singapore Changi Airport"),
	PEK("Beijing Capital International Airport"),
	ICN("Incheon International Airport"),
	SYD("Sydney Airport"),
	FRA("Frankfurt Airport"),
	DEN("Denver International Airport"),
	SFO("San Francisco International Airport"),
	HND("Tokyo Haneda Airport"),
	GMP("Gimpo International Airport"),
	CJJ("Cheongju International Airport"),
	CJU("Jeju International Airport");

	private final String fullName;

	Airport(String fullName) {
		this.fullName = fullName;
	}

	public String getFullName() {
		return fullName;
	}
}
