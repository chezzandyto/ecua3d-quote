package com.ecua3d.quote.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

//PROGRAMAR EVENTOS
@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity runtimeExceptionHandler(EntityExistsException e){
       //System.out.println(e.getMessage());
        log.error("ERROR ENTIDAD EXISTENTE error: {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(),e.getHttpStatus());
    }
    @ExceptionHandler(EntityNoExistsException.class)
    public ResponseEntity runtimeNoExceptionHandler(EntityNoExistsException e){
        //System.out.println(e.getMessage());
        log.error("ERROR ENTIDAD NO EXISTENTE error: {} ", e.getMessage());
        return new ResponseEntity<>(e.getMessage(),e.getHttpStatus());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity errorEmailHandler(DataIntegrityViolationException e){
        //System.out.println(e.getMessage());
        log.error("ERROR DE EMAIL error: {} causa: {}", e.getMessage(), e.getCause().getMessage());
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
    //MethodArgumentNotValidException CONTROLA TODAS LAS VALIDACIONES DE DATOS EN LOS DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> errorFieldsHandler(MethodArgumentNotValidException eList){
        //System.out.println(e.getMessage());
        List<String> aux = new ArrayList<>();
        for (FieldError each : eList.getFieldErrors()) {
            aux.add("Error en " + each.getField());
            log.info("error en {}, rejected: {} ", each.getField() , each.getRejectedValue());
        }
        //log.error("ERROR DE CAMPO error: {} causa: {}", eList.getMessage(), eList.getCause().getMessage());
        return new ResponseEntity<List<String>>(aux,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NewUSerNoExists.class)
    public ResponseEntity newUSerHandler(NewUSerNoExists e){
        log.error("NO SE PUDO CREAR NOMBRE DE USUARIO error: {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(),e.getHttpStatus());
    }
    @ExceptionHandler(TaskHasNotProductsException.class)
    public ResponseEntity hasNotProductExceptionHandler(TaskHasNotProductsException e){
        //System.out.println(e.getMessage());
        log.error("ERROR SE REQUIEREN PRODUCTOS error: {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(),e.getHttpStatus());
    }

}
