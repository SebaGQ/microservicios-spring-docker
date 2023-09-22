package com.microservicios.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/** Basta con tener la dependencia de Gateway para que la aplicación funcione como gateway.
 * En versiones anteriores era necesaria la notación @EnableEurekaClient para que la aplicación se registrara en Eureka.
 * , pero en versiones recientes basta con tener la dependencia de Eureka Client.
 */
@SpringBootApplication
public class ApigatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApigatewayApplication.class, args);
	}
}