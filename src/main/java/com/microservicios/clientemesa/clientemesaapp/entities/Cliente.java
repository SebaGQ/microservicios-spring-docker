package com.microservicios.clientemesa.clientemesaapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="clientes")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente", unique = true, nullable = false)
    private Long idCliente;

    @Column(name="nombre_cliente")
    private String nombreCliente;

    @Column(name="email", unique = true)
    private String email;

    @Column(name="telefono")
    private String telefono;
}
