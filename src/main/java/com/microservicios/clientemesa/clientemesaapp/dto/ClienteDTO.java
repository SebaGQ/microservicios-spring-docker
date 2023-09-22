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

    @NotBlank(message = "INVALID NAME VALUE")
    @JsonProperty("nombreCliente")
    private String nombreCliente;

    @Email(message = "INVALID EMAIL")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "INVALID PHONE NUMBER")
    @JsonProperty("telefono")
    private String telefono;

}

