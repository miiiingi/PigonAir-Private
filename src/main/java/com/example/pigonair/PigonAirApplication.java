package com.example.pigonair;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class PigonAirApplication {

	public static void main(String[] args) {
		SpringApplication.run(PigonAirApplication.class, args);
	}

}
