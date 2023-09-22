package com.microservicios.clientemesa.clientemesaapp.repositories;

import com.microservicios.clientemesa.clientemesaapp.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    Optional<Cliente> findByEmail(String email);
}
