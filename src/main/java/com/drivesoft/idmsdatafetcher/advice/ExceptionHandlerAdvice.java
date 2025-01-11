package com.drivesoft.idmsdatafetcher.advice;

import com.drivesoft.idmsdatafetcher.dto.response.ErrorResponse;
import com.drivesoft.idmsdatafetcher.exception.CommonException;
import com.drivesoft.idmsdatafetcher.exception.InvalidRequestException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice  // Changed from @ControllerAdvice to @RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ApiResponse(responseCode = "400", description = "Validation failed",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Validation error occurred:", ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse response = ErrorResponse.builder()
                .error(HttpStatus.BAD_REQUEST.name())
                .message("Validation failed")
                .status(HttpStatus.BAD_REQUEST.value())
                .details(errors)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidFormatException.class, HttpMessageNotReadableException.class})
    @ApiResponse(responseCode = "400", description = "Invalid format",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    protected ResponseEntity<Object> handleInvalidFormatException(Exception ex) {
        log.error("Invalid format error:", ex);
        ErrorResponse response = ErrorResponse.builder()
                .error(HttpStatus.BAD_REQUEST.name())
                .message("Invalid request format")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ApiResponse(responseCode = "400", description = "Missing parameter",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    protected ResponseEntity<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error("Missing parameter error:", ex);
        ErrorResponse response = ErrorResponse.builder()
                .error(HttpStatus.BAD_REQUEST.name())
                .message("Missing required parameter: " + ex.getParameterName())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ApiResponse(responseCode = "404", description = "Entity not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("Entity not found error:", ex);
        ErrorResponse response = ErrorResponse.builder()
                .error(HttpStatus.NOT_FOUND.name())
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommonException.class)
    @ApiResponse(responseCode = "500", description = "Common error occurred",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    protected ResponseEntity<Object> handleCommonException(CommonException ex) {
        log.error("Common exception occurred:", ex);
        ErrorResponse response = ErrorResponse.builder()
                .error(ex.getHttpStatus().name())
                .message(ex.getMessage())
                .status(ex.getHttpStatus().value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    protected ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException ex) {
        log.error("Invalid request error:", ex);
        ErrorResponse response = ErrorResponse.builder()
                .error(HttpStatus.BAD_REQUEST.name())
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    protected ResponseEntity<Object> handleUnknownException(Exception ex) {
        log.error("Unexpected error occurred:", ex);
        ErrorResponse response = ErrorResponse.builder()
                .error(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .message("An unexpected error occurred")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}