package com.microservicios.clientemesa.clientemesaapp.repositories;

import com.microservicios.clientemesa.clientemesaapp.entities.Mesa;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MesaRepository extends JpaRepository<Mesa,Long> {
    Optional<Mesa> findByNumeroMesa(String numeroMesa);

}
