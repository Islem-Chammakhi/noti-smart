package com.isimm.suivi_note.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.isimm.suivi_note.exceptions.InvalidExcelStructureException;
import com.isimm.suivi_note.exceptions.InvalidFileNameException;
import com.isimm.suivi_note.models.ErrorResponse;

import jakarta.persistence.EntityNotFoundException;


@RestControllerAdvice
public class GlobalExceptionHandler {
     @ExceptionHandler(InvalidFileNameException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFileName(InvalidFileNameException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Invalid File Name", ex.getMessage());
    }

    @ExceptionHandler(InvalidExcelStructureException.class)
    public ResponseEntity<ErrorResponse> handleInvalidExcel(InvalidExcelStructureException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Invalid Excel Structure", ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "cette entité n'existe pas ", ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String error, String message) {
        ErrorResponse body = new ErrorResponse(status.value(), error, message);
        return ResponseEntity.status(status).body(body);
    }
}
