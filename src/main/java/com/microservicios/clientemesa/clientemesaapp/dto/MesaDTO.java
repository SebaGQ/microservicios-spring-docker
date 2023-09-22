package com.microservicios.clientemesa.clientemesaapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MesaDTO {

    @JsonProperty("idMesa")
    private Long idMesa;

    @JsonProperty("numeroMesa")
    private String numeroMesa;

    @JsonProperty("capacidad")
    private Integer capacidad;

    @JsonProperty("estado")
    private String estado;
}
