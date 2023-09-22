package com.microservicios.clientemesa.clientemesaapp.exceptions;

public class ClienteNotFoundException extends RuntimeException{
    public ClienteNotFoundException(String message) {
        super(message);
    }
}
