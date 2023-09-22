package com.microservicios.reservas.reservasapp.exceptions;

public class MesaNotFoundException extends RuntimeException{
    public MesaNotFoundException(String message) {
        super(message);
    }

    public MesaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
