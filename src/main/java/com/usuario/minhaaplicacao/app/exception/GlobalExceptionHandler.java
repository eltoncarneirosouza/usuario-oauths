package com.usuario.minhaaplicacao.app.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
    return createErrorResponse(
          HttpStatus.UNAUTHORIZED,
          "Erro de autenticação",
          "Token de acesso não fornecido ou inválido"
    );
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
    return createErrorResponse(
          HttpStatus.FORBIDDEN,
          "Acesso negado",
          "Você não tem permissão para acessar este recurso"
    );
  }

  @ExceptionHandler(ExpiredJwtException.class)
  public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex) {
    return createErrorResponse(
          HttpStatus.UNAUTHORIZED,
          "Token expirado",
          "O token de acesso expirou. Por favor, faça login novamente"
    );
  }

  @ExceptionHandler({SignatureException.class, MalformedJwtException.class})
  public ResponseEntity<ErrorResponse> handleJwtSignatureException(Exception ex) {
    return createErrorResponse(
          HttpStatus.UNAUTHORIZED,
          "Token inválido",
          "O token de acesso é inválido"
    );
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
    return createErrorResponse(
          HttpStatus.UNAUTHORIZED,
          "Credenciais inválidas",
          "As credenciais fornecidas são inválidas"
    );
  }

  private ResponseEntity<ErrorResponse> createErrorResponse(
        HttpStatus status, String title, String message) {
    ErrorResponse errorResponse = new ErrorResponse(
          status.value(),
          title,
          message,
          LocalDateTime.now()
    );
    return new ResponseEntity<>(errorResponse, status);
  }

  @Data
  @AllArgsConstructor
  static class ErrorResponse {
    private int status;
    private String title;
    private String message;
    private LocalDateTime timestamp;
  }
}
