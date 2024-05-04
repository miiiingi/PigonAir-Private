package com.example.pigonair.global.config.common.ulid;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UlidConfig {
	@Bean
	public Ulid ulid() {
		return new Ulid();
	}
}
