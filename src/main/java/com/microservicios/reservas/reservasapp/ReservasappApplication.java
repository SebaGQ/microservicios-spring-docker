package com.microservicios.reservas.reservasapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ReservasappApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservasappApplication.class, args);
	}

}
