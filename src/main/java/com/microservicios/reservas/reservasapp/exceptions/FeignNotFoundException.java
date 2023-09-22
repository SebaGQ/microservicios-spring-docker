package com.microservicios.reservas.reservasapp.exceptions;

public class FeignNotFoundException extends RuntimeException {
    public FeignNotFoundException(String message) {
        super(message);
    }

    public FeignNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
