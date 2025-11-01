package com.isimm.suivi_note.exceptions;

import com.isimm.suivi_note.models.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.maven.wagon.authorization.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionMapper {

    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<ErrorResponse> invalidOtpHandler(InvalidOtpException e){
        return buildResponse(HttpStatus.UNAUTHORIZED, "Invalid otp", e.getMessage());
    }

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
        return buildResponse(HttpStatus.BAD_REQUEST, "cette entité n'existe pas", ex.getMessage());
    }


    @ExceptionHandler(InvalidCredentials.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(InvalidCredentials e){
        return buildResponse(HttpStatus.UNAUTHORIZED, "Invalid credentials", e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException e){
        return buildResponse(HttpStatus.UNAUTHORIZED, "Invalid credentials", e.getMessage());
    }
    @ExceptionHandler(NoteExistException.class)
    public ResponseEntity<ErrorResponse> handleNoteExist(NoteExistException e){
        return buildResponse(HttpStatus.BAD_REQUEST, "Note existe déjà", e.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String error, String message) {
        ErrorResponse body = new ErrorResponse(status.value(), error, message);
        return ResponseEntity.status(status).body(body);
    }
}
