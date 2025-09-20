package com.usuario.minhaaplicacao.app.util;

import com.usuario.minhaaplicacao.app.exception.CPFValidationException;

public class CPFUtils {

  public static void validate(String cpf) {
    if (cpf == null) {
      throw new CPFValidationException("CPF não pode ser nulo");
    }

    cpf = cpf.replaceAll("[^0-9]", "");

    if (cpf.length() != 11) {
      throw new CPFValidationException("CPF deve conter 11 dígitos");
    }

    if (cpf.matches("(\\d)\\1{10}")) {
      throw new CPFValidationException("CPF não pode conter todos os dígitos iguais");
    }

    int soma = 0;
    for (int i = 0; i < 9; i++) {
      soma += (cpf.charAt(i) - '0') * (10 - i);
    }
    int primeiroDigito = 11 - (soma % 11);
    if (primeiroDigito > 9) {
      primeiroDigito = 0;
    }

    if (primeiroDigito != (cpf.charAt(9) - '0')) {
      throw new CPFValidationException("CPF inválido: primeiro dígito verificador incorreto");
    }

    soma = 0;
    for (int i = 0; i < 10; i++) {
      soma += (cpf.charAt(i) - '0') * (11 - i);
    }
    int segundoDigito = 11 - (soma % 11);
    if (segundoDigito > 9) {
      segundoDigito = 0;
    }

    if (segundoDigito != (cpf.charAt(10) - '0')) {
      throw new CPFValidationException("CPF inválido: segundo dígito verificador incorreto");
    }
  }

  public static boolean isValid(String cpf) {
    try {
      validate(cpf);
      return true;
    } catch (CPFValidationException e) {
      return false;
    }
  }

  public static String formatCPF(String cpf) {
    cpf = cpf.replaceAll("[^0-9]", "");

    if (cpf.length() == 11) {
      return cpf.substring(0, 3) + "." +
            cpf.substring(3, 6) + "." +
            cpf.substring(6, 9) + "-" +
            cpf.substring(9);
    }
    throw new CPFValidationException("CPF inválido para formatação");
  }

  public static String removeMask(String cpf) {
    return cpf.replaceAll("[^0-9]", "");
  }
}
