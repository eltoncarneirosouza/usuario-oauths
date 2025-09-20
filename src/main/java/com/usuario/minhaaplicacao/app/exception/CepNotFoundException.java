package com.usuario.minhaaplicacao.app.exception;

import lombok.Getter;

@Getter
public class CepNotFoundException extends RuntimeException {
  public CepNotFoundException(String cep) {
    super("CEP não encontrado: " + cep);
  }
}
