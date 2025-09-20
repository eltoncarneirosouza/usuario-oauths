package com.usuario.minhaaplicacao.app.exception;

import lombok.Getter;

@Getter
public class CpfValidationException extends RuntimeException {
  private final String field;
  private final String message;

  public CpfValidationException(String field, String message) {
    super(message);
    this.field = field;
    this.message = message;
  }
}
