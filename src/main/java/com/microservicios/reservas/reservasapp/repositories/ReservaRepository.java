package com.microservicios.reservas.reservasapp.repositories;

import com.microservicios.reservas.reservasapp.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva,Long> {
    List<Reserva> findReservaByIdMesaAndFechaHora(Long idMesa, LocalDateTime fechaHora);
}
