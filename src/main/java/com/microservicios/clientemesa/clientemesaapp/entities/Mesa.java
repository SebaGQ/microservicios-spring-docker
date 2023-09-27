package com.microservicios.clientemesa.clientemesaapp.entities;

import jakarta.persistence.*;
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

    @Column(name="numero_mesa", unique = true)
    private String numeroMesa;

    @Column(name="capacidad")
    private Integer capacidad;

    @Column(name="estado")
    private String estado;
}
