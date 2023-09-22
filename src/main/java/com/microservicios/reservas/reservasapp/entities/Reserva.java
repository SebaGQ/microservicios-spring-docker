package com.microservicios.reservas.reservasapp.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="reservas")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva", unique = true, nullable = false)
    private Long idReserva;

    @NotNull(message = "Fecha y hora de la reserva requeridos")
    @Column(name="fecha_hora")
    private LocalDateTime fechaHora;

    @NotNull(message = "ID de la mesa requerido")
    @Column(name="id_mesa")
    private Long idMesa;

    @NotBlank(message = "ID del cliente requerido")
    @Column(name="nombre_cliente")
    private Long idCliente;

    @NotNull(message = "Cantidad de personas requerida")
    @Column(name= "cantPersonas")
    private Integer cantPersonas;

}
