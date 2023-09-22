package com.microservicios.reservas.reservasapp.exceptions;


public class ReservaNotFoundException extends RuntimeException {
    public ReservaNotFoundException(String message) {
        super(message);
    }
}