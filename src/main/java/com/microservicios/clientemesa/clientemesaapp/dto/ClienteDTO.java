package com.microservicios.clientemesa.clientemesaapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClienteDTO {

    @JsonProperty("idCliente")
    private Long idCliente;

    @NotBlank(message = "NOMBRE INVALIDO")
    @JsonProperty("nombreCliente")
    private String nombreCliente;

    @Email(message = "EMAIL INVALIDO")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "TELEFONO INVALIDO")
    @JsonProperty("telefono")
    private String telefono;

}

