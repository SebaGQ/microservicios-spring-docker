package com.microservicios.clientemesa.clientemesaapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="mesas")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mesa", unique = true, nullable = false)
    private Long idMesa;

    @NotBlank(message = "Número de mesa inválido")
    @Column(name="numero_mesa", unique = true)
    private String numeroMesa;

    @NotNull(message = "Capacidad de mesa invalida")
    @Column(name="capacidad")
    private Integer capacidad;

    @NotBlank(message = "Estado de mesa inválido")
    @Column(name="estado")
    private String estado;
}
