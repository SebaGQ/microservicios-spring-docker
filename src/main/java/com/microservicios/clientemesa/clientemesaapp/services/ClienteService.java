package com.microservicios.clientemesa.clientemesaapp.services;

import com.microservicios.clientemesa.clientemesaapp.dto.ClienteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClienteService {


    Page<ClienteDTO> getAllClientes(Pageable pageable);

    ClienteDTO getClienteById(Long id);

    @Transactional
    ClienteDTO saveCliente(ClienteDTO clienteDto);

    @Transactional
    ClienteDTO editCliente(ClienteDTO clienteDto);

    @Transactional
    void deleteClienteById(Long id);
}
