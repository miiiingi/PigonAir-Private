package com.example.pigonair.Flight;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.pigonair.domain.flight.entity.Airport;
import com.example.pigonair.domain.flight.entity.Flight;
import com.example.pigonair.domain.flight.repository.FlightRepository;

@SpringBootTest
public class FlightDataGeneratorTest {

	@Autowired
	private FlightRepository flightRepository;
	private final Random random = new Random();

	private void generateRandomFlightData(int numberOfFlights) {
		List<Flight> flights = new ArrayList<>();
		for (int i = 0; i < numberOfFlights; i++) {
			// Generate random data for fields
			LocalDateTime departureTime = generateRandomDateTime();
			// Add a random duration to the departure time to get the arrival time
			LocalDateTime arrivalTime = departureTime.plusHours(random.nextInt(12) + 1); // Adding random hours between 1 and 12

			// Generate random Airport codes or use predefined ones
			String originCode = generateRandomAirportCode();
			String destinationCode = generateRandomAirportCode();

			// Create Flight entity instance
			Flight flight = Flight.builder()
				.departureTime(departureTime)
				.arrivalTime(arrivalTime)
				.origin(Airport.valueOf(originCode))
				.destination(Airport.valueOf(destinationCode))
				.build();

			flights.add(flight);
		}

		// Save all flights to the database
		flightRepository.saveAll(flights);
	}

	private LocalDateTime generateRandomDateTime() {
		LocalDateTime start = LocalDateTime.now();
		LocalDateTime end = start.plusDays(30);
		long startEpochSecond = start.toEpochSecond(java.time.ZoneOffset.UTC);
		long endEpochSecond = end.toEpochSecond(java.time.ZoneOffset.UTC);
		long randomEpochSecond = ThreadLocalRandom.current().nextLong(startEpochSecond, endEpochSecond);
		return LocalDateTime.ofEpochSecond(randomEpochSecond, 0, java.time.ZoneOffset.UTC);
	}

	private String generateRandomAirportCode() {
		// Example: Generate random Airport code, you can adjust this method according to your Airport enum or database structure
		String[] airportCodes = {"JFK", "LAX", "ORD", "ATL", "DXB", "LHR", "CDG", "AMS", "SIN", "PEK", "ICN", "SYD", "FRA", "DEN", "SFO", "HND", "GMP", "CJJ", "CJU"};
		return airportCodes[random.nextInt(airportCodes.length)];
	}

	@Test
	public void generateRandomFlights() {
		int numberOfFlights = 100;
		int flightSizeBefore = flightRepository.findAll().size();
		generateRandomFlightData(numberOfFlights);
		int flightSizeAfter = flightRepository.findAll().size();
		assertThat(flightSizeAfter - flightSizeBefore).isEqualTo(numberOfFlights);
	}
}
