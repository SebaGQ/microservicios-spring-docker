package com.microservicios.reservas.reservasapp.controllers;

import com.microservicios.reservas.reservasapp.dto.ReservaDTO;
import com.microservicios.reservas.reservasapp.services.ReservaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
// En versiones más recientes es necesario indicar que el path puede ser con o sin '/', antes esto
// se hacía automáticamente, pero cambió por temas de seguridad. Esto aplica a cada @Mapping .
@RequestMapping(path = {"api/reservas","api/reservas/"})
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    /**
     * Este endpoint entrega todas las reservas, aplicando paginacion.
     * Esto es una buena practica ya que si hay una gran cantidad de reservas no es optimo
     * enviarlas todas, en terminos de recursos y tiempos de respuesta.
     */
    @GetMapping
    public ResponseEntity<Page<ReservaDTO>> getAllReservas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idReserva") String sort,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {

        //Se construye el pageable, que tiene todos los detalles de cómo debe hacerse la paginación.
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

        //Se hace la consulta a la base de datos, usando el pageable construido anteriormente.
        Page<ReservaDTO> reservaPage = reservaService.getAllReservas(pageable);

        return new ResponseEntity<>(reservaPage, HttpStatus.OK);
    }

    @GetMapping({"/{id}","{id}/"})
    public ResponseEntity<ReservaDTO> getReservaById(@PathVariable Long id) {
        ReservaDTO reservaDTO = reservaService.getReservaById(id);
        return ResponseEntity.ok(reservaDTO);
    }

     /**
     * Al usar la notacion Valid en conjunto con RequestBody le estamos diciendo que valide la entidad apenas
     * la reciba, es decir, antes de comenzar a trabajar con ella, lo que es bastante eficiente.
     * Esta validacion consiste en validar que se cumplan las notaciones como NotBlank o NotNull que estén presentes en ReservaDTO.
     */
    @PostMapping
    public ResponseEntity<ReservaDTO> createReserva(@Valid @RequestBody ReservaDTO reservaDTO) {
        ReservaDTO createdReserva = reservaService.makeReserva(reservaDTO);
        return new ResponseEntity<>(createdReserva, HttpStatus.CREATED);
    }

    @DeleteMapping({"/{id}","{id}/"})
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        reservaService.cancelReserva(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
