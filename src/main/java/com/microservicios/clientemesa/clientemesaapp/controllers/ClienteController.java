package com.microservicios.clientemesa.clientemesaapp.controllers;

import com.microservicios.clientemesa.clientemesaapp.dto.ClienteDTO;
import com.microservicios.clientemesa.clientemesaapp.exceptions.BadRequestException;
import com.microservicios.clientemesa.clientemesaapp.services.ClienteService;
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
@RequestMapping(path = {"api/clientes","api/clientes/"})
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * Este endpoint entrega todos los clientes, aplicando paginacion.
     * Esto es una buena práctica ya que si hay una gran cantidad de clientes no es optimo
     * enviarlos todos, en terminos de recursos y tiempos de respuesta.
     */
    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> getAllClientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idCliente") String sort,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {

        //Se construye el pageable, que tiene todos los detalles de cómo debe hacerse la paginación.
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

        //Se hace la consulta a la base de datos, usando el pageable construido anteriormente.
        Page<ClienteDTO> clientePage = clienteService.getAllClientes(pageable);

        return new ResponseEntity<>(clientePage, HttpStatus.OK);
    }

    @GetMapping({"/{id}","{id}/"})
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.getClienteById(id));
    }

    /**
     * Al usar la notacion Valid en conjunto con RequestBody le estamos diciendo que valide la entidad apenas
     * la reciba, es decir, antes de comenzar a trabajar con ella, lo que es bastante eficiente.
     * Esta validacion consiste en validar que se cumplan las notaciones como NotBlank o NotNull que estén presentes en ClienteDTO.
     */
    @PostMapping
    public ResponseEntity<ClienteDTO> saveCliente(@Valid @RequestBody ClienteDTO cliente) {
        return new ResponseEntity<>(clienteService.saveCliente(cliente), HttpStatus.CREATED);
    }

    @PutMapping({"/{id}","{id}/"})
    public ResponseEntity<ClienteDTO> editCliente(@Valid @RequestBody ClienteDTO cliente, @PathVariable Long id) {
        if (!cliente.getIdCliente().equals(id)) {
            throw new BadRequestException("ids inconsistentes.");
        }
        return new ResponseEntity<>(clienteService.editCliente(cliente), HttpStatus.OK);
    }

    @DeleteMapping({"/{id}","{id}/"})
    public ResponseEntity<Void> deleteClienteById(@PathVariable Long id) {
        clienteService.deleteClienteById(id);
        return ResponseEntity.noContent().build();
    }
}