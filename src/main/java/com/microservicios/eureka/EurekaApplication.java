package com.microservicios.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
Basta con la notacion @EnableEurekaServer para que nuestra aplicacion funcione como un servidor Eureka.
 La dependencia de eureka server en el pom.xml .
 Y un par de configuraciones que estan en el application.yml .
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaApplication.class, args);
	}

}
