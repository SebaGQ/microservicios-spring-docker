package com.microservicios.clientemesa.clientemesaapp.services;

import com.microservicios.clientemesa.clientemesaapp.dto.MesaDTO;
import com.microservicios.clientemesa.clientemesaapp.entities.Mesa;
import com.microservicios.clientemesa.clientemesaapp.exceptions.BadRequestException;
import com.microservicios.clientemesa.clientemesaapp.exceptions.DuplicatedMesaException;
import com.microservicios.clientemesa.clientemesaapp.exceptions.MesaNotFoundException;
import com.microservicios.clientemesa.clientemesaapp.exceptions.ServiceException;
import com.microservicios.clientemesa.clientemesaapp.repositories.MesaRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * La capa de servicio de Mesa y Cliente son basicas en cuanto a la complejidad
 * de las operaciones, pero la estructura, el codigo y el manejo de errores siguen las practicas recomendadas.
 * La capa de servicio de la aplicacion de Reservas es mas compleja.
 */
@Service
public class MesaServiceImpl implements MesaService {

    private final ModelMapper modelMapper;
    private final MesaRepository mesaRepository;
    private final Logger logger = LoggerFactory.getLogger(MesaServiceImpl.class);

    public MesaServiceImpl(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
        this.modelMapper = new ModelMapper();
    }

    private MesaDTO convertToDTO(Mesa mesa) {
        return modelMapper.map(mesa, MesaDTO.class);
    }

    private Mesa convertToEntity(MesaDTO mesaDTO) {
        return modelMapper.map(mesaDTO, Mesa.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MesaDTO> getAllMesas(Pageable pageable) {
        try {
            Page<Mesa> mesasPage = mesaRepository.findAll(pageable);
            return mesasPage.map(this::convertToDTO);

        } catch (Exception e) {
            logger.error("Error al obtener todas las mesas.", e);
            throw new ServiceException("Error al obtener todas las mesas.", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MesaDTO getMesaById(Long id) {
        try {
            Mesa mesa = mesaRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.error("Mesa id: "+ id +" no encontrada.");
                        return new MesaNotFoundException("Mesa id: "+ id +" no encontrada.");
                    });
            return convertToDTO(mesa);
        } catch (MesaNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error al obtener la mesa por id: "+id, e);
            throw new ServiceException("Error al obtener la mesa por id: "+id, e);
        }
    }

    @Override
    @Transactional
    public MesaDTO saveMesa(MesaDTO mesaDto) {
        try {
            // Verificar si la mesa ya existe en la base de datos por número de mesa
            Optional<Mesa> existingMesa = mesaRepository.findByNumeroMesa(mesaDto.getNumeroMesa());
            if (existingMesa.isPresent()) {
                throw new DuplicatedMesaException("La mesa con el número " + mesaDto.getNumeroMesa() + " ya existe.");
            }
            Mesa mesa = convertToEntity(mesaDto);
            mesa = mesaRepository.save(mesa);
            logger.info("Mesa guardada con éxito.");
            return convertToDTO(mesa);

        } catch (DuplicatedMesaException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error al guardar la mesa.", e);
            throw new ServiceException("Error al guardar la mesa.", e);
        }
    }

    @Override
    @Transactional
    public MesaDTO editMesa(MesaDTO mesaDto) {
        Long id = mesaDto.getIdMesa();
        if (id == null) {
            throw new BadRequestException("El id no puede ser null.");
        } else if (!mesaRepository.existsById(id)) {
            logger.error("Mesa id: "+ id +" no encontrada.");
            throw new MesaNotFoundException("Mesa id: "+ id +" no encontrada.");
        }
        try {
            Mesa mesa = convertToEntity(mesaDto);
            return convertToDTO(mesaRepository.save(mesa));

        } catch (BadRequestException | MesaNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error al editar la mesa con id: "+id, e);
            throw new ServiceException("Error al editar la mesa con id: "+id, e);
        }
    }

    @Override
    @Transactional
    public void deleteMesaById(Long id) {
        try {
            if (!mesaRepository.existsById(id)) {
                logger.error("Mesa id: "+ id +" no encontrada.");
                throw new MesaNotFoundException("Mesa id: "+ id +" no encontrada.");
            }
            mesaRepository.deleteById(id);

        } catch (MesaNotFoundException e) {
            throw e;
        }catch (Exception e) {
            logger.error("Error al eliminar la mesa con id: "+ id, e);
            throw new ServiceException("Error al eliminar la mesa con id: "+ id, e);
        }
    }
}