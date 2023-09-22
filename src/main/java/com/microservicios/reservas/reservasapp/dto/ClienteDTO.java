package com.microservicios.reservas.reservasapp.dto;

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

    @NotBlank(message = "Nombre de cliente invalido")
    @JsonProperty("nombreCliente")
    private String nombreCliente;

    @Email(message = "Email invalido")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Numero de telefono invalido")
    @JsonProperty("telefono")
    private String telefono;

}