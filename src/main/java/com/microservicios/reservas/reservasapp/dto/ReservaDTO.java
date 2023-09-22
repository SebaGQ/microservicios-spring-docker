package com.microservicios.reservas.reservasapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservaDTO {

    @JsonProperty("idReserva")
    private Long idReserva;

    @NotNull(message = "Fecha y hora de la reserva requeridos")
    @JsonProperty("fechaHora")
    private LocalDateTime fechaHora;

    @NotNull(message = "ID de la mesa requerido")
    @JsonProperty("idMesa")
    private Long idMesa;

    @NotNull(message = "ID del cliente requerido")
    @JsonProperty("idCliente")
    private Long idCliente;

    @NotNull(message = "Cantidad de personas requerida")
    @JsonProperty("cantPersonas")
    private Integer cantPersonas;

}