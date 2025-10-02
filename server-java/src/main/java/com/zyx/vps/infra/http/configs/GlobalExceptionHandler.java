package com.zyx.vps.infra.http.configs;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.zyx.vps.application.dto.exceptions.ResponseExceptionDTO;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseExceptionDTO> handleValidationExceptions(
      MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    HttpStatus status = HttpStatus.BAD_REQUEST;
    ResponseExceptionDTO response = new ResponseExceptionDTO();

    response.setFields(errors);
    response.setStatus(status.value());
    response.setMessage(ex.getBindingResult().getAllErrors().size() == 1 ? "Invalid field" : "Invalid fields");

    return ResponseEntity.status(status).body(
        response);
  }
}
