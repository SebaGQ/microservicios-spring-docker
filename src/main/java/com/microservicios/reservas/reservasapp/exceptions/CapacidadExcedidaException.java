package com.microservicios.reservas.reservasapp.exceptions;

public class CapacidadExcedidaException extends RuntimeException{
    public CapacidadExcedidaException(String message) {
        super(message);
    }
}