package com.microservicios.clientemesa.clientemesaapp.services;

import com.microservicios.clientemesa.clientemesaapp.dto.MesaDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MesaService {

    Page<MesaDTO> getAllMesas(Pageable pageable);

    MesaDTO getMesaById(Long id);

    @Transactional
    MesaDTO saveMesa(MesaDTO mesaDto);

    @Transactional
    MesaDTO editMesa(MesaDTO mesaDto);

    @Transactional
    void deleteMesaById(Long id);
}
