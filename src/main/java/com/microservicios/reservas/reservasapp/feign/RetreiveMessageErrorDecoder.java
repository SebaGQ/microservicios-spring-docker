package com.microservicios.reservas.reservasapp.feign;

import com.microservicios.reservas.reservasapp.exceptions.FeignBadRequestException;
import com.microservicios.reservas.reservasapp.exceptions.FeignException;
import com.microservicios.reservas.reservasapp.exceptions.FeignNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.InputStream;
import java.io.IOException;

/**
 * Esta clase se implementa porque las excepciones en la petición feign realizada a otro servicio
 * no se pasan automáticamente como excepción en la respuesta. Por ello se debe tener un ErrorDecoder
 * personalizado que permita devolver la misma excepción que se obtiene en la petición feign.
 */
public class RetreiveMessageErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        ExceptionMessage message = null;
        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, ExceptionMessage.class);
        } catch (IOException e) {
            return new FeignException(e.getMessage(), 500);
        }

        if (message == null) {
            return errorDecoder.decode(methodKey, response);
        }

        return switch (response.status()) {
            case 400 -> new FeignBadRequestException(message.getMessage() != null ? message.getMessage() : "Bad Request");
            case 404 -> new FeignNotFoundException(message.getMessage() != null ? message.getMessage() : "Not found");
            default -> errorDecoder.decode(methodKey, response);
        };
    }
}