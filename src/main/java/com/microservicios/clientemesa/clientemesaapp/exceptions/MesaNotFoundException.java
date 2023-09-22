package com.microservicios.clientemesa.clientemesaapp.exceptions;

public class MesaNotFoundException extends RuntimeException{
    public MesaNotFoundException(String message) {
        super(message);
    }
}
