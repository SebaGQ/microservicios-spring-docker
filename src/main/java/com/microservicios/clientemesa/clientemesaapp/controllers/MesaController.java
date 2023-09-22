package com.microservicios.clientemesa.clientemesaapp.controllers;

import com.microservicios.clientemesa.clientemesaapp.dto.MesaDTO;
import com.microservicios.clientemesa.clientemesaapp.exceptions.BadRequestException;
import com.microservicios.clientemesa.clientemesaapp.services.MesaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
// En versiones más recientes es necesario indicar que el path puede ser con o sin '/', antes esto
// se hacía automáticamente, pero cambió por temas de seguridad. Esto aplica a cada @Mapping .
@RequestMapping(path = {"api/mesas","api/mesas/"})
public class MesaController {

    private final MesaService mesaService;

    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    /**
     * Este endpoint entrega todas las mesas, aplicando paginacion.
     * Esto es una buena practica ya que si hay una gran cantidad de mesas no es optimo
     * enviarlas todas, en terminos de recursos y tiempos de respuesta.
     */
    @GetMapping
    public ResponseEntity<Page<MesaDTO>> getAllMesas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idMesa") String sort,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {


        //Se construye el pageable, que tiene todos los detalles de cómo debe hacerse la paginación.
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

        //Se hace la consulta a la base de datos, usando el pageable construido anteriormente.
        Page<MesaDTO> mesaPage = mesaService.getAllMesas(pageable);

        return new ResponseEntity<>(mesaPage, HttpStatus.OK);
    }

    @GetMapping({"/{id}","{id}/"})
    public ResponseEntity<MesaDTO> getMesaById(@PathVariable Long id) {
        return ResponseEntity.ok(mesaService.getMesaById(id));
    }

    /**
     * Al usar la notacion Valid en conjunto con RequestBody le estamos diciendo que valide la entidad apenas
     * la reciba, es decir, antes de comenzar a trabajar con ella, lo que es bastante eficiente.
     * Esta validacion consiste en validar que se cumplan las notaciones como NotBlank o NotNull que estén presentes en MesaDTO.
     */
    @PostMapping
    public ResponseEntity<MesaDTO> saveMesa(@Valid @RequestBody MesaDTO mesa) {
        return new ResponseEntity<>(mesaService.saveMesa(mesa), HttpStatus.CREATED);
    }

    @PutMapping({"/{id}","{id}/"})
    public ResponseEntity<MesaDTO> editMesa(@Valid @RequestBody MesaDTO mesa, @PathVariable Long id) {
        if (!mesa.getIdMesa().equals(id)) {
            throw new BadRequestException("El id del path y dto son inconsistentes.");
        }
        return new ResponseEntity<>(mesaService.editMesa(mesa), HttpStatus.OK);
    }

    @DeleteMapping({"/{id}","{id}/"})
    public ResponseEntity<Void> deleteMesaById(@PathVariable Long id) {
        mesaService.deleteMesaById(id);
        return ResponseEntity.noContent().build();
    }

}
