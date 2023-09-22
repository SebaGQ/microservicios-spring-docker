package com.microservicios.reservas.reservasapp.exceptions;

public class FeignException extends RuntimeException {

    private final int status;

    public FeignException(String message, int status) {
        super(message);
        this.status = status;
    }

    public FeignException(String message, Throwable cause, int status) {
        super(message, cause);
        this.status = status;
    }

    public FeignException(String message, Throwable cause) {
        super(message, cause);
        this.status = 0;
    }

    public int getStatus() {
        return status;
    }
}
