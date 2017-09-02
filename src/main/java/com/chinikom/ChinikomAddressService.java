package com.chinikom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*
 * This is the primary Spring Boot application class. It configures Spring Boot, JPA, Swagger and
 * other dependent Spring modules.
 */

@SpringBootApplication
// @SuppressWarnings("deprecation")
@EnableAutoConfiguration
// Sprint Boot Automatic Configuration
@ComponentScan(basePackages = "com.chinikom")
@EnableJpaRepositories("com.chinikom.dao.jpa")
// To segregate MongoDB and JPA repositories. Otherwise not needed.
// @EnableSwagger2
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class ChinikomAddressService {
	static Class<ChinikomAddressService> applicationClass = ChinikomAddressService.class;

	public static void main(String[] args) {
		SpringApplication.run(ChinikomAddressService.class, args);
	}
}
