package com.microservicios.clientemesa.clientemesaapp.exceptions;

public class DuplicatedClienteException extends RuntimeException{
    public DuplicatedClienteException(String message) {
        super(message);
    }
}

