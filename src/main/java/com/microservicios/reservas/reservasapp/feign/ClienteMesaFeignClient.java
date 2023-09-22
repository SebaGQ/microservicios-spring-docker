package com.microservicios.reservas.reservasapp.feign;

import com.microservicios.reservas.reservasapp.dto.ClienteDTO;
import com.microservicios.reservas.reservasapp.dto.MesaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cliente-mesa-service", url = "cliente-mesa-service:8082")
public interface ClienteMesaFeignClient {

    @GetMapping("/api/clientes/{idCliente}")
    ResponseEntity<ClienteDTO> getClienteById(@PathVariable Long idCliente);

    @GetMapping("/api/mesas/{idMesa}")
    ResponseEntity<MesaDTO> getMesaById(@PathVariable Long idMesa);
}