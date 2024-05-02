package com.example.pigonair.global.config.iamport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.siot.IamportRestClient.IamportClient;

@Configuration
public class AppConfig {
	@Value("${iamport.apiKey}")
	private String apiKey;

	@Value("${iamport.secretKey}")
	private String secretKey;

	@Bean
	public IamportClient iamportClient() {
		return new IamportClient(apiKey, secretKey);
	}
}
