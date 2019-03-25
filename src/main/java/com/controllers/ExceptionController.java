package com.controllers;

import com.exception.NotValidInformationException;
import com.wire.ResponseMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;


@RestControllerAdvice
public class ExceptionController {


    @ExceptionHandler(NotValidInformationException.class)
    public ResponseEntity<ResponseMessages> notFoundException(NotValidInformationException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(new ResponseMessages(HttpStatus.NOT_FOUND.toString(), ex.getMessage()));
    }


    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ResponseMessages> handleResourceNotFoundException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new ResponseMessages(HttpStatus.BAD_REQUEST.toString(), e.getMessage()));
    }

//    -----Alternative Exception catcher---
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NotValidInformationException.class)
//    public void notFoundException() {
//    }
}
