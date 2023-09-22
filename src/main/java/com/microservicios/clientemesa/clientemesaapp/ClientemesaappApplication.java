package com.microservicios.clientemesa.clientemesaapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Es necesaria la notación FeignCLient para permitir que se puedan hacer solicitudes fegin a esta aplicacion.
 * Además de la dependencia de feign.
 */
@SpringBootApplication
@FeignClient
public class ClientemesaappApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientemesaappApplication.class, args);
	}

}

