package com.microservicios.reservas.reservasapp.exceptions;

public class MesaNotAvailableException extends RuntimeException{
    public MesaNotAvailableException(String message) {
        super(message);
    }
}