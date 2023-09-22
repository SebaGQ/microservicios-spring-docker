package com.microservicios.reservas.reservasapp.services;

import com.microservicios.reservas.reservasapp.dto.ClienteDTO;
import com.microservicios.reservas.reservasapp.dto.MesaDTO;
import com.microservicios.reservas.reservasapp.dto.ReservaDTO;
import com.microservicios.reservas.reservasapp.entities.Reserva;
import com.microservicios.reservas.reservasapp.exceptions.*;
import com.microservicios.reservas.reservasapp.feign.ClienteMesaFeignClient;
import com.microservicios.reservas.reservasapp.repositories.ReservaRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.time.LocalDateTime;
import java.util.List;


/**
 * La lógica de negocio detrás de esta capa de servicio no es tan compleja como lo sería en un escenario real
 * , aunque la estructura está bien desarrollada y sigue las mejores prácticas, ya que la finalidad del proyecto
 * no es solucionar una problemática real, sino demostrar dominio sobre el desarrollo de aplicaciones con arquitecturas
 * de microservicios y buenas prácticas.
 */
@Service
public class ReservaServiceImpl implements ReservaService {
    private final ReservaRepository reservaRepository;
    private final ClienteMesaFeignClient clienteMesaFeignClient;
    private final ModelMapper modelMapper;
    private static final String CLIENTE_MESA_SERVICE = "clienteMesaService";
    private final Logger logger = LoggerFactory.getLogger(ReservaServiceImpl.class);

    public ReservaServiceImpl(ReservaRepository reservaRepository,
                              ClienteMesaFeignClient clienteMesaFeignClient,
                              ModelMapper modelMapper) {
        this.reservaRepository = reservaRepository;
        this.clienteMesaFeignClient = clienteMesaFeignClient;

        this.modelMapper = modelMapper;
    }

    private ReservaDTO convertToDTO(Reserva reserva) {
        return modelMapper.map(reserva, ReservaDTO.class);
    }

    private Reserva convertToEntity(ReservaDTO reservaDTO) {
        return modelMapper.map(reservaDTO, Reserva.class);
    }

    @Override
    public Page<ReservaDTO> getAllReservas(Pageable pageable) {
        try {
            Page<Reserva> reservasPage = reservaRepository.findAll(pageable);
            return reservasPage.map(this::convertToDTO);

        } catch (Exception e) {
            logger.error("Error al obtener todas las reservas.", e);
            throw new ServiceException("Error al obtener todas las reservas.", e);
        }
    }

    @Override
    public ReservaDTO getReservaById(Long id) {
        Reserva reserva = reservaRepository.findById(id).orElseThrow(() ->
                new ReservaNotFoundException("No se encontró la reserva con ID: "+id));
        return convertToDTO(reserva);
    }

    @Override
    @Transactional
    public void cancelReserva(Long id) {
        try{
            if(!reservaRepository.existsById(id)) {
                throw new ReservaNotFoundException("No se encontró la reserva con ID: " + id);
            }
            reservaRepository.deleteById(id);
        } catch (ReservaNotFoundException e){
            throw e;
        } catch (Exception e){
            logger.error("La reserva no pudo ser eliminada.",e);
            throw new ServiceException("La reserva no pudo ser eliminada.",e);
        }
        logger.info("Reserva eliminada.");
        //En un escenario real lo correcto no sería eliminar la reserva, sino registrarla como cancelada.
    }

    /**
     * Este metodo tiene notaciones de resiliencia, esto se debe a que realiza llamados a otro servicio
     * , en estos casos es cuando es especialmente util Resilience4J. La transaccionalidad se pasa a las funciones
     * que se llamen en makeReserva, por lo que no es necesario marcar las otras funciones con Transactional.
     */
    @Override
    @Transactional
    @CircuitBreaker(name = CLIENTE_MESA_SERVICE, fallbackMethod = "fallbackMakeReserva")
    @Bulkhead(name = CLIENTE_MESA_SERVICE)
    @Retry(name = CLIENTE_MESA_SERVICE)
    public ReservaDTO makeReserva(ReservaDTO reservaDTO) {
        try {
            validateReserva(reservaDTO);
            logger.info("Reserva validada con exito.");

            // Se capturan las excepciones que son ignoradas por el retry.
            // Si no se hace esto cualquiera de estas excepciones activará el retry, no es lo que buscamos.
        } catch (ClienteNotFoundException | MesaNotFoundException | MesaNotAvailableException |
                 CapacidadExcedidaException | FeignNotFoundException | ReservaNotFoundException e) {
            logger.error("La reserva ingresada no es valida.", e);
            throw e;
        } catch (Exception e) {
            logger.error("La reserva no pudo realizarse.", e);
            throw new ServiceException("La reserva no pudo realizarse.", e);
        }
        return saveReserva(reservaDTO);
    }



