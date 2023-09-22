package com.microservicios.reservas.reservasapp.exceptions;

public class FeignBadRequestException extends RuntimeException {
    public FeignBadRequestException(String message) {
        super(message);
    }
    public FeignBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}

