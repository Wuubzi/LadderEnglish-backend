package com.LadderEnglish.backend.Exceptions;

import com.LadderEnglish.backend.DTO.Response.ErrorResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEntityNotFoundException(EntityNotFoundException e, WebRequest request) {
        return buildErrorResponse(e, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException e, WebRequest request) {
        return buildErrorResponse(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentialsException(BadCredentialsException e, WebRequest request) {
        return buildErrorResponse(e, request, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception e, WebRequest request) {
        return buildErrorResponse(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(Exception e, WebRequest request, HttpStatus status) {
        ErrorResponseDTO error = new ErrorResponseDTO();
        error.setStatus(status.value());
        error.setException(e.getClass().getSimpleName());
        error.setMessage(e.getMessage());
        error.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        error.setUrl(request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(error, status);
    }

}