    /**
     * Se busca designar las responsabilidades según las buenas prácticas, es decir,
     * separar en varios métodos, donde cada uno se encarga únicamente de lo suyo,
     * esto mejora la legibilidad,mantenibilidad y escalabilidad.
     */
    private void validateReserva(ReservaDTO reservaDTO){

        //Se valida la existencia del cliente y la mesa.
        validateCliente(reservaDTO.getIdCliente());
        MesaDTO mesa = validateMesa(reservaDTO.getIdMesa());
        //Al hacer que el metodo validateMesa retorne el DTO, nos ahorramos una llamada a la base de datos en la función validateCapacidadMesa.

        //Se valida que la capacidad de la mesa da abasto para el número de personas.
        validateCapacidadMesa(mesa,reservaDTO.getCantPersonas());

        //Se valida la disponibilidad de la mesa en el horario escogido.
        validateDisponibilidadMesa(reservaDTO.getIdMesa(),reservaDTO.getFechaHora());

    }

    private void validateCliente(Long idCliente){
        // Se valida la existencia del cliente
        // Hay dos validaciones para la existencia, pero esto es porque manejan escenarios distintos.
        try {
            ResponseEntity<ClienteDTO> clienteResponse = clienteMesaFeignClient.getClienteById(idCliente);
            if (clienteResponse.getBody() == null) {
                logger.error("Cliente no encontrado.");
                throw new ClienteNotFoundException("No se encontró el cliente de ID: " + idCliente);
            }
        }catch (feign.FeignException.NotFound e) {
            logger.error("Feign: Cliente no encontrado", e);
            throw new FeignNotFoundException("Feign: Cliente no encontrado", e);

        }catch (feign.FeignException e) {
            logger.error("Feign: Error al realizar la petición.", e);
                throw new FeignException("Error de feign para cliente id: " + idCliente, e);

        }catch (ClienteNotFoundException e) {
            throw e;

        }catch (Exception e) {
            logger.error("Error al validar el cliente.", e);
            throw new ServiceException("Error al validar el cliente.", e);
        }
    }

    private MesaDTO validateMesa(Long idMesa){
        // Se valida la existencia de la mesa
        // Hay dos validaciones para la existencia, pero esto es porque manejan escenarios distintos.
        try {
            ResponseEntity<MesaDTO> mesaResponse = clienteMesaFeignClient.getMesaById(idMesa);
            if(mesaResponse.getBody() != null) {
               return mesaResponse.getBody();
            }else {
                logger.error("Mesa no encontrada.");
                throw new MesaNotFoundException("No se encontró la mesa con ID: " + idMesa);
            }
        } catch (feign.FeignException e) {
            logger.error("Feign: Error al realizar la petición.", e);
            throw new FeignException("Error de feign para mesa id: "+idMesa ,e);
        } catch (MesaNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error al validar la mesa.", e);
            throw new ServiceException("Error al validar la mesa.",e);
        }
    }

    /**
     *    Se optó por una función que validara la existencia de la mesa y otra la disponibilidad horaria.
     *     Esto permité reutilizar los métodos por separado  en futuras funcionalidades.
     */
    private void validateDisponibilidadMesa(Long idMesa, LocalDateTime fechaHora){
        // Se valida la disponibilidad de la mesa.
        // Esta es una validación muy básica y no resuelve la problemática en un escenario real.
        List<Reserva> reservas = reservaRepository.findReservaByIdMesaAndFechaHora(idMesa, fechaHora);
        if(!reservas.isEmpty()) {
            logger.error("Mesa ya reservada en la hora indicada.");
            throw new MesaNotAvailableException("La mesa con ID: " + idMesa + " ya tiene una reserva para la fecha y hora solicitada.");
        }
    }

    private void validateCapacidadMesa(MesaDTO mesaDTO, Integer cantPersonas) {
        if (mesaDTO.getCapacidad() < cantPersonas) {
            logger.error("Capacidad de mesa insuficiente.");
            throw new CapacidadExcedidaException("La mesa seleccionada no tiene la capacidad suficiente para " + cantPersonas + " personas.");
        }
    }

    private ReservaDTO saveReserva(ReservaDTO reservaDTO) {
        Reserva reserva = convertToEntity(reservaDTO);
        try {
            reserva = reservaRepository.save(reserva);
            logger.info("Reserva realizada.");
            return convertToDTO(reserva);
        } catch (Exception e) {
            logger.error("Error al guardar la reserva.", e);
            throw new ServiceException("Error al guardar la reserva.", e);
        }
    }

    // FALLBACKS: Solo se está describiendo el error y lanzando una excepción, esto debido a que no es un proyecto real.
    private ReservaDTO fallbackMakeReserva(ReservaDTO reservaDTO, Throwable t) {
        logger.error("Error al hacer reserva:  "+t.getMessage());
        throw new ServiceException("Error en el proceso de reserva. Intente más tarde.", t);
    }

}