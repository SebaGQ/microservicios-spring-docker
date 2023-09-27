package com.microservicios.clientemesa.clientemesaapp.services;

import com.microservicios.clientemesa.clientemesaapp.dto.ClienteDTO;
import com.microservicios.clientemesa.clientemesaapp.entities.Cliente;
import com.microservicios.clientemesa.clientemesaapp.exceptions.BadRequestException;
import com.microservicios.clientemesa.clientemesaapp.exceptions.ClienteNotFoundException;
import com.microservicios.clientemesa.clientemesaapp.exceptions.DuplicatedClienteException;
import com.microservicios.clientemesa.clientemesaapp.exceptions.ServiceException;
import com.microservicios.clientemesa.clientemesaapp.repositories.ClienteRepository;
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
 * de las operaciones, pero la estructura y el codigo siguen las practicas recomendadas.
 * La capa de servicio de la aplicacion de reservas es considerablemente mas compleja.
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
        this.modelMapper = new ModelMapper();
    }

    private ClienteDTO convertToDTO(Cliente cliente) {
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    private Cliente convertToEntity(ClienteDTO clienteDTO) {
        return modelMapper.map(clienteDTO, Cliente.class);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<ClienteDTO> getAllClientes(Pageable pageable) {
        try {
            Page<Cliente> clientesPage = clienteRepository.findAll(pageable);
            return clientesPage.map(this::convertToDTO);
        } catch (Exception e) {
            logger.error("Error al obtener todos los clientes.", e);
            throw new ServiceException("Error al obtener todos los clientes.", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteDTO getClienteById(Long id) {
        try {
            Cliente cliente = clienteRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.error("Cliente id: "+ id +" no encontrado.");
                        return new ClienteNotFoundException("Cliente id : "+ id +" no encontrado.");
                    });
            return convertToDTO(cliente);
        } catch (ClienteNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error al obtener el cliente por id: "+ id, e);
            throw new ServiceException("Error al obtener el cliente por id: "+ id, e);
        }
    }

    @Override
    @Transactional
    public ClienteDTO saveCliente(ClienteDTO clienteDto) {
        try {
            Optional<Cliente> existingCliente = clienteRepository.findByEmail(clienteDto.getEmail());
            if (existingCliente.isPresent()) {
                throw new DuplicatedClienteException("El cliente con el email " + clienteDto.getEmail() + " ya existe.");
            }
            Cliente cliente = convertToEntity(clienteDto);
            cliente = clienteRepository.save(cliente);
            logger.info("Cliente guardado con éxito.");
            return convertToDTO(cliente);
        } catch (DuplicatedClienteException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error al guardar el cliente.", e);
            throw new ServiceException("Error al guardar el cliente.", e);
        }
    }

    @Override
    @Transactional
    public ClienteDTO editCliente(ClienteDTO clienteDto) {
        Long id = clienteDto.getIdCliente();
        if (id == null) {
            throw new BadRequestException("El id no puede ser null.");
        }
        try {
            if (!clienteRepository.existsById(id)) {
                logger.error("Cliente id: "+ id+ " no encontrado.");
                throw new ClienteNotFoundException("Cliente id : "+ id +" no encontrado.");
            }
            Cliente cliente = convertToEntity(clienteDto);
            cliente = clienteRepository.save(cliente);
            logger.info("Cliente editado con éxito.");
            return convertToDTO(cliente);
        } catch (BadRequestException | ClienteNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error al editar el cliente con id: "+ id, e);
            throw new ServiceException("Error al editar el cliente con id: "+ id, e);
        }
    }

    @Override
    @Transactional
    public void deleteClienteById(Long id) {
        try {
            if (!clienteRepository.existsById(id)) {
                logger.error("Cliente id: "+ id +" no encontrado.");
                throw new ClienteNotFoundException("Cliente id: "+ id+ " no encontrado.");
            }
            clienteRepository.deleteById(id);
            logger.info("Cliente eliminado con éxito.");
        } catch (ClienteNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error al eliminar el cliente con id: "+ id, e);
            throw new ServiceException("Error al eliminar el cliente con id: "+ id, e);
        }
    }
}