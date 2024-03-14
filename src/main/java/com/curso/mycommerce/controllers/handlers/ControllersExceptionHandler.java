package com.curso.mycommerce.controllers.handlers;

import com.curso.mycommerce.dto.CustomError;
import com.curso.mycommerce.dto.FieldMessege;
import com.curso.mycommerce.dto.ValidateError;
import com.curso.mycommerce.services.exception.DatabaseException;
import com.curso.mycommerce.services.exception.ExceptionNotFound;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidateError err = new ValidateError(Instant.now(), status.value(), "Error de dados", request.getRequestURI());
         for(FieldError f : e.getBindingResult().getFieldErrors()){
             err.addError(f.getField(),f.getDefaultMessage());
         }
        return  ResponseEntity.status(status).body(err);
    }
}
