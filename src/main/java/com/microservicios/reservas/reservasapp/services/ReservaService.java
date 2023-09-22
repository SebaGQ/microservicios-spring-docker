package com.microservicios.reservas.reservasapp.services;

import com.microservicios.reservas.reservasapp.dto.ReservaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservaService {

    Page<ReservaDTO> getAllReservas(Pageable pageable);

    ReservaDTO getReservaById(Long id);

    void cancelReserva(Long id);

    ReservaDTO makeReserva(ReservaDTO reservaDTO);
}
