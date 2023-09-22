package com.microservicios.clientemesa.clientemesaapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {
    private int status;
    private String message;

    public ErrorDTO(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
