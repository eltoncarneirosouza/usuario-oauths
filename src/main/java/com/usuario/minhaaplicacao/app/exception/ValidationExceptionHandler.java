package com.usuario.minhaaplicacao.app.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidationExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(
        MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    return createErrorResponse(
          HttpStatus.BAD_REQUEST,
          "Erro de validação",
          "Existem campos inválidos na requisição",
          errors
    );
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ValidationErrorResponse> handleDataIntegrityViolation(
        DataIntegrityViolationException ex) {
    Map<String, String> errors = new HashMap<>();

    if (ex.getMessage().contains("email")) {
      errors.put("email", "Email já cadastrado");
    } else if (ex.getMessage().contains("cpf")) {
      errors.put("cpf", "CPF já cadastrado");
    }

    return createErrorResponse(
          HttpStatus.CONFLICT,
          "Erro de duplicidade",
          "Dados já cadastrados no sistema",
          errors
    );
  }

  @ExceptionHandler(CepNotFoundException.class)
  public ResponseEntity<ValidationErrorResponse> handleCepNotFound(
        CepNotFoundException ex) {
    Map<String, String> errors = new HashMap<>();
    errors.put("cep", ex.getMessage());

    return createErrorResponse(
          HttpStatus.BAD_REQUEST,
          "CEP não encontrado",
          "O CEP informado é inválido ou não foi encontrado",
          errors
    );
  }

  private ResponseEntity<ValidationErrorResponse> createErrorResponse(
        HttpStatus status, String title, String message, Map<String, String> errors) {
    ValidationErrorResponse response = new ValidationErrorResponse(
          status.value(),
          title,
          message,
          errors,
          LocalDateTime.now()
    );
    return new ResponseEntity<>(response, status);
  }

  record ValidationErrorResponse(
        int status,
        String title,
        String message,
        Map<String, String> errors,
        LocalDateTime timestamp
  ) {
  }
}
