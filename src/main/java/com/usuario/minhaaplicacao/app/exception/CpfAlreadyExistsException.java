package com.usuario.minhaaplicacao.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CpfAlreadyExistsException extends RuntimeException {
  public CpfAlreadyExistsException(String cpf) {
    super("CPF já cadastrado: " + cpf);
  }
}
