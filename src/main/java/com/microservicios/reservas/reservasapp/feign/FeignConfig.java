package com.microservicios.reservas.reservasapp.feign;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    /**
     * Acá se está indicando que se debe usar la clase personalizada RetrieveMessageErrorDecoder para manejar los errores feign.
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new RetreiveMessageErrorDecoder();
    }
}
