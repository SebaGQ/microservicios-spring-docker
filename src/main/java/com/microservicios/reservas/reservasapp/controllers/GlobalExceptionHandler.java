package com.microservicios.reservas.reservasapp.controllers;

import com.microservicios.reservas.reservasapp.dto.ErrorResponse;
import com.microservicios.reservas.reservasapp.exceptions.*;
import com.microservicios.reservas.reservasapp.feign.ExceptionMessage;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * Este es un manejador globlal de excepciones, es necesario en una aplicación de mayor tamaño
 * ya que en lugar de manejar las excepciones de manera manual en cada una de las capas,
 * se centraliza esta responsabilidad, lo que facilita el desarrollo y mejora la legibilidad y depuracion.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(FeignException ex) {
        ErrorResponse error = new ErrorResponse(ex.getStatus(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.valueOf(ex.getStatus()));
    }
    @ExceptionHandler(FeignNotFoundException.class)
    public ResponseEntity<Object> handleFeignNotFoundException(FeignNotFoundException ex, WebRequest request) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setTimestamp(LocalDateTime.now().toString());
        exceptionMessage.setStatus(HttpStatus.NOT_FOUND.value());
        exceptionMessage.setError("Not Found");
        exceptionMessage.setMessage(ex.getMessage());
        exceptionMessage.setPath(((ServletWebRequest)request).getRequest().getRequestURI());

        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }

    // HTTP 400
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MesaNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleMesaNotAvailableException(MesaNotAvailableException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // HTTP 404
    @ExceptionHandler(ReservaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReservaNotFoundException(ReservaNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClienteNotFoundException(ClienteNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MesaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMesaNotFoundException(ClienteNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
