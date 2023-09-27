package com.microservicios.clientemesa.clientemesaapp.controllers;

import com.microservicios.clientemesa.clientemesaapp.dto.ErrorDTO;
import com.microservicios.clientemesa.clientemesaapp.exceptions.ClienteNotFoundException;
import com.microservicios.clientemesa.clientemesaapp.exceptions.MesaNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Este es un manejador globlal de excepciones, es necesario en una aplicación de mayor tamaño
 * ya que en lugar de manejar las excepciones de manera manual en cada una de las capas,
 * se centraliza esta responsabilidad, lo que facilita el desarrollo y mejora la legibilidad.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // HTTP 404
    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleClienteNotFoundException(ClienteNotFoundException ex) {
        ErrorDTO error = new ErrorDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MesaNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleMesaNotFoundException(MesaNotFoundException ex) {
        ErrorDTO error = new ErrorDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // HTTP 400
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintViolationException(ConstraintViolationException ex) {
        ErrorDTO error = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
