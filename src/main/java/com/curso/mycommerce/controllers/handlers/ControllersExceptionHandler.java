package com.curso.mycommerce.controllers.handlers;

import com.curso.mycommerce.dto.CustomError;
import com.curso.mycommerce.services.exception.DatabaseException;
import com.curso.mycommerce.services.exception.ExceptionNotFound;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllersExceptionHandler {

    @ExceptionHandler(ExceptionNotFound.class)
    public ResponseEntity<CustomError> exceptionNotFound(ExceptionNotFound e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError err = new CustomError(
                Instant.now(),
                status.value(),
                e.getMessage(),
                request.getRequestURI());
        return  ResponseEntity.status(status).body(err);
    }
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> databaseException(DatabaseException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError err = new CustomError(
                Instant.now(),
                status.value(),
                e.getMessage(),
                request.getRequestURI());
        return  ResponseEntity.status(status).body(err);
    }
}
