package com.microservicios.clientemesa.clientemesaapp.exceptions;

public class DuplicatedMesaException extends RuntimeException{
    public DuplicatedMesaException(String message) {
        super(message);
    }
}
