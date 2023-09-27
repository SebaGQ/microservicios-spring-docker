package com.microservicios.clientemesa.clientemesaapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MesaDTO {

    @JsonProperty("idMesa")
    private Long idMesa;

    @NotBlank(message = "NUMERO DE MESA INVALIDO")
    @JsonProperty("numeroMesa")
    private String numeroMesa;

    @NotNull(message = "CAPACIDAD MESA INVALIDA")
    @JsonProperty("capacidad")
    private Integer capacidad;

    @NotBlank(message = "ESTADO DE MESA INVALIDO")
    @JsonProperty("estado")
    private String estado;

}
